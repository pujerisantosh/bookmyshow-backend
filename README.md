BookMyShow Backend System

A high-concurrency movie ticket booking backend system that prevents double booking using Redis-based distributed locking and ensures consistency via transactional and event-driven architecture.

🚀 Problem Statement

Design and implement a scalable backend system for a movie ticket booking platform that:

Handles high-concurrency seat reservations
Prevents double booking
Ensures data consistency
Provides fault tolerance and scalability
❗ Challenges Addressed
🔥 High Concurrency

Multiple users trying to book the same seat simultaneously.

🔒 Double Booking

Avoid race conditions leading to duplicate seat allocation.

⚡ Performance

Minimize response time under heavy traffic.

🔄 Consistency

Ensure booking and seat updates are atomic.

⏳ Failure Handling

Handle abandoned bookings and payment failures.

💡 Solution Overview
✅ Redis-Based Seat Locking
Temporary seat locking mechanism
Prevents concurrent booking of same seat
✅ Transaction Management
Implemented using @Transactional
Ensures:
Seat status update
Booking confirmation
happen atomically
✅ Event-Driven Architecture (Kafka)
Booking events published asynchronously
Improves scalability and decouples services
✅ Seat Expiry System
Scheduled jobs to:
Release locked seats after timeout
Expire stale bookings
✅ Redis Caching
Fast seat availability lookup
Reduces DB load
🏗️ System Design Highlights
Distributed locking using Redis
Event-driven architecture using Kafka
Transactional consistency with Spring Boot
Scalable and modular design
Fault-tolerant booking workflow
🔄 Booking Flow
1. User selects seats
2. Seats are LOCKED in Redis
3. Booking created (PENDING)
4. Payment processed
5. On success:
   - Seats → BOOKED
   - Booking → CONFIRMED
   - Kafka event published
6. On failure:
   - Seats unlocked
🧩 Core Modules
🎟️ Booking Service
🎬 Show Management
🪑 Seat Management
🔐 Seat Locking System
💳 Payment Flow (basic simulation)
📩 Event Publisher (Kafka)
⚙️ Tech Stack
Category	Technology
Backend	Java, Spring Boot
Database	PostgreSQL
Cache	Redis
Messaging	Kafka
ORM	Hibernate / JPA
API Docs	Swagger UI
Testing	JUnit, Mockito
Build Tool	Maven
Container	Docker
📡 API Endpoints (Sample)
🎟️ Booking APIs
POST /bookings → Create booking
POST /bookings/{id}/confirm → Confirm booking
🎬 Show APIs
GET /shows → List shows
POST /shows → Create show
🏢 Theatre APIs
POST /theatres
GET /theatres/city/{city}
🧪 Testing
Unit tests using JUnit & Mockito
API testing via Swagger UI / Postman
Manual concurrency testing (multiple users)
🧠 Key Engineering Concepts Used
Distributed Locking (Redis)
Transaction Management
Event-Driven Architecture
Concurrency Handling
Caching Strategy
Scheduled Jobs (Expiry Handling)
🧩 Future Enhancements
Payment Gateway Integration (Stripe/Razorpay)
Rate Limiting
Circuit Breaker (Resilience4j)
Microservices Architecture
Distributed Tracing (Zipkin)
Seat Recommendation Engine (AI)
🐳 Running the Project
Prerequisites
Java 17+
Docker
PostgreSQL
Redis
Kafka
Steps
# Clone repo
git clone https://github.com/your-username/bookmyshow-backend.git

# Navigate
cd bookmyshow-backend

# Run application
mvn spring-boot:run
Swagger UI
http://localhost:8080/swagger-ui.html
📊 Database Schema (High Level)
Booking
Show
ShowSeat
Theatre
Screen
Seat
🔥 What Makes This Project Special

✅ Handles real-world concurrency problems
✅ Prevents double booking
✅ Uses Redis + Kafka (advanced backend stack)
✅ Production-grade architecture concepts
✅ Not a CRUD app — system design focused

Architecture Diagram:
## 🏗️ System Architecture 

```
User
  │
  ▼
REST API Layer (Controller)
  │
  ▼
Booking Service (Transactional Core)
  │
  ├── Redis (Distributed Locking + Caching)
  │     ├─ Seat Lock (TTL-based)
  │     └─ Fast Availability Lookup
  │
  ├── PostgreSQL (Source of Truth)
  │     ├─ Booking Table
  │     └─ Show Seats Table
  │
  └── Kafka (Event Streaming)
        ├─ Booking Confirmed Event
        └─ Async Processing

Kafka Consumers:
  ├── Notification Service
  ├── Analytics Service
  └── Expiry Scheduler
        ├─ Release Expired Locks
        └─ Cleanup Stale Bookings

Infrastructure:
  ├── Docker (Containerization)
  ├── Redis Container
  ├── PostgreSQL Container
  └── Kafka Container
```



👨‍💻 Author

Santosh Pujeri
Backend Developer | Java | Spring Boot | System Design
