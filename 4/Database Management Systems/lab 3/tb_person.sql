CREATE TABLE person (id NUMBER(4) NOT NULL, name VARCHAR(20) NOT NULL, year NUMBER(2));


insert into person(id, name, year) values (1,'anna', 15);
insert into person(id, name, year) values (2,'lisa', 16);
insert into person(id, name, year) values (3,'olga', 17);

select * from person;

rman @/u01/dbms/lab3/create_backup.rman
rman @/u01/dbms/lab3/restore_db.rman