CREATE TABLE owners (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   user_id INT NOT NULL UNIQUE REFERENCES auto_user(id)
);
