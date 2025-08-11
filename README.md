# EPMS - Employee Performance Management System

Run the application:

```
mvn spring-boot:run
```

H2 Console: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:epmsdb
Username: sa
Password: password

Sample endpoints:
GET /api/employees
GET /api/employees/{id}

This project includes sample data in `src/main/resources/data.sql`.
