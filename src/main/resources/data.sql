-- Gebruikers toevoegen
INSERT INTO users (username, password, email, enabled, apikey, profile_picture)
VALUES ('user', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce','user@example.nl', TRUE, '654321', 'user.jpg'),
       ('admin', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce', 'admin@example.nl', TRUE, '654321', 'admin.jpg');
-- Alle passwords -> 123456

-- Autoriteiten toevoegen
INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN');


