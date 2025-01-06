-- drop database if exists
drop database if exists acme;

-- create database
-- create database if not exists acme
create database acme;

-- select the database
use acme;

-- create one or more tables
-- you let the person choose their id if the id means smt to them
-- eg social media handles
-- application generated identity vs user supplied identity
-- line below is a print statement
select "Creating employee table...";
create table employees (
    emp_id char(8) not null, -- this is the PK
    name varchar(128) not null,
    salary decimal(10, 2) default 4500,
    dob date not null,
    dept_id int,

    constraint pk_emp_id primary key(emp_id),
    constraint chk_salary check(salary >= 1500)
);

-- create department
create table dept (
    dept_id int auto_increment,
    name varchar(64) not null,

    constraint pk_dept_id primary key(dept_id)
);

select "Creating rsvp table...";
create table rsvp (
    email varchar(50) not null, -- this is the PK
    name varchar(128) not null,
    phone varchar(10) not null,
    confirmation_date date not null,
    comments text,

    constraint pk_email primary key(email)
);

-- line below is a print statement
select "Grant privileges to fred";
grant all privileges on acme.* to 'fred'@'%';
flush privileges;