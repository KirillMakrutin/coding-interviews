# Multi-Tenant Marketplace API

## General Description

This is a coding task to implement a **multi-tenant marketplace API** using Java, Spring Boot, and a
SQL database.
The goal is to test your understanding of:

- Spring Boot web applications
- Multi-module Maven projects
- [Multi-tenancy concepts](https://www.baeldung.com/multitenancy-with-spring-data-jpa)
- Messaging systems
- Business logic and data integrity

The application consists of two modules:

1. **Application Module** – the main Spring Boot web application, containing APIs, persistence, and
   core business logic.
2. **Notification Module** – handles notification events from the message broker and outputs email
   content to the console.

The estimated completion time is **30 minutes**, and candidates are allowed to use AI assistance to
complete the task.

---

## Project Requirements

### Project Structure

- **Maven multi-module project**
    - `application` – Spring Boot application, REST APIs, database, business logic
    - `notification` – notification handling module (console output)
- **Database:** any SQL database (PostgreSQL, MySQL, SQL Server)
- **Message Broker:** any messaging solution (RabbitMQ, Kafka, Azure Service Bus, etc.)

---

## Functional Requirements

### 1. Marketplace admin create a store

- **Entity:** `Store` (tenant)
    - Fields: `name` (unique), `code` (unique, generated: incremental number with `S` prefix),
      `password` (generated)
    - Immutable after creation (cannot be updated)
- **API:** `POST` to create a store that accepts `name` in the request body
- **Business Logic:**
    - Validate that no store exists with the same `name`
    - Publish a message to a message broker queue to create the store
- **Broker Consumer:**
    - Generate store code (incremental number with `S` prefix) and random password
    - Push an event to the **notification queue** with store details (`name`, `code`, `password`)
- **Notification Module:**
    - Print the email content to the console (simulate sending email)

---

### 2. Store admin create a product in the Store space

- **Entity:** `Product`
    - Fields: `name` (immutable), `description` (immutable), `quantity` (modifiable, ≥0)
- **API:** `POST` with store to create a product under a specific store `code` in the target store tenant space
- **Security:** Headers `x-username={code}`, `x-password={password}`)
- **Request Interceptor:**
  - Validate **Auth** headers with store code as username and store password
  - Return `401 Unauthorized` if invalid
- **Business Logic:**
    - Validate that no store exists with the same product `name`
    - Create product in the store's tenant space
- **Business Rules:**
    - `name` and `description` cannot be updated after creation

---

### 3. Marketplace admin create marketplace users

- **Entity:** `User`
    - Fields: `username` (unique), `password` (randomly generated)
- **API:** `POST` to create a user that accepts `username` in the request body
- **Business Logic:**
    - Validate that no user exists with the same `username`
    - Generate a random password for the user
    - Publish a notification message to the notification queue with username and password
- **Notification Module:**
    - Notification module prints email content to console

---

### 4. Users view products

- **API Endpoints:**
    - `GET` stores → list all stores
    - `GET` products → list all products across all stores
    - `GET` store products → list products for a specific store

---

### 5. Users buy a store product

- **API:** `POST` to buy a product from a specific store with store
- **Security:** Headers `x-username={username}`, `x-password={password}`)
- **Request Interceptor:**
  - Validate **Auth** headers with user credentials
  - Return `401 Unauthorized` if invalid
- **Business Logic:**
    - Decrease product quantity by 1
    - Publish notification message with purchase details to the notification queue
    - Associate purchase with the user
- **Error Case:**
    - If product name contains `"error"` (case-insensitive), throw a server-side error

---

### 6. Users view their purchases
- **API:** `GET` to view all purchases made by the authenticated user
- **Security:** Headers `x-username={username}`, `x-password={password}`)
- **Request Interceptor:**
  - Validate **Auth** headers with user credentials
  - Return `401 Unauthorized` if invalid
- **Business Logic:**
    - Return all purchases associated with the user

---

## Non-Functional / Additional Requirements

- **Multi-tenancy:** any approach (schema-per-tenant, table-per-tenant, or row-level tenant ID)
- **Random generators:**
    - Store code: incremental number prefixed with `S`
    - Passwords: random alphanumeric strings
- **Security:** Custom headers and request interceptors
- **Immutability:** certain entity fields must not be updated after creation
- **Messaging:** all notifications/events go through message broker queues
- **Notification Module:** console output simulates emails

---

## Hints

- Use **Spring Data JPA** for persistence
- Use **Spring Boot starter for messaging**
- **Multi-module Maven:** application module depends on notification module for event contracts (not
  implementation)
- No frontend required; API-only implementation is sufficient
- Focus on correctness and code quality over completeness
- Use AI assistance as needed
- **Docker** is preferred for database and message broker setup

---

