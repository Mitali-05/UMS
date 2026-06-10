# UMS - User Management System

A REST API backend for managing Users, Roles, Groups and Permissions with full authentication and authorization.

## Tech Stack

- Java 21
- Spring Boot 4.0
- Spring Security 7 + JWT
- Spring Data JPA + Hibernate
- MySQL 8
- Lombok
- Maven

## Features

- JWT Authentication - login returns token, all endpoints protected
- Permission-based Role Access Control (RBAC) - roles and permissions stored in DB, not hardcoded
- BCrypt password hashing - passwords never stored or returned in plain text
- Full CRUD for Users, Roles, User Groups and Permissions
- Input validation with descriptive error messages
- Global exception handling with clean JSON error responses
- Layered architecture - Controller → Service → Repository

## Architecture

```
src/main/java/com/example/ums/
├── common/          # Security, JWT, Exception handling
├── domain/          # Entities, Repositories
├── application/     # DTOs, Mappers, Services
└── interfaces/      # REST Controllers
```

## Database Schema

| Table | Description |
|-------|-------------|
| `user_info` | Stores users with hashed passwords |
| `role_info` | Stores roles |
| `permission_info` | Stores permissions |
| `grp_info` | Stores user groups |
| `user_role` | Many-to-many: users ↔ roles |
| `role_permission` | Many-to-many: roles ↔ permissions |
| `user_grp` | Many-to-many: users ↔ groups |
| `grp_role` | Many-to-many: groups ↔ roles |

## Setup

### Prerequisites

- Java 21
- MySQL 8
- Maven

### Steps

**1. Clone the repo**

```bash
git clone https://github.com/Mitali-05/UMS.git
cd UMS
```

**2. Create MySQL database**

```sql
CREATE DATABASE umsDb;
```

**3. Configure application properties**

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` and fill in your MySQL credentials and JWT secret.

**4. Run the app**

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:9090`

## API Endpoints

### Auth

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/login` | Public | Login and get JWT token |

### Users

| Method | Endpoint | Permission Required | Description |
|--------|----------|-------------------|-------------|
| GET | `/api/users` | READ_USERS | Get all users |
| GET | `/api/users/{id}` | READ_USERS | Get user by ID |
| POST | `/api/users` | Public | Register new user |
| PUT | `/api/users/{id}` | UPDATE_USERS | Update user |
| DELETE | `/api/users/{id}` | DELETE_USERS | Delete user |

### Roles

| Method | Endpoint | Permission Required | Description |
|--------|----------|-------------------|-------------|
| GET | `/api/roles` | READ_ROLES | Get all roles |
| GET | `/api/roles/{id}` | READ_ROLES | Get role by ID |
| POST | `/api/roles` | CREATE_ROLES | Create role |
| PUT | `/api/roles/{id}` | UPDATE_ROLES | Update role |
| DELETE | `/api/roles/{id}` | DELETE_ROLES | Delete role |

### User Groups

| Method | Endpoint | Permission Required | Description |
|--------|----------|-------------------|-------------|
| GET | `/api/usergrps` | READ_GROUPS | Get all groups |
| GET | `/api/usergrps/{id}` | READ_GROUPS | Get group by ID |
| POST | `/api/usergrps` | CREATE_GROUPS | Create group |
| PUT | `/api/usergrps/{id}` | UPDATE_GROUPS | Update group |
| DELETE | `/api/usergrps/{id}` | DELETE_GROUPS | Delete group |

### Permissions

| Method | Endpoint | Permission Required | Description |
|--------|----------|-------------------|-------------|
| GET | `/api/permissions` | READ_PERMISSIONS | Get all permissions |
| POST | `/api/permissions` | CREATE_PERMISSIONS | Create permission |

## How RBAC Works

1. Permissions are stored in `permission_info` table (e.g. `READ_USERS`, `DELETE_USERS`)
2. Roles are assigned permissions via `role_permission` table
3. Users are assigned roles via `user_role` table
4. On login, JWT token is generated containing the user's permissions
5. On each request, `JwtAuthFilter` extracts permissions from token
6. Each endpoint is protected with `@PreAuthorize("hasAuthority('PERMISSION_NAME')")`

## How to Test

### 1. Register a user

```json
POST /api/users
{
    "user_name": "xyz",
    "email": "xyz@example.com",
    "password": "xyz123",
    "grps": [],
    "roles": []
}
```

### 2. Login

```json
POST /api/auth/login
{
    "user_name": "xyz",
    "password": "xyz123"
}
```

### 3. Use the token

```
GET /api/users
Authorization: Bearer <token>
```

## Error Responses

All errors return a consistent JSON format:

```json
{
    "status": 403,
    "message": "Access Denied",
    "path": "/api/users/1",
    "timestamp": "2026-06-09T11:00:00"
}
```