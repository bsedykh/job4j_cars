CREATE TABLE history_owners (
   id SERIAL PRIMARY KEY,
   car_id INT REFERENCES car(id),
   owner_id INT REFERENCES owners(id),
   history_id INT REFERENCES history(id),
   UNIQUE (car_id, owner_id)
);
