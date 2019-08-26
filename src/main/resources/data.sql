---------------------------------------
-- Users
---------------------------------------
-- pass for all: 123
insert into user (id, name, role, email, password, enabled)
values (1, 'guest1', 0, 'user1@gmail.com', '$2a$10$LPWXDhi23gwFk.4tvfv8quqBCTBSAJt9SBXZLRCjz8yJ9.O8gAXOW', true),
       (2, 'admin', 1, 'user2@gmail.com', '$2a$10$LPWXDhi23gwFk.4tvfv8quqBCTBSAJt9SBXZLRCjz8yJ9.O8gAXOW', true);

---------------------------------------
-- Product
---------------------------------------
insert into product (id, name, brand, price, count, cdat)
values (1, 'product1', 0, 10.0, 1, CURRENT_TIMESTAMP),
       (2, 'product2', 1, 11.0, 1, CURRENT_TIMESTAMP);
