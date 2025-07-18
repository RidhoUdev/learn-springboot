# Learn API - Spring Boot REST API

A REST API application for product management built with Spring Boot and MySQL database.

## Technologies Used

- Java 17
- Spring Boot 3.5.3
- Maven
- Spring Data JPA
- MySQL Database
- Spring Web
- Lombok

## System Requirements

Before running the application, make sure you have installed:

- Java Development Kit (JDK) 22 or higher
- Maven
- MySQL Server

## Installation and Configuration

### 1. Clone Repository

```bash
git clone https://github.com/RidhoUdev/learn-api.git
cd learn-api
```

### 2. Database Configuration

#### Create MySQL Database

1. Access MySQL console:
```bash
mysql -u root -p
```

2. Create new database:
```sql
CREATE DATABASE learn_api;
```

3. Create new table:
```sql
CREATE TABLE product (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    product_Name VARCHAR(255) DEFAULT NULL,
    harga INT(11) DEFAULT NULL,
    PRIMARY KEY (id)
);
```

#### Configure Database Connection

Edit file `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/learn_api
spring.datasource.username=learn_user
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
```

### 3. Install Dependencies

```bash
mvn clean install
```

### 4. Running the Application

#### Using Maven

```bash
mvn spring-boot:run
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── learnapi/
│   │               ├── LearnApiApplication.java
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── handler/
│   │               ├── model/
│   │               ├── repository/
│   │               └── service/
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
└── test/
```

## API Endpoints

After the application is running, you can access the API at `http://localhost:8080`

### Product Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product by ID |
| DELETE | `/api/products/{id}` | Delete product by ID |

### File Storage Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/files/upload` | Upload file |
| GET | `/api/files/{filename}` | Download file |
