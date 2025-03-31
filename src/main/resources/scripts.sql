-- Create users table (if not already done)
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(100),
    user_phone VARCHAR(20),
    user_address TEXT,
    user_role VARCHAR(20) NOT NULL
);

-- Create memberships table
CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    membership_type VARCHAR(50) NOT NULL,
    membership_description TEXT,
    membership_cost DECIMAL(10, 2) NOT NULL,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_on_hold BOOLEAN DEFAULT FALSE
);

-- ==== INSERT USERS ====
INSERT INTO users (
    user_name, 
    user_password, 
    user_email, 
    user_phone, 
    user_address, 
    user_role)
VALUES
('Samantha Bennet', '$2b$12$Qato2z3nbHFiPriCaswIDOireBF4Y3dRos2HZQWATYa40maIORSlq', 'sabennet@gmail.com', '123-456-7890', '123 Admin St', 'Admin'),
('Leo Jordan', '$2b$12$6rwSPkq2AMSuNRE5Q/poBeTP.GOaI/vyrUxNqEGu06yx3DsBfQOv.', 'leojordanr@yahoo.com', '555-111-2222', '10 Fit Lane', 'Trainer'),
('Jordan Mercer', '$2b$12$km6/SffzCZjTZmkMheIv1..s7zR9kT34Lfm68nVKbI0ZsXZmcLwLu', 'jordanm@hotmail.com', '555-333-4444', '20 Pump Rd', 'Trainer'),
('Emily Turner', '$2b$12$VE5vb9Ufhu1NZfBlGke07uRsWY99dm.jSBzoTix2esFq30JTYPMY.', 'eturner@homtmail.com', '777-888-9999', '5 Jog Ave', 'Member'),
('Noah Diaz', '$2b$12$0xcZ2gQXtjokevRLHRWO.e5Dr.IKsLTSocCPKs0BQlfwWLgwkeF9y', 'noahdiaz@gmail.com', '222-333-4444', '6 Spin Blvd', 'Member');

-- ==== INSERT MEMBERSHIPS ====

INSERT INTO memberships (
    membership_type,
    membership_description,
    membership_cost,
    member_id,
    start_date,
    end_date,
    is_on_hold
)
VALUES
('Monthly', 'Monthly pass for Emily', 49.99, 4, '2025-04-01', '2025-04-30', FALSE),
('Annual', 'Noah’s annual premium access', 499.99, 5, '2025-01-01', '2025-12-31', FALSE),
('Student', 'Emily’s discounted student rate', 29.99, 4, '2025-03-01', '2025-06-01', TRUE),
('Summer Pass', 'Noah’s seasonal deal', 89.99, 5, '2025-06-01', '2025-08-31', FALSE),
('Trial', 'Emily’s 7-day trial', 0.00, 4, '2025-04-15', '2025-04-22', FALSE);