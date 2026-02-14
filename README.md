# API Usage Tracker

## 📌 Project Overview
This project tracks API usage for multiple users using Spring Boot, JPA, and MySQL.
Every API call is intercepted and stored in the database with user and API details.

## 🛠 Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Lombok

## 🚀 Features
- User registration
- API tracking using Spring Interceptor
- Usage statistics per API and per user
- Dashboard APIs for analytics
- Multi-user support using header-based tracking

## 📂 Database Tables
- users
- apis
- api_usage

## 📡 Sample API Requests

### Create User
POST /create-user

```json
{
  "name": "Gayathri",
  "email": "gayathri@test.com"
}
