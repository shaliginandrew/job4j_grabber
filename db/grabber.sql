create database grabber;

create table post
(
    id serial primary key,
    link text unique,
    name text,
    description text,
    create_date date,
    create_time time
);