# College Complaint and Request Management

A Java Spring Boot web application for college complaint and request management with:

- User and admin login
- Student complaint/request submission
- Request status tracking
- Admin request status updates with notes
- Admin user ID creation and account enable/disable
- MongoDB storage for use with MongoDB Compass
- Animated HTML, CSS, and JavaScript frontend

## Requirements

- Java 21 or newer
- Maven
- MongoDB running locally on `mongodb://localhost:27017`
- MongoDB Compass, if you want to inspect the database visually

## Run

Start MongoDB first, then run:

```powershell
mvn spring-boot:run
```

On this machine, you can also use:

```powershell
.\run-app.ps1
```

Open:

```text
http://localhost:8080
```

## Demo Logins

```text
Admin:   admin / admin123
Student: student / student123
```

The app seeds these accounts automatically when MongoDB is available.

## MongoDB Compass

Connect Compass to:

```text
mongodb://localhost:27017
```

Database:

```text
college_complaints
```

Collections:

```text
users
requests
```
