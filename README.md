**Online Shop (School Shop)**
A personal Java web application project simulating a basic e-commerce system, developed to practice backend development and MVC architecture.

**Overview**
This project demonstrates the development of a simple online shopping platform, including user authentication, product management, and order processing. It focuses on applying core Java web technologies and structuring a maintainable web application.

**Key Features**

* User registration and login system
* Product and category management (CRUD operations)
* Role-based user management (basic authorization)
* Shopping cart and order processing (Cart, Order, OrderItem)
* Database interaction using DAO pattern

**Tech Stack**

* **Backend:** Java (JSP, Servlet)
* **Database:** SQL Server
* **Server:** Apache Tomcat
* **Architecture:** MVC (Model – DAO – Controller)
* **Tools:** NetBeans (Ant-based project)

**Project Structure**

Online Shop/
├── src/java/
│   ├── control/      # Servlet controllers
│   ├── dal/          # Data access layer (DAO)
│   └── model/        # Entity classes
├── web/
│   ├── jsp/          # View layer (JSP)
│   └── WEB-INF/      # Configuration files
├── SchoolShop.sql    # Database script
└── build.xml         # Ant build file

**Setup Instructions**

1. Run `SchoolShop.sql` on SQL Server
2. Configure database connection in `ConnectDB.properties`
3. Open in NetBeans and deploy to Apache Tomcat

**Notes**
This is a self-developed project aimed at strengthening understanding of Java web development, MVC design patterns, and database-driven applications.
