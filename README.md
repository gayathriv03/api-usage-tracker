# 🚀 API Usage Tracker Backend

A production-style Spring Boot backend that tracks API usage per user and endpoint. This project demonstrates real-world backend engineering skills including logging, pagination, exception handling, Docker containerization, and AWS deployment with RDS.

---

## 📌 Project Highlights

* Track API calls per user and endpoint
* Interceptor-based request logging
* Global exception handling
* Custom filters & structured logging
* Pagination & sorting for analytics APIs
* Docker containerization & DockerHub publishing
* AWS EC2 deployment with RDS MySQL
* Environment-variable-based configuration

This project simulates a real microservice backend used in production systems.

---

## 🧰 Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven
* **Containerization:** Docker & Docker Compose
* **Cloud:** AWS EC2 + AWS RDS
* **Testing Tool:** Postman
* **Version Control:** Git + GitHub

---

## 🏗️ System Architecture

Client → Spring Boot API → Interceptor Logs Usage → MySQL Database → Stats APIs

Deployment:
Docker Container → EC2 Instance → RDS MySQL

---

## 📊 Database Design

Tables:

* **users** – stores user info
* **apis** – stores API endpoints
* **api_usage** – tracks API usage per user & API

Relationships:

* One User → Many API Usage Records
* One API → Many Usage Records

---

## ⚙️ Features Implemented

### 1️⃣ Logging & Tracking

* Interceptor captures every API call
* Tracks user ID from request headers
* Stores call count and timestamp

### 2️⃣ Global Exception Handling

* Centralized exception handler using `@ControllerAdvice`
* Handles validation errors, DB errors, and runtime exceptions
* Clean JSON error responses

### 3️⃣ Filters

* Custom request filters for validation and tracking
* Multi-user support using headers

### 4️⃣ Pagination & Sorting

Implemented pagination for large datasets:

* `/logs`
* `/stats/per-user`
* `/stats/per-api`

Supports:

* page number
* page size
* sorting by fields

### 5️⃣ Docker Containerization

* Created Dockerfile for Spring Boot app
* Built image and pushed to DockerHub
* Used environment variables for DB config

### 6️⃣ AWS Deployment

* Deployed Docker container on EC2
* Connected to AWS RDS MySQL
* Configured Security Groups
* Used SSH deployment
* Verified logs and DB connectivity

---

## 🐳 Docker Setup

### Build Image

```bash
mvn clean package

docker build -t gayathriv03/apiusagetracker:2.0 .
```

### Push to DockerHub

```bash
docker login

docker push gayathriv03/apiusagetracker:2.0
```

### Run Container

```bash
docker run -d --name api-tracker -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:mysql://<DB_ENDPOINT>:3306/apiusagetracker \
-e SPRING_DATASOURCE_USERNAME=api_user \
-e SPRING_DATASOURCE_PASSWORD=Api12345 \
gayathriv03/apiusagetracker:2.0
```

---

## ☁️ AWS Deployment Steps

1. Create EC2 instance
2. Install Docker
3. Create RDS MySQL (Free Tier)
4. Allow EC2 Security Group in RDS inbound rules
5. Pull Docker image
6. Run container with RDS endpoint
7. Access app using EC2 public IP

Example:

```
http://<EC2_PUBLIC_IP>:8080
```

---

## 🧪 Sample APIs

* `GET /logs`
* `GET /stats/per-user`
* `GET /stats/per-api`
* `POST /users`
* `POST /apis`

---

## 💡 Learning Outcomes

Through this project I learned:

* Real-world Spring Boot backend design
* Writing clean REST APIs
* Logging & exception handling strategies
* Pagination & query optimization
* Docker containerization
* AWS EC2 & RDS deployment
* Environment-based configuration
* Debugging production issues

---

## 📎 GitHub Repository Structure

```
src/
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── interceptor
 ├── filter
 └── exception
Dockerfile
pom.xml
README.md
```

---

## 👩‍💻 Author

**Gayathri V**
Java Backend Developer | Spring Boot | MySQL | Docker | AWS

---

## ⭐ Future Improvements

* Add JWT Authentication
* Add API rate limiting
* Add Grafana dashboard
* Convert to Microservices architecture
* Deploy using Kubernetes

---

## ❤️ If you like this project

Please ⭐ the repository!
