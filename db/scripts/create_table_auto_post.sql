create table if not exists auto_post
(
    id   serial primary key,
    description varchar,
    created timestamp,
    auto_user_id int references auto_user(id),
    auto_post_id int references auto_post(id),
    car_id int REFERENCES car(id)
);