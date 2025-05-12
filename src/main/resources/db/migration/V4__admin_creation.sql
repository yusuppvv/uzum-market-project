INSERT INTO users
    (id, full_name, email, role, status, visibility, created_at, updated_at)
VALUES (gen_random_uuid(), 'Azizbek Yusupov', 'azizbeky250@gmail.com', 'ADMIN', 'ACTIVE', true, now(),  now());
