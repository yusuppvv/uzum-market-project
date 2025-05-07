CREATE OR REPLACE FUNCTION function_review_rating()
    RETURNS trigger AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
UPDATE product
SET rating = (SELECT avg(rating)
              FROM review
              WHERE product_id = NEW.product_id
                AND review.visibility = true)
WHERE id = NEW.product_id;

ELSIF (TG_OP = 'UPDATE') THEN
UPDATE product
SET rating = (SELECT avg(rating)
              FROM review
              WHERE product_id = OLD.product_id
                AND review.visibility = true)
WHERE id = OLD.product_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE TRIGGER trigger_review_rating
    AFTER INSERT OR UPDATE ON review
                                  FOR EACH ROW

                                  EXECUTE FUNCTION function_review_rating();

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
