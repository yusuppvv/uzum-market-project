

-- insert into review


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



INSERT INTO orders (
    id, created_at, updated_at, visibility, status, total_price, type, user_id
) VALUES (
             '550e8400-e29b-41d4-a716-446655440000',  -- id
             '2025-05-02 10:00:00.000000',           -- created_at
             '2025-05-02 10:05:00.000000',           -- updated_at
             true,                                   -- visibility
             1,                                      -- status
             99.99,                                  -- total_price
             2,                                      -- type
             '6b15732e-6068-464a-8616-abd43a39873e'  -- user_id
         );
