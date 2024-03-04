CREATE TABLE history_owners (
   id SERIAL PRIMARY KEY,
   car_id INT NOT NULL REFERENCES car(id),
   owner_id INT NOT NULL REFERENCES owners(id),
   start_at TIMESTAMP,
   end_at TIMESTAMP,
   UNIQUE (car_id, owner_id)
);
