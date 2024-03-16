CREATE TABLE files (
   post_id INT REFERENCES auto_post(id),
   files_order INT,
   name TEXT NOT NULL,
   path TEXT NOT NULL,
   PRIMARY KEY(post_id, files_order)
);
