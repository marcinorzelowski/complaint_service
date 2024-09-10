# **Complaint REST Service**


> 
> This project is designed to show the knowledge of REST, Spring Boot and SQL technologies.



## **Project Overview**

Small REST project to manage complaints. It is allowing user to create, edit and read complaints. No authentication is needed.


## **Features**

- Feature 1: Adding new complaints. When the complaint with given author and product already exists in the database, system increase the requestCounter by 1. Otherwise, the new complaint is created. Country field is automatically filled based on IP address location.
- Feature 2: Edition of complaints. Allows to edit text of an existing complaint.
- Feature 3: Reading of existing complaints. Allows to read the list of complaints.

## **Getting Started**

### **Prerequisites**

Ensure you have the following installed on your system:

- Java 21+
- Maven


### **Installation and launching the project**

Clone the repository:

```bash
git clone https://github.com/marcinorzelowski/complaint_service.git
```

Start the project:
```bash
mvn spring-boot:run
```

Run tests:

```bash
mvn test
```

### API Documentation

You can find an API documentation at following address after starting the project:
http://localhost:8080/swagger-ui/


