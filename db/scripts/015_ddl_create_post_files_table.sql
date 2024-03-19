CREATE TABLE post_files (
   post_id INT REFERENCES auto_post(id),
   file_id INT REFERENCES files(id),
   PRIMARY KEY(post_id, file_id)
);
