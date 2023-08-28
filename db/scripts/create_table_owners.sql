CREATE TABLE if not exists owners (
   id serial PRIMARY KEY,
   name text,
   auto_user_id int not null REFERENCES auto_user(id)
);