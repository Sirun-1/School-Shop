# Online Shop (School Shop)

A simple e-commerce website built as a personal project for the **PRJ301 – Java Web Application Development** course at FPT University.

## Description

A basic web application for online shopping that supports product, category, and user management, along with order processing and basic role-based access control (Role/Permission).

## Tech Stack

- **Language:** Java (JSP, Servlet)
- **Database:** SQL Server
- **Server:** Apache Tomcat
- **Architecture:** Model – DAO – Controller (basic MVC)
- **IDE:** NetBeans (Ant project)

## Key Features

- User registration / login
- Product management (create / update / delete / list)
- Category management
- User management with basic role-based permissions
- Shopping cart and order processing (Cart, Order, OrderItem)

## Project Structure

```
Online Shop/
├── src/java/
│   ├── control/      # Servlets handling requests
│   ├── dal/          # Data Access Layer - database connection & queries
│   └── model/        # Model classes (User, Product, Order, ...)
├── web/
│   ├── jsp/          # JSP pages
│   └── WEB-INF/      # Configuration, ConnectDB.properties
├── SchoolShop.sql    # Database initialization script
└── build.xml         # Build configuration (Ant)
```

## Getting Started

1. Install **SQL Server** and run `SchoolShop.sql` to initialize the database
2. Open the project in **NetBeans** (Ant-based Java Web project)
3. Configure the database connection in `web/WEB-INF/ConnectDB.properties`:
   ```
   url=jdbc:sqlserver://localhost:1433;databaseName=SchoolShop;trustServerCertificate=true
   userID=YOUR_USER_HERE
   password=YOUR_PASSWORD_HERE
   ```
4. Deploy and run the project on **Apache Tomcat** through NetBeans

## Note

This is a personal project developed during coursework to practice Java Web development (JSP/Servlet), relational database operations, and building applications with a basic MVC architecture.
