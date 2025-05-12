INSERT INTO users
    (id, full_name, email, role, status, visibility, created_at, updated_at)
VALUES (gen_random_uuid(), 'Behruz', 'example@gmail.com', 'SELLER', 'ACTIVE', true, now(),  now());