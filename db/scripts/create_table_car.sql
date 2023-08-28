CREATE TABLE if not exists car (
   id serial PRIMARY KEY,
   name text,
   engine_id int not null REFERENCES engine(id)
);