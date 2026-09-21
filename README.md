# Job Portal — Microservices Backend

A scalable, cloud-ready **Job Portal backend** built with **Spring Boot microservices**.  
Includes service discovery, centralized configuration, API gateway, and modular domain services.

---

## 📦 Modules

| Module | Description |
|--------|-------------|
| `job-portal-common` | Shared DTOs, exceptions, utilities, constants |
| `job-portal-cloud`  | Eureka Server, Config Server, API Gateway |
| `job-portal-service`| Business microservices (user, auth, job, company, etc.) |

---

## 🛠️ Tech Stack

- **Java 21+**
- **Spring Boot 4.x**
- **Spring Cloud** (Eureka, Config, Gateway)
- **Spring Data JPA**
- **PostgreSQL** / **MySQL**
- **Docker** + **Docker Compose**
- **Maven** (multi-module)
- **JWT** for authentication

---

## 📂 Project Structure

Job Portal/

├── docker/ # Docker Compose & infra configs

├── job-portal-config/ # Centralized config server YAMLs

└── job-portal-system/ # Maven multi-module root

├── cloud/ # Eureka, Config Server, API Gateway

├── common-lib/ # Shared library

└── services/ # Business microservices


---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL (or use Docker)

### Run Locally

```bash
# Clone
git clone https://github.com/YOUR_USERNAME/job-portal.git
cd job-portal

# Copy env templates
cp docker/.env
cp job-portal-system/.env

# Start infra
cd docker
docker-compose up -d

# Build all modules
cd ../job-portal-system
mvn clean install

# Run services (in order)
# 1. Eureka Server
# 2. Config Server
# 3. API Gateway
# 4. Business services