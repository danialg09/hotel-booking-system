[Русская версия](README_RU.md)

# 🏨 Hotel Booking System

**Hotel Booking System** — a full-featured backend solution for hotel reservations with REST API endpoints as a CMS panel, user management, CSV reporting, and Kafka event streaming. The system supports hotel CRUD operations, room management, bookings, role-based access, and statistics export via MongoDB.

---

## 🔍 Core Features

- Hotel, Room, Booking, and User management
- Role-based authentication (Admin/User) with **JWT Bearer Tokens**
- **Refresh Token** mechanism for secure session management
- REST API endpoints serve as CMS panel replacement
- Kafka integration for user and booking events
- MongoDB storage for statistics and CSV export
- CSV export of system statistics
- **DTOs** for all request and response objects

---

## ⚙️ Tech Stack

- **Backend:** Java 21+, Spring Boot, Spring Data JPA, MapStruct, Validation
- **Database:** PostgreSQL (main), MongoDB (statistics)
- **Messaging:** Kafka
- **Security:** Spring Security, **JWT, Refresh Tokens**
- **Caching/Tokens:** **Redis**
- **DevOps:** Docker, Docker Compose
- **Utilities:** Lombok, Maven

---

## 🧩 Entities

- **User, RoleType** — user management and authorization (Roles stored using `@ElementCollection`)
- **RefreshToken** — security entity managed by Redis
- **Hotel, Room, Booking** — main hotel reservation entities
- **StatisticsDocument** — for CSV export and MongoDB storage

---

## 🧾 REST API Endpoints

### Authentication

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| POST | /api/auth/register | Register a new user | any | RegisterRequest → LoginResponse |
| POST | /api/auth/login | Authenticate and get JWT/Refresh Tokens | any | LoginRequest → LoginResponse |
| POST | /api/auth/refresh-token | Renew JWT using a valid Refresh Token | any | RefreshTokenRequest → RefreshTokenResponse |
| POST | /api/auth/logout | Invalidate Refresh Token and log out | ADMIN, USER | — |

### Users

| Method | URL                | Description | Roles | DTO |
|--------|--------------------|-------------|-------|-----|
| GET | /api/users         | Get all users | ADMIN | UserListResponse |
| GET | /api/users/profile | Get current user profile | ADMIN, USER | String |
| GET | /api/users/{id}    | Get user by ID | ADMIN, USER | UserResponse |
| POST | /api/users/create  | Create a user | any | UserRequest → UserResponse |
| PUT | /api/users/{id}    | Update user | ADMIN, USER | UserRequest → UserResponse |
| DELETE | /api/users/{id}    | Delete user | ADMIN, USER | — |

### Hotels

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| GET | /api/hotels | Get all hotels | ADMIN | ListHotelResponse |
| GET | /api/hotels/filter | Filter hotels | ADMIN, USER | HotelFilter → PageResponse<HotelResponse> |
| GET | /api/hotels/{id} | Get hotel by ID | ADMIN, USER | HotelResponse |
| POST | /api/hotels | Create hotel | ADMIN | HotelRequest → HotelResponse |
| PUT | /api/hotels/{id} | Update hotel | ADMIN | HotelRequest → HotelResponse |
| DELETE | /api/hotels/{id} | Delete hotel | ADMIN | — |
| POST | /api/hotels/review/{id} | Add rating | any | RatingDTO → HotelResponse |

### Rooms

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| GET | /api/rooms/{id} | Get room by ID | ADMIN, USER | RoomResponse |
| GET | /api/rooms/filter | Filter rooms | ADMIN, USER | RoomFilter → PageResponse<RoomResponse> |
| POST | /api/rooms | Create room | ADMIN | RoomRequest → RoomResponse |
| PUT | /api/rooms/{id} | Update room | ADMIN | RoomRequest → RoomResponse |
| DELETE | /api/rooms/{id} | Delete room | ADMIN | — |

### Bookings

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| POST | /api/bookings | Create booking | ADMIN, USER | BookingRequest → BookingResponse |
| GET | /api/bookings | List all bookings | ADMIN | ListBookingResponse |

### Statistics

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| GET | /statistics/csv | Download CSV statistics | ADMIN | CSV file (from StatisticsDocument) |

---

## 📦 Project Structure

- **Controllers** — REST controllers for API endpoints, including the new `AuthController`.
- **DTO** — Request and Response classes (User, Hotel, Room, Booking, Statistics, Errors, **Auth**)
- **Services** — business logic and event handling, including `AuthService` and `RefreshTokenService`.
- **Mappers** — MapStruct mappers for DTO conversion
- **Repositories** — Spring Data JPA / Mongo / **Redis** repositories
- **Security** — Authentication, JWT components, and role-based access
- **Kafka** — Producers and consumers for User and Booking events

---

## ⚙️ Configuration

Application uses `application.yaml` for server settings, database connections, Kafka, and security parameters.

### Example `application.yaml`:

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
  data:
    mongodb:
      host: localhost
      port: 27017
      database: statistics_db
    redis: # <-- NEW
      host: localhost # <-- NEW
      port: 6379 # <-- NEW

kafka:
  bootstrap-servers: localhost:9092

app:
  kafka:
    statisticsTopic: "statistics"
    kafkaMessageGroupId: "kafka-message-group-id"
  jwt:
    secret: "yourSecretKey" # <-- NEW
    tokenExpiration: 12h # <-- NEW
    refreshTokenExpiration: 24h # <-- NEW
```
## ⚙️ Key Parameters

- **server.port** — port for the application
- **spring.datasource.** — PostgreSQL connection settings
- **spring.jpa.hibernate.ddl-auto** — database schema management
- **kafka.** — Kafka topics and bootstrap server
- **mongodb.uri** — MongoDB connection for statistics
- **redis.** — Redis connection for Refresh Tokens
- **app.jwt.** — JWT secret and token expiration times

---

## 🚀 Local Setup

### Requirements

- Java 21+
- Maven
- PostgreSQL
- MongoDB
- Kafka
- Redis
- Docker & Docker Compose (optional)

### Steps

1. Clone the repository:

```bash
git clone <REPO_URL>
cd <PROJECT_FOLDER>
```
### Configure PostgreSQL, MongoDB, and Kafka

```bash
cd docker
docker compose up
cd ..
```

---

### Build and Run the Project

```bash
mvn clean install
mvn spring-boot:run
```
### API Access

The API will be available at:  
[http://localhost:8080](http://localhost:8080)

---

### ⚡ Notes

- Authentication is now done by sending a JWT Bearer Token in the Authorization header.
- All requests/responses use **DTOs**
- Kafka streams **UserRegistrationEvent** and **BookingEvent**
- Statistics stored in MongoDB can be exported via **CSV endpoint**
- Role-based access ensures **Admin/User separation**
- Errors return unified JSON response through **ExceptionController**  