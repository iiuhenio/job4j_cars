CREATE TABLE if not exists history_owners (
   id serial PRIMARY KEY,
   car_id int not null REFERENCES car(id),
   owners_id int not null REFERENCES owners(id)
);