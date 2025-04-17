# Gym Management System 🏋️‍♀️

A Java-based console application for managing gym memberships, workout classes, and user roles (Admin, Trainer, Member). The system supports user registration/login, class scheduling, and membership management using PostgreSQL.

---

## 💡 Features

- 🔐 User registration and login (BCrypt password encryption)
- 👩‍🏫 Role-based functionality: Admin, Trainer, Member
- 📅 Add/view/update/delete workout classes
- 💳 Manage memberships (Monthly, Annual, Trial)
- 📁 JDBC PostgreSQL integration
- 🔒 Input validation and error handling

---

## 🧱 Technologies Used

- Java 17
- JDBC
- PostgreSQL
=======
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
>>>>>>> origin/main
- Maven
- BCrypt (for password hashing)

---

## 🗂️ Project Directory Structure
gym-management-system/ │ 
├── src/ │ ├── main/ 
│ │ ├── java/ 
│ │ │ └── org/keyin/ 
│ │ │ ├── app/ # Main GymApp.java 
│ │ │ ├── dao/ # DAO interfaces & JDBC implementations 
│ │ │ ├── model/ # User, Admin, Trainer, Member, WorkoutClass, Membership 
│ │ │ ├── service/ # Business logic (UserService, ClassService, etc.) 
│ │ │ └── util/ # Utility classes (e.g., DatabaseConnection.java) 
│ │ └── resources/ # Config files 
│ ├── .gitignore 
├── pom.xml # Maven build file 
└── README.md # You're here!

---

## ⚙️ How to Build & Run

```bash
# Clone the repo
git clone https://github.com/NSparkes95/java-final-sprint-s3/tree/main
cd java-final-sprint-s3

# Build using Maven
mvn clean install

# Run the project
mvn exec:java -Dexec.mainClass="org.keyin.app.GymApp" 

 🐘 PostgreSQL Setup

Create the database:
CREATE DATABASE gym_management;
Connect and run schema:
-- Users table
CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  user_name VARCHAR(100) NOT NULL,
  user_email VARCHAR(100) UNIQUE NOT NULL,
  user_password VARCHAR(255) NOT NULL,
  user_role VARCHAR(20) NOT NULL
);

-- Workout classes table
CREATE TABLE workoutclasses (
  class_id SERIAL PRIMARY KEY,
  class_name VARCHAR(100),
  class_description TEXT,
  class_level VARCHAR(50),
  class_duration INTEGER,
  class_capacity INTEGER,
  class_date DATE,
  class_time TIME,
  class_location VARCHAR(100),
  class_equipment VARCHAR(100),
  trainer_id INT REFERENCES users(user_id) ON DELETE CASCADE,
  is_completed BOOLEAN DEFAULT FALSE
);

-- Memberships table
CREATE TABLE memberships (
  membership_id SERIAL PRIMARY KEY,
  membership_type VARCHAR(100) NOT NULL,
  membership_description TEXT,
  membership_cost NUMERIC(10, 2) NOT NULL,
  member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_on_hold BOOLEAN DEFAULT FALSE,
  payment_method VARCHAR(100),
  status VARCHAR(50)
);

▶️ Running the App

Make sure PostgreSQL is running.
Configure your database credentials in DatabaseConnection.java:
private static final String URL = "jdbc:postgresql://localhost:5432/gym_management";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
=======
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
>>>>>>> origin/main
