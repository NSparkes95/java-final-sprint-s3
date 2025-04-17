# Gym Management System – Final Sprint (Winter 2025)

A console-based Java application that simulates a gym management system. Users can register as **Admins**, **Trainers**, or **Members** and interact with features based on their role. Built with **Java**, **PostgreSQL**, and **Maven**, using a clean, menu-driven interface.

---

## 🧑‍💻 User Roles & Features

### Admin
- View all users (including contact info)
- View all memberships and total revenue
- Delete users from the system

### Trainer
- Add, update, and delete workout classes
- View all their assigned classes
- Purchase a gym membership

### Member
- Browse available workout classes
- View their memberships
- Purchase a new membership

---

## 🛠️ Technologies Used
- Java 17
- PostgreSQL
- JDBC
- Maven
- BCrypt (for password hashing)

---

## 🗃️ Database Setup

1. Open pgAdmin (or any SQL client).
2. Run the provided SQL setup script (included in the project directory).
3. This will create and populate the following tables:
   - `users`
   - `memberships`
   - `workoutclasses`

Ensure the database connection is configured correctly in `DatabaseConnection.java`.

---

## ▶️ How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/NSparkes95/java-final-sprint-s3.git
   ```
2. Open the project in **IntelliJ IDEA**, **VS Code**, or any preferred Java IDE.
3. Make sure your PostgreSQL credentials are set in `DatabaseConnection.java`.
4. Run the app:
   ```java
   Run GymApp.java
   ```

You will be prompted to login or register and proceed based on your role.

---

## 📁 Project Structure
```
src/main/java/org/keyin/
├── membership/            // Membership classes & services
├── user/                  // Base User class & DAO
├── user/childclasses/     // Admin, Trainer, Member classes
├── workoutclasses/        // WorkoutClass DAO and service
├── utils/                 // Password hashing (BCrypt)
└── GymApp.java            // Main application
```

---

## 👥 Contributors
- **Christian Rose** – Console menus, Membership system, User documentation, Javadoc, Member functionality
- **Dylan Finlay** – Trainer CRUD & workout class system
- **Nicole Sparkes** – Admin tools, database integration, PostgreSQL setup