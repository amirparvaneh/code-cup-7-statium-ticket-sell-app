INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO users (id, username, password, balance) VALUES (1, 'admin', '$2a$10$TGTo2u0MIYRtEV32XczjXO1CnsE8vFMYDQ6Q7pgrdGfl5c4iuJ9fu', 0);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);