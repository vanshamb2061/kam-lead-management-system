# KAM Lead Management System

## Project Overview
The **KAM (Key Account Manager) Lead Management System** is a Spring Boot application developed for Udaan's B2B e-commerce platform. It enables Key Account Managers to efficiently manage relationships with large restaurant accounts. The system provides comprehensive features including lead and contact management, interaction tracking, call planning, order tracking, and performance analytics.

### Key Features
- **Lead Management**: Manage the entire lifecycle of leads (New → In Progress → Won/Lost).
- **Contact Management**: Store and manage multiple points of contact (POCs) for each lead.
- **Call Planning**: Automate call scheduling and reminders based on customizable frequencies.
- **Interaction Tracking**: Record and view all interactions with leads.
- **Order Management**: Track orders and analyze ordering patterns.
- **Performance Analytics**: Generate insights into account performance with configurable evaluation strategies.

---

## System Requirements

### Prerequisites
- **Java**: Version 17 or later
- **Maven**: Version 3.8+
- **PostgreSQL**: Version 14+ (production environment)
- **Spring Boot**: Version 3.2
- **Git**: Version control system

---

## Installation Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/kam-lead-management.git
   cd kam-lead-management
   ```

   2. **Configure the Database**:

      This project was tested using the H2 in-memory database for development and PostgreSQL can be used for further testing. Below are setup instructions for both:

      ### H2 Database (Development)
       1. By default, the application is configured to use H2. No additional setup is required.
       2. The H2 database configuration in `application.properties` is as follows:
          ```properties
           spring.datasource.url=jdbc:h2:mem:kamdb
           spring.datasource.driver-class-name=org.h2.Driver
           spring.datasource.username=sa
           spring.datasource.password=
           spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
          ```
       3. Access the H2 console at `http://localhost:8080/h2-console` (if enabled in `application.properties`). Use the following credentials:
           - JDBC URL: `jdbc:h2:mem:kamdb`
           - Username: `sa`
           - Password: 

      ### PostgreSQL Database (Production)
       1. Install PostgreSQL and create a database named `kam_db`.
       2. Update the `application.properties` file with the following configuration:
          ```properties
          spring.datasource.url=jdbc:postgresql://localhost:5432/kam_db
          spring.datasource.username=your_username
          spring.datasource.password=your_password
          spring.jpa.hibernate.ddl-auto=update
          spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
          ```

      ### Migrating from H2 to PostgreSQL
       1. Export the H2 database schema and data:
           - Generate a SQL script from the in-memory database.
       2. Import the SQL script into the PostgreSQL database:
          ```bash
          psql -U your_username -d kam_db -f exported_h2_script.sql
          ```
       3. Verify the data and schema consistency in PostgreSQL.

3. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

---

## Running Instructions

### Development Environment
1. Start the application:
   ```bash
   mvn spring-boot:run
   ```
2. Access the application:
    - API Base URL: `http://localhost:8080`
    - H2 Console (if enabled): `http://localhost:8080/h2-console`


## Test Execution Guide
1. Test cases are not added as of now, they will be added later.
### Running Unit Tests
```bash
mvn test
```

---

## API Documentation

### Authentication
All API requests require a JWT token for authentication. To obtain a token, use the `/auth/login` endpoint:

- `POST /auth/login`: Authenticate and generate a JWT token.
    - **Request Body**:
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - **Response**: JWT token.

Example usage of the token:
- Add the token in the `Authorization` header of subsequent API calls:
  ```
  Authorization: Bearer <JWT_TOKEN>
  ```

### Lead Management
- `POST /leads`: Create a new lead.
    - **Request Body**:
      ```json
      {
        "name": "string",
        "address": "string",
        "status": "NEW | IN_PROGRESS | WON | LOST",
        "pointsOfContact": [
          {
            "name": "string",
            "role": "string",
            "email": "string",
            "phone": "string"
          }
        ]
      }
      ```
    - **Response**: Returns the created lead.
- `GET /leads/{id}`: Retrieve lead details by ID.
    - **Response**: Lead details including name, address, status, and POCs.
- `GET /leads/{id}/status`: Get the status of a specific lead.

### Call Planning
- `PUT /call-planner/update-frequency/{leadId}`: Update call frequency for a lead.
    - **Request Body**:
      ```json
      {
        "callFrequency": "daily | weekly | monthly"
      }
      ```
    - **Response**: Updated call frequency details.
- `GET /call-planner/{leadId}`: Get call planning details for a specific lead.
- `GET /call-planner/last-call/{leadId}`: Retrieve the last call date for a lead.
- `GET /call-planner/leads-to-call-today`: Get a list of leads requiring calls today.

### Interaction Tracking
- `POST /interactions/create`: Record a new interaction.
    - **Request Body**:
      ```json
      {
        "leadId": "number",
        "description": "string",
        "date": "YYYY-MM-DDTHH:mm:ss"
      }
      ```
    - **Response**: Details of the created interaction.
- `GET /interactions/lead/{leadId}`: Retrieve interactions for a specific lead.

### Order Management
- `POST /orders`: Create a new order.
    - **Request Body**:
      ```json
      {
        "orderDate": "YYYY-MM-DDTHH:mm:ss",
        "totalAmount": "number",
        "leadId": "number"
      }
      ```
    - **Response**: Details of the created order.
- `GET /orders/lead/{leadId}`: Get all orders associated with a lead.

### Performance Metrics
- `POST /performance/strategy`: Set the performance evaluation strategy.
    - **Query Parameters**: `strategyType=revenue | orders`
    - **Response**: Confirmation of strategy update.
- `GET /performance/well-performing`: Get a list of well-performing accounts.
- `GET /performance/under-performing`: Get a list of underperforming accounts.

---

## Sample Usage Examples
--TODO: Add postman here, and remove curl
### Creating a New Lead
```bash
curl -X POST http://localhost:8080/leads \
-H "Content-Type: application/json" \
-d '{
    "name": "Restaurant ABC",
    "address": "123 Food Street",
    "status": "NEW",
    "pointsOfContact": [
        {
            "name": "John Doe",
            "role": "Owner",
            "email": "john@restaurantabc.com",
            "phone": "1234567890"
        }
    ]
}'
```

### Updating Call Frequency
```bash
curl -X PUT http://localhost:8080/call-planner/update-frequency/1 \
-H "Content-Type: application/json" \
-d '{
    "callFrequency": "weekly"
}'
```

### Recording an Interaction
```bash
curl -X POST http://localhost:8080/interactions/create \
-H "Content-Type: application/json" \
-d '{
    "leadId": 1,
    "description": "Initial meeting with owner",
    "date": "2024-12-25T10:30:00"
}'
```
