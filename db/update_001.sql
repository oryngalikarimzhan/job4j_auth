create table employee (
    id serial primary key not null,
    name varchar(30),
    lastname varchar(30),
    unique_id_number varchar(30),
    hired timestamp
);

create table person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000),
    employee_id int references employee(id)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');

insert into employee(name, lastname, unique_id_number, hired) values ('petr', 'arsentev', '94299322f334234', now());