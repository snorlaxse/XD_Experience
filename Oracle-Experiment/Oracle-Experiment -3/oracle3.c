/*
PL/SQL
设计过程和函数各1个
实验内容：
在HR用户的例表下，自行设计业务逻辑
分别实现设计过程与函数各一个，要求提交并存储到数据中。
*/

/*设计储存过程，其功能为，查询员工的历史工作信息，如果历史工作超过一个，将该员工的工资翻倍*/
create or replace procedure set_db_salary(eid in number)
	is 
	history_job_count integer;
	BEGIN
	select count(*) into history_job_count from job_history where employee_id=eid;
	if history_job_count>1 then 
	update employees set salary=salary*2 where employee_id=eid;
	end if;
	end set_db_salary;
	/

/*
执行该该过程
执行背景：编号101的历史工作有两个，编号102的历史工作有一个
执行后的结果，101工资翻倍，102工资不变
*/
exec set_db_salary(101);
exec set_db_salary(102);





/*就设计函数对Oracle的md5加密进行封装*/
create or replace function mymd5(str in varchar2)
return varchar2
is 
md5_str varchar2(32);
BEGIN
md5_str:=ut1_raw.cast_to_raw(DBMS_OBFUSCATION_TOOLKIT.MD5(INPUT_STRING=>str));
return md5_str;
end;
/

/*创建新表admin，字段为ID和密码*/
create table admin(
aid number(4) primary key,
password varchar2(32) not null
);

/*插入测试*/
insert into admin
	values(38,mymd5('Captain'));

/*插入结果*/
select * from admin where password= mymd5('Captain');

/*查询测试*/
select mymd5('Captain') from dual;




/*课件示例*/
/*使用单个游标 – 实例*/
CREATE OR REPLACE PROCEDURE promotion_review_1 
IS
   nemployeeid   NUMBER;
   dstartdate    DATE;
   denddate      DATE;
   sjobid        VARCHAR2 (20);
   CURSOR cselectjob
   IS
      SELECT employee_id, start_date, end_date, job_id  FROM hr.job_history;
BEGIN
   OPEN cselectjob;
   LOOP
      FETCH cselectjob INTO nemployeeid, dstartdate, denddate, sjobid;
      EXIT WHEN cselectjob%NOTFOUND;
      DBMS_OUTPUT.put_line ('Employee ' || nemployeeid || ' had job ' || sjobid || ' for ' || (denddate - dstartdate) || ' days.');
   END LOOP;
   CLOSE cselectjob;
END;


/*FOR游标 – 实例*/
CREATE OR REPLACE PROCEDURE promotion_review_2
IS
   CURSOR cselectjob
   IS
      SELECT employee_id, start_date, end_date, job_id
        FROM hr.job_history;
BEGIN
   FOR jh_rec IN cselectjob

   LOOP
      DBMS_OUTPUT.put_line ('Employee ' || jh_rec.employee_id  || ' had job ' || jh_rec.job_id || ' for ' || (jh_rec.end_date - jh_rec.start_date) || ' days.');
   END LOOP;
END;


/*
总结函数和储存过程的异同：
相同点：
1. 创建语法结构相似，都可以携带多个传入参数和传出参数。
2. 都是一次编译，多次执行。

不同点：
1. 存储过程定义关键字用procedure，函数定义用function。
2. 存储过程中不能用return返回值，但函数中可以，而且函数中必须有return子句。
3. 执行方式略有不同，存储过程的执行方式有两种（execute或begin和end），
函数除了存储过程的两种方式外，还可以当做表达式使用，例如放在select中（select mymd5(“”) form dual;）
*/
