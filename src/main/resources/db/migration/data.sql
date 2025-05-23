CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.Employees
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    position VARCHAR(30) NOT NULL,
    format VARCHAR(100) NOT NULL,
    photo VARCHAR NOT NULL,
    email VARCHAR(30),
    tg VARCHAR(30),
    phone BIGINT,
    summary VARCHAR,
    english_level INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Skills(
    id SERIAL PRIMARY KEY,
    skill INT,
    id_employee BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS Educations
(
    id SERIAL PRIMARY KEY,
    year_start INT NOT NULL,
    year_end INT NOT NULL,
    university VARCHAR(100) NOT NULL,
    degree VARCHAR(100) NOT NULL,
    id_employee BIGINT NOT NULL ,
    FOREIGN KEY (id_employee) REFERENCES Employees (id)
);

CREATE TABLE IF NOT EXISTS Projects
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    id_employee BIGINT NOT NULL ,
    FOREIGN KEY (id_employee) REFERENCES Employees (id)
);

CREATE TABLE IF NOT EXISTS Tasks
(
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    id_project INT NOT NULL,
    FOREIGN KEY (id_project) REFERENCES Projects (id)
);

INSERT INTO Employees (name, position,format, photo, email, tg, phone, summary, english_level)
VALUES
('Hanna Lapina','Java Developer','remotely or hybrid from Brest, Belarus','/images/img_1.png',
'alapina840@gmail.com', '@HanaLapina', 375298211966,
'I have been developing applications in Java and Sprig since 2021. I have experience in optimizing high-load microservice applications, as well as creating applications from scratch',
 2),
('Irina Kochetkova','Java Developer','remotely or hybrid from Brest, Belarus','/images/img_1.png',
    'alapina840@gmail.com', '@HanaLapina', 375298211966,
    'I have been developing applications in Java and Sprig since 2021. I have experience in optimizing high-load microservice applications, as well as creating applications from scratch',
3);

INSERT INTO Skills(skill,id_employee)
VALUES (1,1),(2,1),(3,1),(5,1),(7,1),
       (1,2),(3,2),(7,2),(8,2),(10,2);

INSERT INTO Educations
(year_start,year_end, university, degree, id_employee)
VALUES  (2001, 2006, 'Brest State Technical University', 'Civil engineer', 1),
        (2006, 2007, 'Brest State Technical University. Magistracy', 'Master of Technical Sciences',1),
        (2007, 2009, 'Brest State Technical University', 'Software engineer',1),
        (2007, 2009, 'Brest State Technical University', 'Software engineer',2);

INSERT INTO Projects (name, description, id_employee)
VALUES  ('Project for the Ministry of Nature of the Republic of Belarus','Microservice RestFul web application',1),
        ('Project for the Ministry of Transport of the Republic of Belarus','Monolithic RestFul web application',1),
        ('Car leasing project','Microservice desktop application',1),
        ('Car leasing project','Microservice desktop application',2);

INSERT INTO Tasks (description, id_project)
VALUES
    ('Developing a microservice responsible for creating reports (using SpringBoot)', 1),
    ('Outputting reports to Excel and Word using the Apache POI library, working with the Jasper Reports application', 1),
    ('Writing complex queries to the database in native SQL (PostgreSQL)', 1),
    ('Mapping entities to Java objects (Hibernate)', 1),
    ('Determining performance bottlenecks and optimizing SQL queries', 1),
    ('Working with Stream API and multithreading', 1),
    ('Working with message brokers (Kafka)', 1),
    ('Working with a noSQL database (Redis)', 1),
    ('Connecting Swagger for automatic generation of API descriptions', 1),
    ('Code review and code refactoring', 1),
    ('Creating algorithms for statistical and probabilistic calculations', 1),
    ('Assistance to analysts in creating technical specifications', 1),
    ('Creating a demo application from scratch', 1),
    ('Development of calculation algorithms', 1),
    ('Development of an architectural solution using the MVC pattern', 1),
    ('Development of a REST API', 1),
    ('Database design', 1),
    ('Deployment of the database in Docker', 1),
    ('Implementation of all application logic', 2),
    ('Writing build scripts on maven', 2),
    ('Working with multithreading', 2),
    ('Working with message brokers (RabbitMQ)', 2),
    ('Writing JUnit and integration tests (parameterized tests, Mockito, TestContainers)', 2),
    ('Working on SCRUM methodology in a team of 6 people', 2),
    ('Working with multithreading',3),
    ('Working with message brokers (RabbitMQ)',3),
    ('Writing JUnit and integration tests (parameterized tests, Mockito, TestContainers)', 3),
    ('Working on SCRUM methodology in a team of 6 people', 3)
