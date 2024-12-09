
# Real-Time Event Ticketing System

This project is a **Real-Time Event Ticketing System** built in Java. It provides a simulation where vendors release tickets, customers purchase them, and all operations are governed by a centralized configuration. The system includes database persistence for configurations and a CLI for user interaction.

---

## Table of Contents
1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Setup Instructions](#setup-instructions)
4. [CLI Usage Guidelines](#cli-usage-guidelines)
5. [Troubleshooting](#troubleshooting)

---

## Features

- **Configurable Settings**: Configure ticket release rate, customer retrieval rate, total tickets, and maximum capacity.
- **Real-Time Simulation**: Vendors and customers operate concurrently using threads.
- **Database Integration**: Save and load configurations from a MySQL database.
- **Thread-Safe Operations**: Leveraging locks to ensure safe multi-threaded execution.
- **Interactive CLI**: Intuitive interface for managing the simulation.

---

## Prerequisites

1. **Java Development Kit (JDK)**: Version 8 or higher. Check installation with:
   ```bash
   java -version
   ```

2. **Maven**: For dependency management and building the project. Check installation with:
   ```bash
   mvn -version
   ```

3. **MySQL Database**:
    - Ensure MySQL server is installed and running.
    - Create a database named `ticketing_system`.

---

## Setup Instructions

### 1. Clone the Repository
```bash
https://github.com/CodeWhi5per/Ticketing-System-CLI.git
```

### 2. Configure the Database
- Update the database connection details in `CLI.Util.DatabaseUtil`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/";
   private static final String DB_NAME = "ticketing_system";
   private static final String USER = "root";
   private static final String PASSWORD = "password";
   ```

- The application will automatically create the necessary table during runtime.

### 3. Build the Project
Compile the project using Maven:
```bash
mvn clean install
```

### 4. Run the Application
Execute the application with:
```bash
java -cp target/real-time-ticketing-system-1.0.jar CLI.Main
```

---

## CLI Usage Guidelines

### Starting the Application
1. Run the program and enter `START` to initialize the system.
2. Follow the prompts to:
    - Enter system configurations.
    - Save/load configurations to/from the database.

### Simulation Workflow
1. Configure the system parameters (e.g., total tickets, release rate, etc.).
2. Choose the number of vendors and customers.
3. Observe real-time ticket transactions through logs.
4. Stop the simulation when needed.

### Commands
- **START**: Initializes the system.
- **STOP**: Terminates the application.

---

## Troubleshooting

### Common Issues
1. **Database Connection Errors**:
    - Ensure MySQL is running and accessible.
    - Verify the database credentials in `DatabaseUtil`.

2. **Invalid Input**:
    - Enter positive integers for all configurations.

3. **Thread Issues**:
    - If interrupted, restart the simulation.

### Logs
Logs are generated using Log4j and include detailed information about system activity. Check the logs for debugging issues.


