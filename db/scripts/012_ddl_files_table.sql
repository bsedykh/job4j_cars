CREATE TABLE files (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   path TEXT NOT NULL,
   post_id INT NOT NULL REFERENCES auto_post(id)
);
