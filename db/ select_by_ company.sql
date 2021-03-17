create database company;

CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);


CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);


insert into company values (1, 'Газпром');
insert into company values (2, 'Альфабанк');
insert into company values (3, 'Втб');
insert into company values (4, 'СеверСталь');
insert into company values (5, 'РостАтом');

select *from company;
select *from person;

insert into person values (1, 'Иванов', 2);
insert into person values (2, 'Петров', 1);
insert into person values (3, 'Сидоров', 3);
insert into person values (4, 'Обухов', 4);
insert into person values (5, 'Зайцев', 5);
insert into person values (6, 'Жидкова', 1);
insert into person values (7, 'Кучаев', 2);
insert into person values (8, 'Пушкин', 2);

select *from person where company_id != 5 ;

-- В одном запросе получить:
-- - имена всех person, которые не состоят в компании с id = 5;
-- - название компании для каждого человека
select p.name as "Имя сотрудника", c.name as "Название компании"
from person p join company c on p.company_id = c.id  where p.company_id != 5;

-- Необходимо выбрать название компании с максимальным количеством человек + количество человек в этой компании
select c.name, count(p.id) from company c join person p on c.id = p.company_id
group by c.name order by count(p.id) desc limit 1;