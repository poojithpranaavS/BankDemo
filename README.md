# BankServlet – Online Banking Management System

A Java Servlet-based online banking application that demonstrates user account management, authentication, deposits, fund transfers, transaction tracking, profile management, feedback, and administrative operations.

> **Project attribution:** This repository is based on the publicly available BankDemo / BankServlet project by the original repository author. The underlying source structure and implementation are retained. This README documents the project and its local deployment setup.

## Overview

BankServlet is a web-based banking application built using Java Servlets, JDBC, MySQL, HTML, CSS, and JavaScript.

The application provides two main roles:

- **Customer/User**
  - Register an account
  - Log in and log out
  - View account information
  - Deposit money
  - Transfer money
  - View transaction history
  - Update profile information
  - Submit feedback

- **Administrator**
  - Log in through the admin interface
  - View registered users
  - Monitor account information
  - Add or deduct funds
  - Monitor banking transactions

## Features

### User Features

- User registration
- User authentication
- Session-based login/logout
- Account dashboard
- Deposit functionality
- Money transfer between accounts
- Transaction history
- Profile viewing and updating
- Contact/feedback form

### Admin Features

- Administrator authentication
- User management
- Account balance management
- Transaction monitoring
- Administrative banking operations

## Technology Stack

| Layer | Technology |
|---|---|
| Frontend | HTML5, CSS3, JavaScript |
| Backend | Java Servlets |
| Database Access | JDBC |
| Database | MySQL |
| Web Server | Apache Tomcat 10.1 |
| Client-side Requests | JavaScript Fetch API |
| Project Format | Eclipse-style Java Web Application |

## Application Architecture

The application follows a traditional Java web application architecture:

    Browser
       |
       | HTTP Requests / Fetch API
       v
    Java Servlets
       |
       | JDBC
       v
    MySQL Database

Servlets handle authentication, account operations, transactions, profile management, and administrative operations. JDBC provides the connection between the Java application and MySQL.

## Project Structure

    BankDemo/
    │
    ├── src/
    │   └── main/
    │       ├── java/
    │       │   └── com/
    │       │       └── ducat/
    │       │           ├── LoginServlet.java
    │       │           ├── SignupServlet.java
    │       │           ├── LogoutServlet.java
    │       │           ├── DepositServlet.java
    │       │           ├── TransferServlet.java
    │       │           ├── GetProfileServlet.java
    │       │           ├── ProfileUpdateServlet.java
    │       │           ├── AdminActionServlet.java
    │       │           ├── ContactusServlet.java
    │       │           ├── DBConnection.java
    │       │           └── MGSamples.java
    │       │
    │       └── webapp/
    │           ├── index.html
    │           ├── login.html
    │           ├── signup.html
    │           ├── dashboard.html
    │           ├── deposit.html
    │           ├── transfer.html
    │           ├── transactions.html
    │           ├── profile.html
    │           ├── admin.html
    │           ├── contact.html
    │           └── sql.txt
    │
    ├── build/
    │   └── classes/
    │
    ├── .classpath
    ├── .project
    ├── LICENSE
    └── README.md

## Database

The application uses a MySQL database named:

    bankproj

The database contains tables for:

- Users
- Bank accounts
- Transactions
- Feedback
- Administrative data

The SQL setup script is available at:

    src/main/webapp/sql.txt

### Create the Database

Start MySQL and import the supplied SQL file:

    mysql -u root < src/main/webapp/sql.txt

Verify the database:

    USE bankproj;
    SHOW TABLES;

## Database Connection

The application connects to MySQL through:

    localhost:3306

The database connection is implemented in:

    src/main/java/com/ducat/DBConnection.java

Configure the database credentials in the connection class or through the appropriate local configuration before running the application.

**Do not commit database passwords, API keys, or other credentials to a public repository.**

## Mailgun Configuration

The project contains Mailgun-related functionality in:

    src/main/java/com/ducat/MGSamples.java

The hard-coded Mailgun API key has been removed from the source before publication.

If Mailgun functionality is required, configure the API key through an environment variable rather than placing the secret directly in source code.

Example:

    API_KEY=<your-mailgun-api-key>

Never commit the actual API key to GitHub.

## Running the Application

### Requirements

Install:

- Java JDK
- MySQL Server
- Apache Tomcat 10.1
- Git

### 1. Clone the Repository

    git clone <your-repository-url>
    cd BankDemo

### 2. Start MySQL

Make sure MySQL Server is running on:

    localhost:3306

### 3. Import the Database

    mysql -u root < src/main/webapp/sql.txt

### 4. Deploy to Tomcat

Copy the application files into the Tomcat deployment directory:

    apache-tomcat/
    └── webapps/
        └── BankServlet/

The compiled Java classes must be available under:

    BankServlet/WEB-INF/classes/

### 5. Start Tomcat

On Windows, run:

    startup.bat

from:

    apache-tomcat/bin/

### 6. Open the Application

Open the following URL in a browser:

    http://localhost:8080/BankServlet/

## Default Administrator

The original project provides a default administrator account for demonstration purposes:

    Username: Admin
    Password: Admin123

For any real deployment, change demonstration credentials and use proper password management.

## Security Considerations

This project is intended primarily as an academic/demo application.

Before production use, additional security measures should be implemented, including:

- Password hashing with a modern password-hashing algorithm
- Secure secret management
- HTTPS/TLS
- CSRF protection
- Strong input validation
- Prepared statements for all database operations
- Proper authorization checks
- Secure session-cookie configuration
- Rate limiting
- Audit logging
- Removal of demonstration credentials
- Secure handling of email/API credentials

## Educational Concepts Demonstrated

This project demonstrates several Java Web Technology concepts:

- HTML/CSS web interfaces
- JavaScript client-side scripting
- Java Servlets
- Servlet annotations
- HTTP request/response handling
- Session management
- JDBC database connectivity
- MySQL integration
- Fetch API / asynchronous requests
- Role-based application functionality
- Server-side business logic
- Web application deployment using Apache Tomcat

## Testing

The application can be tested using the following workflow:

1. Open the application.
2. Create a user account.
3. Log in.
4. Open the dashboard.
5. Check account information.
6. Perform a deposit.
7. Perform a transfer.
8. Check transaction history.
9. Update the profile.
10. Log out.
11. Log in through the administrator interface.
12. Verify administrative operations.

## Screenshots

Screenshots can be added to this section as the project is demonstrated.

Example:

    <img width="1844" height="916" alt="image" src="https://github.com/user-attachments/assets/aa4af05e-c957-44c7-909d-cd9e86fd9217" />

    <img width="795" height="822" alt="image" src="https://github.com/user-attachments/assets/f38e1daa-8d3c-4241-becf-bae23c1b10f3" />

    <img width="957" height="896" alt="image" src="https://github.com/user-attachments/assets/18fbbdf5-e712-41d6-9595-96d6b7d918f5" />


## Repository Notes

This project is maintained for academic demonstration and learning purposes.
