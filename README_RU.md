[English version](README.md)

# 🏨 Система бронирования отелей

**Hotel Booking System** — это полноценное backend-решение для бронирования отелей с REST API, выполняющим роль CMS-панели, системой управления пользователями, CSV-отчетностью и Kafka для обработки событий. Система поддерживает управление отелями, номерами, бронированиями, ролевой доступ и экспорт статистики через MongoDB.

---

## 🔍 Основной функционал

- Управление пользователями, ролями и авторизацией
- CRUD операции для отелей, номеров и бронирований
- Ролевая аутентификация (Admin/User) с Basic Auth
- REST API выступает в роли CMS-панели
- Kafka интеграция для событий **UserRegistrationEvent** и **BookingEvent**
- Хранение статистики в MongoDB
- Экспорт статистики в CSV
- Использование DTO для всех запросов и ответов

---

## ⚙️ Технологии

- **Backend:** Java 17+, Spring Boot, Spring Data JPA, MapStruct, Validation
- **База данных:** PostgreSQL (основная), MongoDB (статистика)
- **Месседжинг:** Kafka
- **Безопасность:** Spring Security, Basic Auth
- **DevOps:** Docker, Docker Compose
- **Утилиты:** Lombok, Maven

---

## 🧩 Сущности

- **User, Role, RoleType** — управление пользователями и правами
- **Hotel, Room, Booking** — основные сущности бронирования
- **StatisticsDocument** — хранение статистики и генерация CSV

---

## 🧾 REST API Эндпоинты

### Пользователи
| Метод | URL | Описание | Роли | DTO |
|-------|-----|----------|------|-----|
| GET | `/api/users` | Получить всех пользователей | ADMIN | UserListResponse |
| GET | `/api/users/profile` | Получить профиль текущего пользователя | ADMIN, USER | String |
| GET | `/api/users/{id}` | Получить пользователя по ID | ADMIN, USER | UserResponse |
| POST | `/api/users/account` | Создать пользователя | любой | UserRequest → UserResponse |
| PUT | `/api/users/{id}` | Обновить пользователя | ADMIN, USER | UserRequest → UserResponse |
| DELETE | `/api/users/{id}` | Удалить пользователя | ADMIN, USER | — |

### Отели
| Метод | URL | Описание | Роли | DTO |
|-------|-----|----------|------|-----|
| GET | `/api/hotels` | Получить все отели | ADMIN | ListHotelResponse |
| GET | `/api/hotels/filter` | Фильтрация отелей | ADMIN, USER | HotelFilter → PageResponse<HotelResponse> |
| GET | `/api/hotels/{id}` | Получить отель по ID | ADMIN, USER | HotelResponse |
| POST | `/api/hotels` | Создать отель | ADMIN | HotelRequest → HotelResponse |
| PUT | `/api/hotels/{id}` | Обновить отель | ADMIN | HotelRequest → HotelResponse |
| DELETE | `/api/hotels/{id}` | Удалить отель | ADMIN | — |
| POST | `/api/hotels/review/{id}` | Добавить рейтинг | любой | RatingDTO → HotelResponse |

### Номера
| Метод | URL | Описание | Роли | DTO |
|-------|-----|----------|------|-----|
| GET | `/api/rooms/{id}` | Получить номер по ID | ADMIN, USER | RoomResponse |
| GET | `/api/rooms/filter` | Фильтрация номеров | ADMIN, USER | RoomFilter → PageResponse<RoomResponse> |
| POST | `/api/rooms` | Создать номер | ADMIN | RoomRequest → RoomResponse |
| PUT | `/api/rooms/{id}` | Обновить номер | ADMIN | RoomRequest → RoomResponse |
| DELETE | `/api/rooms/{id}` | Удалить номер | ADMIN | — |

### Бронирования
| Метод | URL | Описание | Роли | DTO |
|-------|-----|----------|------|-----|
| POST | `/api/bookings` | Создать бронирование | ADMIN, USER | BookingRequest → BookingResponse |
| GET | `/api/bookings` | Получить все бронирования | ADMIN | ListBookingResponse |

### Статистика
| Метод | URL | Описание | Роли | DTO |
|-------|-----|----------|------|-----|
| GET | `/statistics/csv` | Скачать CSV статистики | ADMIN | CSV файл (генерируется из StatisticsDocument) |

---

## 📦 Структура проекта

- **Controllers** — REST контроллеры для API
- **DTO** — классы запросов и ответов (User, Hotel, Room, Booking, Statistics, Errors)
- **Services** — бизнес-логика и обработка событий
- **Mappers** — MapStruct мапперы для конвертации DTO
- **Repositories** — Spring Data JPA / Mongo репозитории
- **Security** — аутентификация и ролевой доступ
- **Kafka** — продюсеры и консьюмеры для событий пользователей и бронирований

---

## ⚙️ Конфигурация

Приложение использует **application.yaml** для настройки сервера, баз данных, Kafka и CSV-экспорта.

Пример `application.yaml`:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hotel
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

kafka:
  bootstrap-servers: localhost:9092
  user-topic: user-events
  booking-topic: booking-events

mongodb:
  uri: mongodb://localhost:27017/statistics
```
## 🔑 Основные параметры

- `server.port` — порт приложения
- `spring.datasource.*` — настройки подключения к PostgreSQL
- `spring.jpa.hibernate.ddl-auto` — управление схемой базы данных
- `kafka.*` — Kafka topics и bootstrap server
- `mongodb.uri` — MongoDB для хранения статистики

---

## 🚀 Локальный запуск

### Требования

- Java 17+
- Maven
- PostgreSQL
- MongoDB
- Kafka
- Docker & Docker Compose (опционально)

### Шаги запуска

1. Клонировать репозиторий:

```bash
git clone <REPO_URL>
cd <PROJECT_FOLDER>
```
### Настройка и запуск

1. Настроить PostgreSQL, MongoDB и Kafka в `application.yaml`.

2. Собрать и запустить проект:

```bash
mvn clean install
mvn spring-boot:run
```
### Доступ к API

[http://localhost:8080](http://localhost:8080)

---

### ⚡ Примечания

- Все запросы/ответы используют **DTO**.
- Kafka стримит **UserRegistrationEvent** и **BookingEvent**.
- Статистика сохраняется в **MongoDB** и может быть экспортирована в CSV.
- Ролевой доступ обеспечивает разделение **Admin/User**.
- Ошибки возвращаются в виде единого JSON через **ExceptionController**.