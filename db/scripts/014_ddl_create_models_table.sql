CREATE TABLE models (
   id SERIAL PRIMARY KEY,
   brand_id INT NOT NULL REFERENCES brands(id),
   name TEXT NOT NULL
);
