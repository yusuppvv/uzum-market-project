

-- insert into review_entity


CREATE OR REPLACE FUNCTION function_review_rating()
    RETURNS trigger AS
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
UPDATE product
SET rating = (
    SELECT avg(rating)
    FROM review_entity
    WHERE product_id = NEW.product_id
      AND review_entity.visibility = true
)
WHERE id = NEW.product_id;

ELSIF (TG_OP = 'UPDATE') THEN
UPDATE product
SET rating = (
    SELECT avg(rating)
    FROM review_entity
    WHERE product_id = OLD.product_id
      AND review_entity.visibility = true
)
WHERE id = OLD.product_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE TRIGGER trigger_review_rating
    AFTER INSERT OR UPDATE ON review_entity
                                  FOR EACH ROW
                                  EXECUTE FUNCTION function_review_rating();