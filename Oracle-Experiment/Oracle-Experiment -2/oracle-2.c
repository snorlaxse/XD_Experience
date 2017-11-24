#主键约束:实际上强制认为是UNIQUE和NOT NULL约束的组合
create table a (
  t1    char(10)   primary key,
  t2    number(2),
  t3    number(4) unique not null );

insert into a values ('123', 1, 1);
insert into a values (null, 2, 2);
insert into a values ('123', 2, 2);
insert into a values ('234', 2, null);


#约束类型-NOT NULL
create table NN (
  t1    char(10)   not null,
  t2    number(2) );

select constraint_name, search_condition, constraint_type
from user_constraints where table_name = 'NN';

insert into NN values ( '123', 1 );
insert into NN values ( null, 2);
insert into NN values ( '234', null);

#约束类型- DEFAULT
create table foo (
  col1        number,
  col2        number default 1);

insert into foo (col1) values (17);
insert into foo values (18, null);
select * from foo;

#约束类型- UNIQUE
create table holo(
holo_id number UNIQUE,
holo_name VARCHAR(10)
);

insert into holo values(123,'hahaha');
insert into holo values(123,'heiheihei');

select * from holo;

#约束类型- CHECK
 alter table holo
 	add constraint holo_id 
 		check(holo_id<=200);

insert into holo values(233,'mmp');

#将约束应用于不符合条件的数据
alter table customers
  add constraint customer_dob_ck
  check ( date_of_birth > ‘1-jan-1900’
        and date_of_birth < ‘1-jan-2000’ );

select to_char(max(date_of_birth), ‘dd-mon-yyyy’)
from  customers;

#约束类型- FOREIGN KEY
create table holo1(
holo1_id number,
holo1_name VARCHAR(10),
constraint holo1_fk foreign key(holo1_id)
	references holo(holo_id)
);

insert into holo1 values(123,'hehehhe');
insert into holo1 values(233,'hehehhe');

create table foo (
  foo_key        number  primary key,
  foo_custno  number,
  constraint foo_cust_fk foreign key ( foo_custno)
    references oe.customers ( customer_id ) );

create table foo (
  foo_key        number  primary key,
  foo_custno  number,
constraint foo_cust_fk foreign key ( foo_custno)
   references oe.customers );

create table foo (
  foo_key        number  primary key,
  foo_custno  number constraint foo_cust_fk references oe.customers );

create table foo_notifications (
  foo_nfy_key        number  primary key,
  foo_nfy_email     varchar2(25)
  constraint foo_email_fk 
  references hr.employees ( email ) );

create table foo1 (
  foo_key        number  primary key,
  foo_custno  number,
constraint foo1_cust_fk foreign key ( foo_custno)
   references oe.customers on delete cascade );

create table foo1 (
  foo_key        number  primary key,
  foo_custno  number,
constraint foo1_cust_fk foreign key ( foo_custno)
   references oe.customers on delete set null );

insert into foo values ( 1, 9 );
insert into foo values ( 1, 144 );
delete customers where customer_id=144;
alter table orders disable constraint orders_customer_id_fk;
delete from customers where customer_id=144;



##对象数据类型中使用约束

create type person_type as object(
 first_name           varchar2(25),
  last_name            varchar2(30),
  birthdate            date,
  work_phone        number );

create table contact_list(
 contact  person_type,
  check ( contact.first_name is not null
        and contact.last_name is not null));


##Alter Table命令可以删除约束
alter table customers 
  drop constraint customer_credit_limit_max2；

alter table customers drop primary key;
alter table customers drop constraint customers_pk;

##Alter Table命令允许修改现有约束的名称
alter table customers rename constraint
     customer_gender to customer_gender_ck;

##可供选择的禁用约束的方式
alter table customers modify constraint name disable
alter table customers modify primary key disable;
alter table customers disable constraint name;
alter table customers disable primary key;

##启用某个约束的语法
alter table customers modify constraint name enable;


