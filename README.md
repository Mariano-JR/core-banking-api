# Core Banking API 🏦

![Java](https://img.shields.io/badge/java-21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring_boot-4.0.6-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-%232D373B.svg?style=for-the-badge)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

> A robust RESTful API simulating a core banking ledger, focused on transactional integrity, clean architecture, and containerization.

## 📖 About the Project
This project is a backend service designed to handle core financial operations, such as user onboarding, account management, and secure transactions. It is built to demonstrate corporate-level Java development, emphasizing robust business rules and data consistency.

## 🚀 Technologies Used
* **Java 21**
* **Spring Boot 4**
* **Spring Data JPA / Hibernate**
* **H2 Database** (In-memory for development)
* **Maven**
* **Docker** (Multi-stage build)

## ⚙️ How to Run (Getting Started)

### 1. Running Locally (Development Mode)
1. Clone the repository: 
    ```bash
    git clone https://github.com/Mariano-JR/core-banking-api.git
    ```
2. Import the project into your preferred IDE (IntelliJ IDEA Ultimate recommended).
3. Run the `LedgerApiApplication.java` file.
4. Access the H2 Database visual console at `http://localhost:8080/h2-console`.

### 2. Running via Docker (Production Simulation)
1. Build the Docker image:
    ```bash
    docker build -t core-banking-api .
    ```
2. Run the container:
    ```bash
   docker run --name banking-api -p 8080:8080 core-banking-api
   ```

## 🗺️ Roadmap (Next Steps)
Here is the planned evolution for this financial architecture:

- [x] **Phase 1:** Initial Setup & Entity Mapping (User Entity with UUIDs).
- [x] **Phase 2:** JPA Repositories, Services, and REST Controllers implementation.
- [x] **Phase 3:** Financial Domain (Account Entity and database relationships).
- [ ] **Phase 4:** Transaction Engine (ACID compliance with `@Transactional`).
- [ ] **Phase 5:** Global Exception Handling (`@ControllerAdvice`) & Data Validation.
- [ ] **Phase 6:** Cloud Deployment (AWS Integration).