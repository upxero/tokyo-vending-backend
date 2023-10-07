-- Users
INSERT INTO users (username, password, email, enabled, apikey, profile_picture)
VALUES ('Maxwell', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce', 'maxwell@example.com', TRUE, '123456', 'admin.jpg'),
       ('Mike', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce', 'mike@example.com', TRUE, '123456', 'user.jpg'),
       ('Prattana', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce', 'prattana@example.com', TRUE, '123456', 'prattana.jpg'),
       ('Maxime', '$2a$10$VwlhwMryhmR4ONBkQqU/SOuW/c.rOrxWgJed6ZR0CjgJqU5V1s7ce', 'maxime@example.com', TRUE, '123456', 'maxime.jpg');
-- All passwords: 123456

-- Authority
INSERT INTO authorities (username, authority)
VALUES ('Maxwell', 'ROLE_ADMIN'),
       ('Mike', 'ROLE_USER'),
       ('Prattana', 'ROLE_USER'),
       ('Maxime', 'ROLE_USER');

-- Vending Machines
INSERT INTO vending_machines (id, location, is_open)
VALUES (1, 'Tokyo', true),
       (2, 'Bangkok', false);

-- Categories
INSERT INTO categories (id, name)
VALUES (1, 'Beverages'),
       (2, 'Snacks');

-- Products (Randomly linked to vending machines and categories)
INSERT INTO products (id, name, price, specifications, vending_machine_id, category_id)
VALUES (1, 'Coca-Cola', 1.5, 'Keep in a cool place', 1, 1),
       (2, 'Potato Chips', 2.0, 'Air-sealed package', 2, 2),
       (3, 'Sprite', 1.6, 'Keep in a cool place', 1, 1),
       (4, 'Peanuts', 1.8, 'Sealed for freshness', 2, 2);

-- Carts (Linked to the user)
INSERT INTO carts (id, user_id)
VALUES (1, 'Mike'),
       (2, 'Prattana'),
       (3, 'Maxime');

-- Cart_Products (Products in the cart)
INSERT INTO cart_products (cart_id, product_id)
VALUES (1, 1),  -- Mike's cart has Coca-Cola
       (1, 3),  -- Mike's cart also has Sprite
       (2, 1),  -- Prattana's cart has Coca-Cola
       (2, 3),  -- Prattana's cart also has Sprite
       (3, 2); -- Maxime's cart has Potato Chips

-- Orders
INSERT INTO orders (id, user_id, order_date_time, is_completed, vending_machine_id)
VALUES (1, 'Mike', '2023-10-05T12:00:00', false, 1),
       (2, 'Prattana', '2023-10-06T10:00:00', true, 1),
       (3, 'Maxime', '2023-10-07T15:00:00', true, 2);

-- Order_Products (Products in the order)
INSERT INTO order_product (order_id, product_id)
VALUES (1, 2),  -- Mike's order includes Potato Chips
       (1, 4),  -- Mike's order also includes Peanuts
       (2, 4),  -- Prattana's order includes Peanuts
       (3, 1),  -- Maxime's order includes Coca-Cola
       (3, 3); -- Maxime's order also includes Sprite









