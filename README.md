# 🍕 Pizzeria Management System

A comprehensive RESTful API backend for managing an online pizza ordering system, built with Spring Boot and modern Java technologies.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security](#security)
- [Testing](#testing)
- [Contributing](#contributing)

## 🎯 Overview

The Pizzeria Management System is a robust backend application that provides a complete solution for managing an online pizza restaurant. The system supports user authentication, pizza catalog management, order processing, and administrative functions with role-based access control.

This project demonstrates modern enterprise Java development practices, including:

- Clean architecture with separation of concerns
- RESTful API design principles
- Comprehensive security implementation
- Advanced database operations with JPA/Hibernate
- Professional error handling and validation

## ✨ Features

### 🔐 Authentication & Authorization

- **JWT-based authentication** with secure token management
- **Role-based access control** (CUSTOMER, ADMIN)
- **Password encryption** using BCrypt
- **Secure endpoint protection**

### 🍕 Pizza Management

- **CRUD operations** for pizza catalog
- **Advanced filtering** by name, size, and dough type
- **Pagination and sorting** for large datasets
- **Image URL management** for pizza photos
- **Flexible size and dough type configuration**

### 📦 Order Management

- **Complete order lifecycle** management
- **Order status tracking** (NEW, IN_PROGRESS, DELIVERED, CANCELLED)
- **Order history** for customers
- **Administrative order management**
- **Detailed order items** with pricing calculations

### 👥 User Management

- **User registration and profile management**
- **Secure password change functionality**
- **Address and contact information management**
- **Order history tracking**

### 🔍 Advanced Features

- **Dynamic filtering and search**
- **Pagination** for optimal performance
- **Multi-criteria sorting**
- **Comprehensive error handling**
- **Input validation**
- **Database transaction management**

## 🛠 Tech Stack

### Backend Framework

- **Spring Boot 3.4.0** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Spring Web** - RESTful web services
- **Spring Validation** - Input validation

### Database

- **PostgreSQL** - Primary database
- **Hibernate** - ORM framework
- **JPA** - Java Persistence API

### Security & Authentication

- **JWT (JSON Web Tokens)** - Authentication tokens
- **BCrypt** - Password hashing
- **Spring Security** - Security framework

### Development Tools

- **Java 17** - Programming language
- **Maven** - Dependency management
- **Lombok** - Code generation
- **Spring Boot DevTools** - Development utilities

### Additional Libraries

- **JJWT 0.11.5** - JWT implementation
- **PostgreSQL Driver** - Database connectivity

## 🏗 Architecture

The application follows a layered architecture pattern:

```
┌─────────────────┐
│   Controllers   │  ← REST endpoints, request/response handling
├─────────────────┤
│    Services     │  ← Business logic, transaction management
├─────────────────┤
│  Repositories   │  ← Data access layer, JPA repositories
├─────────────────┤
│    Entities     │  ← JPA entities, database mapping
└─────────────────┘
```

### Key Components:

- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic and coordinate operations
- **Repositories**: Manage data persistence and retrieval
- **Entities**: Define database structure and relationships
- **DTOs**: Data transfer objects for API communication
- **Configuration**: Security, database, and application configuration

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6** or higher

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/pizzeria.git
   cd pizzeria
   ```

2. **Set up PostgreSQL database**

   ```sql
   CREATE DATABASE pizzeria;
   CREATE USER postgres WITH PASSWORD '2005';
   GRANT ALL PRIVILEGES ON DATABASE pizzeria TO postgres;
   ```

3. **Configure application properties**
   Update `src/main/resources/application.yml`:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/pizzeria
       username: postgres
       password: your_password
   ```

4. **Build the project**

   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Initial Data

The application includes a `DataInitializer` that automatically creates:

- Sample pizza catalog with various options
- Admin user account
- Sample customer data

## 📚 API Documentation

### Base URL

```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User

```http
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe",
  "phone": "+1234567890",
  "address": "123 Main St"
}
```

#### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "CUSTOMER"
}
```

### Pizza Endpoints

#### Get All Pizzas (with filtering and pagination)

```http
GET /pizzas?search=pepperoni&size=30cm&dough=thin&page=0&sizePage=10&sortBy=name&sortDir=asc
```

#### Get Pizza by ID

```http
GET /pizzas/{id}
```

#### Create Pizza (Admin only)

```http
POST /pizzas
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Margherita",
  "description": "Classic pizza with tomato sauce, mozzarella, and basil",
  "basePrice": 12.99,
  "imageUrl": "https://example.com/margherita.jpg",
  "availableSizes": ["25cm", "30cm", "35cm"],
  "doughTypes": ["thin", "traditional"]
}
```

### Order Endpoints

#### Create Order

```http
POST /orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "pizzaId": 1,
      "quantity": 2,
      "selectedSize": "30cm",
      "selectedDough": "thin"
    }
  ],
  "customerName": "John Doe",
  "customerPhone": "+1234567890",
  "deliveryAddress": "123 Main St"
}
```

#### Get My Orders

```http
GET /orders/my
Authorization: Bearer {token}
```

#### Cancel Order

```http
PUT /orders/{id}/cancel
Authorization: Bearer {token}
```

### Admin Endpoints

#### Update Order Status

```http
PUT /admin/orders/{id}/status
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "status": "IN_PROGRESS"
}
```

### User Management

#### Get Profile

```http
GET /users/me
Authorization: Bearer {token}
```

#### Update Profile

```http
PUT /users/me
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "John Smith",
  "phone": "+1987654321",
  "address": "456 New Street"
}
```

#### Change Password

```http
PUT /users/me/password
Authorization: Bearer {token}
Content-Type: application/json

{
  "currentPassword": "oldpassword123",
  "newPassword": "newpassword456"
}
```

## 🗄 Database Schema

### Core Entities

#### Users Table

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    address VARCHAR(255),
    role VARCHAR(50) NOT NULL
);
```

#### Pizzas Table

```sql
CREATE TABLE pizza (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    base_price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500)
);
```

#### Orders Table

```sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(255) NOT NULL,
    delivery_address VARCHAR(500) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    user_id BIGINT REFERENCES users(id)
);
```

### Relationships

- **User** ↔ **Order**: One-to-Many (User can have multiple orders)
- **Order** ↔ **OrderItem**: One-to-Many (Order contains multiple items)
- **Pizza** ↔ **OrderItem**: Many-to-One (Pizza can be in multiple order items)

## 🔒 Security

### Authentication Flow

1. User registers or logs in with credentials
2. Server validates credentials and generates JWT token
3. Client includes token in `Authorization: Bearer {token}` header
4. Server validates token on each protected request
5. Token contains user email and role for authorization

### Authorization Levels

- **Public**: Registration, login, view pizzas
- **CUSTOMER**: Create orders, view own orders, manage profile
- **ADMIN**: Manage pizzas, view all orders, update order status

### Security Features

- **Password hashing** with BCrypt
- **JWT token expiration**
- **Role-based access control**
- **Input validation and sanitization**
- **SQL injection prevention** through JPA
- **CORS configuration** for frontend integration

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

### Test Structure

- **Unit Tests**: Service layer business logic
- **Integration Tests**: Controller endpoints
- **Repository Tests**: Database operations
- **Security Tests**: Authentication and authorization

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'Add some amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add appropriate comments for complex logic
- Ensure all tests pass before submitting

## 📊 Project Status

✅ **Completed Features:**

- User authentication and authorization
- Pizza catalog management
- Order processing system
- Admin panel functionality
- Database integration
- Security implementation

🚧 **Future Enhancements:**

- Real-time order tracking
- Payment integration
- Notification system
- Advanced reporting
- Caching implementation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

_This project demonstrates modern Java development practices and enterprise-level application architecture. It serves as an excellent reference for building scalable RESTful APIs with Spring Boot._
