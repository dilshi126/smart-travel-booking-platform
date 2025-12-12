# Smart Travel Booking Platform

A distributed travel booking backend platform built with Spring Boot 3+ and Java 17, featuring 6 microservices with inter-service communication using REST API, Feign Client, and WebClient.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           SMART TRAVEL BOOKING PLATFORM                                  │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                  │
│    ┌──────────────┐                                                             │
│    │    Client    │                                                             │
│    │  (Postman)   │                                                             │
│    └──────┬───────┘                                                             │
│           │                                                                      │
│           ▼                                                                      │
│    ┌──────────────────────────────────────────────────────────────────┐         │
│    │                    BOOKING SERVICE (8086)                         │         │
│    │                    [Main Orchestrator]                            │         │
│    └──────────────────────────────────────────────────────────────────┘         │
│           │                                                                      │
│           │                                                                      │
│    ┌──────┴──────────────────────────────────────────────────────┐              │
│    │                                                              │              │
│    │  WebClient                    Feign Client                   │              │
│    │                                                              │              │
│    ▼                               ▼                              ▼              │
│ ┌────────────┐              ┌────────────┐              ┌────────────┐          │
│ │   USER     │              │  FLIGHT    │              │   HOTEL    │          │
│ │  SERVICE   │              │  SERVICE   │              │  SERVICE   │          │
│ │  (8081)    │              │  (8082)    │              │  (8083)    │          │
│ └────────────┘              └────────────┘              └────────────┘          │
│                                                                                  │
│    WebClient                                                                     │
│    ▼                                                                             │
│ ┌────────────┐              ┌────────────┐                                      │
│ │  PAYMENT   │──WebClient──▶│  BOOKING   │                                      │
│ │  SERVICE   │              │  SERVICE   │                                      │
│ │  (8084)    │              │  (8086)    │                                      │
│ └────────────┘              └────────────┘                                      │
│                                                                                  │
│    WebClient                                                                     │
│    ▼                                                                             │
│ ┌────────────┐                                                                  │
│ │NOTIFICATION│                                                                  │
│ │  SERVICE   │                                                                  │
│ │  (8085)    │                                                                  │
│ └────────────┘                                                                  │
│                                                                                  │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## Communication Flow

| From | To | Method | Purpose |
|------|-----|--------|---------|
| Booking Service | User Service | WebClient | Validate user exists |
| Booking Service | Flight Service | Feign Client | Check flight availability |
| Booking Service | Hotel Service | Feign Client | Check hotel availability |
| Booking Service | Payment Service | WebClient | Process payment |
| Booking Service | Notification Service | WebClient | Send booking confirmation |
| Payment Service | Booking Service | WebClient | Confirm booking after payment |

## Microservices

| Service | Port | Description |
|---------|------|-------------|
| User Service | 8081 | Manages user data and validation |
| Flight Service | 8082 | Manages flights and availability |
| Hotel Service | 8083 | Manages hotels and room availability |
| Payment Service | 8084 | Processes payments |
| Notification Service | 8085 | Sends email/SMS notifications |
| Booking Service | 8086 | Main orchestrator for bookings |

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Cloud OpenFeign
- Spring WebFlux (WebClient)
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- SpringDoc OpenAPI (Swagger)

## Booking Flow

1. User sends booking request to Booking Service
2. Booking Service validates user via WebClient → User Service
3. Booking Service checks flight availability via Feign Client → Flight Service
4. Booking Service checks hotel availability via Feign Client → Hotel Service
5. Booking Service calculates total cost
6. Booking Service stores booking as PENDING
7. Booking Service processes payment via WebClient → Payment Service
8. Booking Service sends notification via WebClient → Notification Service
9. Booking Service updates booking to CONFIRMED

## Running the Services

### Prerequisites
- Java 17+
- Maven 3.8+

### Start Each Service

```bash
# Terminal 1 - User Service
cd user-service
mvn spring-boot:run

# Terminal 2 - Flight Service
cd flight-service
mvn spring-boot:run

# Terminal 3 - Hotel Service
cd hotel-service
mvn spring-boot:run

# Terminal 4 - Payment Service
cd payment-service
mvn spring-boot:run

# Terminal 5 - Notification Service
cd notification-service
mvn spring-boot:run

# Terminal 6 - Booking Service
cd booking-service
mvn spring-boot:run
```

## API Endpoints

### User Service (8081)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/{id}/validate` - Validate user exists
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Flight Service (8082)
- `GET /api/flights` - Get all flights
- `GET /api/flights/{id}` - Get flight by ID
- `GET /api/flights/{id}/availability` - Check availability
- `POST /api/flights/{id}/book` - Book a seat
- `POST /api/flights` - Create flight

### Hotel Service (8083)
- `GET /api/hotels` - Get all hotels
- `GET /api/hotels/{id}` - Get hotel by ID
- `GET /api/hotels/{id}/availability` - Check availability
- `POST /api/hotels/{id}/book` - Book a room
- `POST /api/hotels` - Create hotel

### Payment Service (8084)
- `GET /api/payments` - Get all payments
- `GET /api/payments/{id}` - Get payment by ID
- `POST /api/payments/process` - Process payment
- `POST /api/payments/{id}/refund` - Refund payment

### Notification Service (8085)
- `GET /api/notifications` - Get all notifications
- `GET /api/notifications/user/{userId}` - Get by user
- `POST /api/notifications/send` - Send notification

### Booking Service (8086)
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `POST /api/bookings` - Create booking (full flow)
- `PUT /api/bookings/{id}/confirm` - Confirm booking
- `PUT /api/bookings/{id}/cancel` - Cancel booking

## Sample Booking Request

```json
POST http://localhost:8086/api/bookings
Content-Type: application/json

{
  "userId": 1,
  "flightId": 1,
  "hotelId": 1,
  "travelDate": "2025-01-10"
}
```

## Swagger UI

Each service has Swagger UI available at:
- User Service: http://localhost:8081/swagger-ui.html
- Flight Service: http://localhost:8082/swagger-ui.html
- Hotel Service: http://localhost:8083/swagger-ui.html
- Payment Service: http://localhost:8084/swagger-ui.html
- Notification Service: http://localhost:8085/swagger-ui.html
- Booking Service: http://localhost:8086/swagger-ui.html

## H2 Console

Each service has H2 console available at `http://localhost:{port}/h2-console`
- JDBC URL: `jdbc:h2:mem:{servicename}db`
- Username: `sa`
- Password: (empty)

## Project Structure

```
smart-travel-platform/
├── user-service/
├── flight-service/
├── hotel-service/
├── booking-service/
├── payment-service/
├── notification-service/
├── Smart-Travel-Platform.postman_collection.json
└── README.md
```
