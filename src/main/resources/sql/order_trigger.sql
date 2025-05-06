CREATE OR REPLACE FUNCTION update_delivery_status_function()
    RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF NEW.status = 'PAYED' THEN
            INSERT INTO delivery (
                id, created_at, updated_at, visibility, address, status, user_id
            )
            VALUES (
                       gen_random_uuid(),
                       now(),
                       now(),
                       true,
                       'Tashkent, Yunusabad 12',
                       1,
                       NEW.user_id
            );
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_delivery_status_trigger
    AFTER UPDATE ON orders
    FOR EACH ROW
    WHEN (OLD.status IS DISTINCT FROM NEW.status)
EXECUTE FUNCTION update_delivery_status_function();
