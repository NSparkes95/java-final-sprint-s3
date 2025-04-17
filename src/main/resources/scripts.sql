-- Drop table if it exists (for dev resets)
DROP TABLE IF EXISTS memberships CASCADE;
DROP TABLE IF EXISTS workoutclasses CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create users table (if not already done)
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(100) UNIQUE,
    user_phone VARCHAR(20),
    user_address TEXT,
    user_role VARCHAR(20) NOT NULL
);

-- ==== INSERT USERS ====
INSERT INTO users (
    user_name, 
    user_password, 
    user_email, 
    user_phone, 
    user_address, 
    user_role
)
VALUES
('Samantha Bennet', '$2b$12$Qato2z3nbHFiPriCaswIDOireBF4Y3dRos2HZQWATYa40maIORSlq', 'sabennet@gmail.com', '123-456-7890', '123 Admin St', 'Admin'),
('Leo Jordan', '$2b$12$6rwSPkq2AMSuNRE5Q/poBeTP.GOaI/vyrUxNqEGu06yx3DsBfQOv.', 'leojordanr@yahoo.com', '555-111-2222', '10 Fit Lane', 'Trainer'),
('Jordan Mercer', '$2b$12$km6/SffzCZjTZmkMheIv1..s7zR9kT34Lfm68nVKbI0ZsXZmcLwLu', 'jordanm@hotmail.com', '555-333-4444', '20 Pump Rd', 'Trainer'),
('Emily Turner', '$2b$12$VE5vb9Ufhu1NZfBlGke07uRsWY99dm.jSBzoTix2esFq30JTYPMY.', 'eturner@homtmail.com', '777-888-9999', '5 Jog Ave', 'Member'),
('Noah Diaz', '$2b$12$0xcZ2gQXtjokevRLHRWO.e5Dr.IKsLTSocCPKs0BQlfwWLgwkeF9y', 'noahdiaz@gmail.com', '222-333-4444', '6 Spin Blvd', 'Member');



-- Create memberships table
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

-- ==== INSERT MEMBERSHIPS ====

INSERT INTO memberships (
    membership_type,
    membership_description,
    membership_cost,
    member_id,
    start_date,
    end_date,
    is_on_hold,
    payment_method,
    status
)
VALUES
('Monthly', 'Monthly pass for Emily', 49.99, 4, '2025-04-01', '2025-04-30', FALSE, 'Card', 'active'),
('Annual', 'Noah’s annual premium access', 499.99, 5, '2025-01-01', '2025-12-31', FALSE, 'Bank', 'active'),
('Student', 'Emily’s discounted student rate', 29.99, 4, '2025-03-01', '2025-06-01', TRUE, 'Cash', 'on hold'),
('Summer Pass', 'Noah’s seasonal deal', 89.99, 5, '2025-06-01', '2025-08-31', FALSE, 'Card', 'active'),
('Trial', 'Emily’s 7-day trial', 0.00, 4, '2025-04-15', '2025-04-22', FALSE, 'None', 'active');



-- Create workout classes table
CREATE TABLE workoutclasses (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    class_description TEXT,
    class_date DATE NOT NULL,
    class_time TIME NOT NULL,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL,  -- ← this comma was missing
    class_duration INT NOT NULL,
    class_capacity INT NOT NULL,
    class_location VARCHAR(100) NOT NULL,
    class_level VARCHAR(50) NOT NULL,
    class_equipment VARCHAR(100),
    is_completed BOOLEAN DEFAULT FALSE
);

-- ==== INSERT WORKOUT CLASSES ====
INSERT INTO workoutclasses (
    class_name,
    class_description,
    trainer_id,
    class_date,
    class_time,
    class_duration,
    class_capacity,
    class_location,
    class_level,
    class_equipment,
    is_completed
)
VALUES
('Yoga Basics', 'A gentle introduction to yoga for beginners.', 2, '2025-04-10', '09:00:00', '60 minutes' , 20, 'Studio A', 'Beginner', 'Yoga Mat', FALSE),
('HIIT Blast', 'High-intensity interval training for all levels.', 3, '2025-04-11', '18:00:00', '45 minutes', 15, 'Gym Floor', 'Intermediate', 'Dumbbells', FALSE),
('Zumba Dance Party', 'Fun and energetic dance workout.', 2, '2025-04-12', '17:00:00', '60 minutes', 25, 'Dance Studio', 'All Levels', 'None', FALSE),
('Pilates Core Strength', 'Focus on core strength and flexibility.', 3, '2025-04-13', '08:30:00', '50 minutes', 15, 'Studio B', 'Intermediate', 'Reformer', FALSE),
('Spin Class Challenge', 'High-energy cycling class with music.', 2, '2025-04-14', '19:30:00', '45 minutes', 20, 'Cycling Studio', 'All Levels', 'None', FALSE);