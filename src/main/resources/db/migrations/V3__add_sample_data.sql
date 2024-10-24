INSERT INTO user (age, email, first_name, last_name)
VALUES (25, 'john.doe@example.com', 'John', 'Doe'),
       (30, 'jane.smith@example.com', 'Jane', 'Smith'),
       (28, 'michael.brown@example.com', 'Michael', 'Brown'),
       (35, 'emily.jones@example.com', 'Emily', 'Jones'),
       (22, 'robert.johnson@example.com', 'Robert', 'Johnson');

INSERT INTO advert (created_date, description, end_date, is_active, title, user_id, category)
VALUES ('2024-10-22', 'Spacious apartment for rent', '2024-11-21', 1, 'Apartment for Rent', 1, 'REAL_ESTATE'),
       ('2024-10-05', 'Selling a used car in great condition', '2024-11-04', 1, 'Used Car for Sale', 1, 'VEHICLES'),
       ('2024-09-25', 'Job opening for software developer', '2024-10-25', 1, 'Software Developer Job', 2, 'JOBS'),
       ('2024-09-30', 'Brand new smartphone for sale', '2024-10-30', 1, 'Smartphone for Sale', 2, 'ELECTRONICS'),
       ('2024-10-12', 'Offering cleaning services for homes', '2024-11-11', 1, 'Cleaning Services', 3, 'SERVICES'),
       ('2024-09-28', 'Luxury house for sale', '2024-10-28', 1, 'Luxury House for Sale', 3, 'REAL_ESTATE'),
       ('2024-10-08', 'Looking for a mechanic', '2024-11-07', 1, 'Mechanic Job Opportunity', 4, 'JOBS'),
       ('2024-10-15', 'Professional moving services available', '2024-11-14', 1, 'Moving Services', 4, 'SERVICES'),
       ('2024-09-18', 'Selling a modern apartment', '2024-10-18', 0, 'Modern Apartment for Sale', 5, 'REAL_ESTATE'),
       ('2024-09-23', 'Latest gaming console for sale', '2024-10-23', 0, 'Gaming Console for Sale', 5, 'ELECTRONICS');