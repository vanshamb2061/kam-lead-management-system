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
1. Start the application
2. DataLoader class will insert some sample data into the database
3. Access the application:
    - API Base URL: `http://localhost:8080`
    - H2 Console (if enabled): `http://localhost:8080/h2-console`

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
    - Use the username and password in the Postman Collection for development/testing purpose.

Example usage of the token:
- Add the token in the `Authorization` header of subsequent API calls:
  ```
  Authorization: Bearer <JWT_TOKEN>
  ```

---

### Lead Management
- `POST /leads`: Create a new lead.
    - **Request Body**:
      ```json
      {
        "name": "string",
        "address": "string",
        "status": "NEW | IN_PROGRESS | FOLLOWUP | WON | LOST",
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
- `PUT /leads/{id}/update-status`: Update the status of a specific lead.
    - **Query Parameter**: `newStatus=NEW | IN_PROGRESS | FOLLOWUP | WON | LOST`
    - **Response**: Updated lead details.

---

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

---

### Interaction Tracking
- `POST /interactions/create`: Record a new interaction.
    - **Request Parameters**:
        - `timezone`: Timezone of the interaction.
    - **Request Body**:
      ```json
      {
        "leadId": "number",
        "description": "string",
        "date": "YYYY-MM-DDTHH:mm:ss"
      }
      ```
    - **Response**: Details of the created interaction with the date converted to UTC.
    - Note: Use 'Asia/Kolkata' for India time zone.
- `GET /interactions/lead/{leadId}`: Retrieve interactions for a specific lead.
    - **Request Parameter**: `timezone`: Timezone to convert interaction dates to.
    - **Response**: List of interactions with dates adjusted to the provided timezone.

---

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
    - **Response**: List of orders for the specified lead.

---

### Performance Metrics
- `POST /performance/strategy`: Set the performance evaluation strategy.
    - **Query Parameter**: `strategyType=revenue | orders`
    - **Response**: Confirmation of strategy update.
- `GET /performance/well-performing`: Get a list of well-performing accounts.
- `GET /performance/well-performing/{count}`: Get a specified number of well-performing accounts.
    - **Path Parameter**: `count`: Number of accounts to retrieve.
- `GET /performance/under-performing`: Get a list of underperforming accounts.
- `GET /performance/under-performing/{count}`: Get a specified number of underperforming accounts.
    - **Path Parameter**: `count`: Number of accounts to retrieve.
- `GET /performance/orders/dates/{leadId}`: Retrieve order dates for a specific lead.
- `GET /performance/orders/average-monthly/{leadId}`: Get the average number of orders per month for a specific lead.

---

## Sample Usage Examples
### Using Postman
1. Import the provided Postman collection (`postman_collection.json`) into your Postman workspace.
2. Use pre-configured requests to test each endpoint:
    - Create leads.
    - Update call frequencies.
    - Record interactions.
    - Fetch performance metrics.
    - Authenticate and obtain JWT tokens.

