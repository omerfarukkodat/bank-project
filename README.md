# Bank Project

This is a Spring Boot-based banking application that provides various functionalities such as user account management, balance enquiry, credit/debit transactions, fund transfers, and bank statement generation in PDF format. The application also supports email notifications.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Dependencies](#dependencies)
- [License](#license)

## Features

- **User Account Management**: Create and delete user accounts.
- **Balance Enquiry**: Check account balance.
- **Fund Transactions**: Credit and debit operations.
- **Fund Transfer**: Transfer funds between accounts.
- **Bank Statement**: Generate and email PDF statements of transactions within a date range.
- **Authentication**: User login functionality.

## Getting Started

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- An email account for sending emails (e.g., Gmail)

### Installation

1. Clone the repository:
   ```bash
     git clone https://github.com/yourusername/bank-project.git
     cd bank-project

2. Update the database configuration in application.properties:
   ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/bank
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password

3. Update the email configuration in application.properties:
   ```bash
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_email_password_app_key
   
4. Build the project using Maven:
   ```bash
   mvn clean install
### Running the Application

Running the Application

5. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
The application will start on http://localhost:8080.

## API Endpoints
### User Account Management
- Create New User Account

  - POST /api/v1/user
  - Request Body: UserRequest
  - Response: BankResponse
  - Delete User Account

- DELETE /api/v1/user/delete
  - Request Body: EnquiryRequest
  - Response: String
### Balance Enquiry
- Get Balance
  - GET /api/v1/user/balanceEnquiry
  - Request Body: EnquiryRequest
  - Response: BankResponse
### Name Enquiry
- Get User Name
  - GET /api/v1/user/nameEnquiry
  - Request Body: EnquiryRequest
  - Response: String
### Transactions
- Credit Account

  - POST /api/v1/user/credit
  - Request Body: CreditDebitRequest
  - Response: BankResponse
- Debit Account

  - POST /api/v1/user/debit
  - Request Body: CreditDebitRequest
  - Response: BankResponse
- Transfer Funds

  - POST /api/v1/user/transfer
  - Request Body: TransferRequest
  - Response: BankResponse
 ### Authentication
- Login
  - POST /api/v1/user/login
  - Request Body: LoginDTO
  - Response: BankResponse
## Configuration
The application configuration is managed in the application.properties file.
  ```bash
spring.application.name=bank-project
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/bank
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

app.jwt-secret=your_jwt_secret_key
app.jwt-expiration=86400000
```
## Dependencies
The project uses the following dependencies:

 - Spring Boot Starter Data JPA
 - Spring Boot Starter Web
 - PostgreSQL
 - Lombok
 - Spring Boot Starter Mail
 - Springdoc OpenAPI
 - iTextPDF
 - Spring Boot Starter Security
 - JJWT (Java JWT: JSON Web Token for Java and Android)
 For a complete list, refer to the pom.xml file.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
