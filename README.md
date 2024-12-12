# Online Role-Playing Game Database System  

## Overview  
This project implements a relational database system for an online role-playing game, progressing from conceptual design to a functional web application. It includes logical modeling, physical implementation, Java-based interaction, and a JSP web interface for user interaction with the data.  

## Key Technologies  
- **Database**: MySQL (schema design, SQL queries)  
- **Backend**: Java (JDBC, DAO pattern)  
- **Frontend**: JSP (dynamic web pages)  
- **Server**: Apache Tomcat  
- **Tools**: MySQL Workbench, IntelliJ IDEA, Lucidchart  

---

## Features  
- **Scalable Relational Database**:  
  - Designed for complex relationships between players, characters, items, and jobs.  
- **Secure CRUD Operations**:  
  - Ensured data integrity and prevented vulnerabilities like SQL injection.  
- **Web Interface**:  
  - Provided user-friendly interaction through JSP for filtering, viewing, and updating data.  
- **Modular Java Design**:  
  - Decoupled logic for scalability and maintainability.  

---

## Project Milestones  

### **Milestone 1: Logical Modeling**  
- Designed an **Entity-Relationship Diagram (ERD)** to represent key entities such as:
  - Players, Characters, Jobs, Inventory, and Items.  
- Applied **Third Normal Form (3NF)** to minimize redundancy and ensure data consistency.  
- Defined relationships using **Crow’s Foot Notation**, specifying primary keys, foreign keys, and unique constraints.  

### **Milestone 2: Physical Modeling**  
- Converted the logical model into **SQL schema** with:  
  - Normalized tables, appropriate data types, and `NOT NULL` constraints.  
  - Referential integrity enforced via **foreign key relationships** with `ON DELETE` and `ON UPDATE` clauses.  
- Populated tables with sample data to support development and testing.  
- Verified the schema's functionality using **MySQL Workbench**.  

### **Milestone 3: Java Data Model and JDBC Integration**  
- Developed **Java classes** for object-relational mapping of database entities (e.g., `Player`, `Character`, `Item`).  
- Implemented **DAO (Data Access Object)** classes to perform CRUD operations.
- Ensured **SQL injection prevention** with prepared statements in JDBC.  
- Built a `Driver` class to test DAO methods and validate functionality.  

### **Milestone 4: Final Implementation**  
- Developed a **JSP web application** deployed on **Apache Tomcat**:  
  - List-based report with filtering and sorting functionality.  
  - Detailed reports combining data from multiple tables.  
  - User-friendly update operations for editable attributes.  
- Integrated backend operations with the frontend, demonstrating seamless interaction between Java and MySQL.  

---

## How to Run  

1. **Database Setup**:  
   - Import the SQL file located in the directory into MySQL.  

2. **Build and Deploy**:  
   - Compile the Java code using Maven or your preferred IDE.  
   - Deploy the WAR file to **Apache Tomcat**.  

3. **Access the Application**:  
   - Navigate to `http://localhost:3306` in your browser.  

---

## Highlights  
This project showcases proficiency in:  
- **Relational Database Design and Implementation**: Comprehensive modeling and secure schema design.  
- **Java Application Development**: Building data models and efficient access layers with JDBC.  
- **Web Application Integration**: Bridging backend functionality with a user-friendly interface.  
- **Best Practices**: Following secure coding standards and ensuring scalability
