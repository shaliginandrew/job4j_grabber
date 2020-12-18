create database grabber;

create table post
(
    id serial primary key,
    name text,
    text text,
    link text unique,
    created_date date,
    created_time time
);