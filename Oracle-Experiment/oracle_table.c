#查询用户下所有表
select * from user_tables;

#自嗨
create table department(
department_id VARCHAR(5) ,
department_name VARCHAR(20),
CONSTRAINT department_P_cons primary key(department_id)
);
insert into department values (13,'SE');

select * from department;

#holo
create table holo(
holo_id number UNIQUE,
holo_name VARCHAR(10)
);

insert into holo values(123,'hahaha');
insert into holo values(066,'mmpmmp');
insert into holo values(099,'momomo');


update holo set holo_id = 100
      WHERE holo_id = 123;

select * from holo;

#holo1
create table holo1(
holo1_id number,
holo1_name VARCHAR(10),
constraint holo1_fk foreign key(holo1_id)
	references holo(holo_id)
);


insert into holo1 values(066,'heihei');
insert into holo1 values(123,'lalala');
insert into holo1 values(123,'bibibi');
insert into holo1 values(123,'hehehhe');


delete from holo1 where holo1_name='bibibi';

select * from holo1;