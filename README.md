[Русская версия](README_RU.md)

# 🏨 Hotel Booking System

**Hotel Booking System** — a full-featured backend solution for hotel reservations with REST API endpoints as a CMS panel, user management, CSV reporting, and Kafka event streaming. The system supports hotel CRUD operations, room management, bookings, role-based access, and statistics export via MongoDB.

---

## 🔍 Core Features

- Hotel, Room, Booking, and User management
- Role-based authentication (Admin/User) with **Basic Auth**
- REST API endpoints serve as CMS panel replacement
- Kafka integration for user and booking events
- MongoDB storage for statistics and CSV export
- CSV export of system statistics
- **DTOs** for all request and response objects

---

## ⚙️ Tech Stack

- **Backend:** Java 17+, Spring Boot, Spring Data JPA, MapStruct, Validation
- **Database:** PostgreSQL (main), MongoDB (statistics)
- **Messaging:** Kafka
- **Security:** Spring Security, Basic Auth
- **DevOps:** Docker, Docker Compose
- **Utilities:** Lombok, Maven

---

## 🧩 Entities

- **User, Role, RoleType** — user management and authorization
- **Hotel, Room, Booking** — main hotel reservation entities
- **StatisticsDocument** — for CSV export and MongoDB storage

---

## 🧾 REST API Endpoints

### Users

| Method | URL | Description | Roles | DTO |
|--------|-----|-------------|-------|-----|
| GET | /api/users | Get all users | ADMIN | UserListResponse |
| GET | /api/users/profile | Get current user profile | ADMIN, USER | String |
| GET | /api/users/{id} | Get user by ID | ADMIN, USER | UserResponse |
| POST | /api/users/account | Create a user | any | UserRequest → UserResponse |
| PUT | /api/users/{id} | Update user | ADMIN, USER | UserRequest → UserResponse |
| DELETE | /api/users/{id} | Delete user | ADMIN, USER | — |

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

- **Controllers** — REST controllers for API endpoints
- **DTO** — Request and Response classes (User, Hotel, Room, Booking, Statistics, Errors)
- **Services** — business logic and event handling
- **Mappers** — MapStruct mappers for DTO conversion
- **Repositories** — Spring Data JPA / Mongo repositories
- **Security** — Authentication and role-based access
- **Kafka** — Producers and consumers for User and Booking events

---

## ⚙️ Configuration

Application uses `application.yaml` for server settings, database connections, Kafka, and CSV export.

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

kafka:
  bootstrap-servers: localhost:9092
  user-topic: user-events
  booking-topic: booking-events

mongodb:
  uri: mongodb://localhost:27017/statistics
```
## ⚙️ Key Parameters

- **server.port** — port for the application
- **spring.datasource.\*** — PostgreSQL connection settings
- **spring.jpa.hibernate.ddl-auto** — database schema management
- **kafka.\*** — Kafka topics and bootstrap server
- **mongodb.uri** — MongoDB connection for statistics

---

## 🚀 Local Setup

### Requirements

- Java 17+
- Maven
- PostgreSQL
- MongoDB
- Kafka
- Docker & Docker Compose (optional)

### Steps

1. Clone the repository:

```bash
git clone <REPO_URL>
cd <PROJECT_FOLDER>
```
### Configure PostgreSQL, MongoDB, and Kafka

Edit the `application.yaml` file to set up connections for PostgreSQL, MongoDB, and Kafka according to your environment.

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

- All requests/responses use **DTOs**
- Kafka streams **UserRegistrationEvent** and **BookingEvent**
- Statistics stored in MongoDB can be exported via **CSV endpoint**
- Role-based access ensures **Admin/User separation**
- Errors return unified JSON response through **ExceptionController**  