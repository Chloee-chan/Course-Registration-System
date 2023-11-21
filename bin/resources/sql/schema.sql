drop database if exists course_system_management;

create database course_system_management;

USE course_system_management;

create table account (
    username varchar(255) primary key,
    password varchar(255),
    role varchar(10)
);

create table student(
	id varchar(255) primary key,
    name varchar(50),
    address varchar(255),
    phone varchar(10),
    email varchar(50),
    class varchar(20),
    account_id varchar(255),
    constraint foreign key (account_id) references account(username) on delete cascade
);

create table faculty (
	id varchar(255) primary key,
    name varchar(50),
    address varchar(255),
    phone varchar(10),
    email varchar(50),
    department varchar(20),
    account_id varchar(255),
    constraint foreign key (account_id) references account(username) on delete cascade
);

create table course (
	id varchar(255) primary key,
    name varchar(50),
    credit int,
    max_student int,
    faculty_id varchar(255),
    foreign key (faculty_id) references faculty(id)
);

create table register (
	student_id varchar(255),
    course_id varchar(255),
    foreign key (student_id) references student(id) on delete cascade,
    foreign key (course_id) references course(id) on delete cascade,
    constraint primary key(student_id, course_id)
);