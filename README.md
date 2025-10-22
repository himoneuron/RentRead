Overview
A RESTful API service built using Spring Boot to manage an online Book Rental System while using MySQL to persist the data.

Features
Authentication and Authorization:
Implemented using Basic Auth with two roles: USER and ADMIN.
User Management:
User registration with email, password (encrypted using BCrypt), first name, last name, and role (defaulted to "USER" if not specified).
User login using email and password.
Book Management:
Store and manage book details such as title, author, genre, and availability status.
Browse all available books (accessible to any user).
Create, update and delete books (accessible only to the administrator).
Rental Management:
Rent books with validation to limit users to a maximum of two active rentals.
Return rented books.
Getting Started
Prerequisites
Java 17 or higher
MySQL
Postman for API testing
Installation
Clone the repository:

git clone https://github.com/pgprajwal/RentRead.git
Navigate to the project directory:

cd RentRead
Configuration
Open the application.properties file located in src/main/resources directory.

Configure the MySQL database connection settings:

spring.datasource.url = jdbc:mysql://localhost:3306/rent_read
spring.datasource.username = root
spring.datasource.password = password
Replace localhost, 3306, root and password with your MySQL host, port, username and password respectively.

Save the changes to the application.properties file.

Running the Application
Using Gradle:

./gradlew bootrun
Using a Generated JAR File:

Build the JAR File:
./gradlew build
Run the generated JAR file:
java -jar build/libs/RentRead-0.0.1-SNAPSHOT.jar
API Endpoints
You can find the API endpoints and test them using the provided Postman Collection
