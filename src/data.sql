ALTER TABLE company AUTO_INCREMENT = 1;
ALTER TABLE skill AUTO_INCREMENT = 1
INSERT INTO company (about, email, comp_name, phone, web_url, address)
VALUES ("One of Vietnam's leading technology companies specializing in AI and machine learning solutions",
        'contact@fptai.com', 'FPT AI', '024-7300-7300', 'http://www.fpt.ai', 1),
       ("Vietnam's top software outsourcing company with a focus on global markets", 'info@tma.com.vn', 'TMA Solutions',
        '028-3997-8000', 'http://www.tma.com.vn', 2),
       ('Leading provider of digital transformation and IT services in Southeast Asia', 'sales@vnpt-technology.vn',
        'VNPT Technology', '024-3782-0400', 'http://www.vnpt-technology.vn', 3),
       ('Premier game development and publishing company in Vietnam', 'support@vng.com', 'VNG Corporation',
        '028-3962-3888', 'http://www.vng.com.vn', 4),
       ("Vietnam's top IT consulting and business solutions provider", 'contact@cmc.com.vn', 'CMC Corporation',
        '024-3755-6888', 'http://www.cmc.com.vn', 5),
       ("Leading provider of IT outsourcing and software services with an international presence",
        'hello@kms-technology.com', 'KMS Technology', '028-3811-9977', 'http://www.kms-technology.com', 6),
       ('Specializes in cloud computing, big data, and digital transformation services', 'info@rikkeisoft.com',
        'Rikkeisoft', '024-3247-4288', 'http://www.rikkeisoft.com', 7),
       ('Top cybersecurity company in Vietnam', 'security@bkav.com', 'BKAV Corporation', '024-3763-2552',
        'http://www.bkav.com.vn', 8),
       ('Well-known IT training and software development company', 'training@aptechvietnam.com', 'Aptech Vietnam',
        '024-3851-8765', 'http://www.aptechvietnam.com', 9),
       ('Global IT solutions provider with a strong presence in Vietnam', 'info@fsoft.com.vn', 'FPT Software',
        '024-7300-1866', 'http://www.fsoft.com.vn', 10);

-- Chèn 10 mẫu dữ liệu vào bảng skill
INSERT INTO skill(skill_description, skill_name, type)
VALUES ('Ability to design and manage database systems', 'Database Management', 0),
       ('Experience in cloud platforms like AWS or Azure', 'Cloud Computing', 0),
       ('Knowledge of software testing and quality assurance practices', 'Quality Assurance', 0),
       ('Understanding of network security protocols and practices', 'Network Security', 0),
       ('Proficiency in front-end frameworks such as React or Angular', 'Frontend Development', 0),
       ('Ability to think critically and make data-driven decisions', 'Critical Thinking', 1),
       ('Skill in managing projects with Agile and Scrum methodologies', 'Project Management', 1),
       ('Experience in designing user-friendly interfaces and experiences', 'UI/UX Design', 0),
       ('Understanding of machine learning concepts and algorithms', 'Machine Learning', 0),
       ('Capability to adapt to new technologies and tools quickly', 'Adaptability', 1);

