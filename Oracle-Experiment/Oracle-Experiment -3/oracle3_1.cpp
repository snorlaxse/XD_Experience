/*在hr用户employees表下，设计一个PL/SQL过程用来查询该表下的不同工作下的最高，最低，平均工资。
通过在命令行窗口处执行这个procedure,显示具体的查询过程，
通过在过程中添加错误机制处理，增加程序的容错性。*/

create or replace procedure query_sal(v_job in employees.job_id%type)
   as
     v_min_sal employees.salary%type;
     v_max_sal employees.salary%type;
     v_avg_sal employees.salary%type;
begin
   select min(salary) into v_min_sal from hr.employees where job_id = v_job;
   select max(salary) into v_max_sal from hr.employees where job_id = v_job;
   select avg(salary) into v_avg_sal from hr.employees where job_id = v_job;
   dbms_output.put_line('This job is minimum salary is ' || v_min_sal);
   dbms_output.put_line('This job is maximum salary is ' || v_max_sal);
   dbms_output.put_line('This job is average salary is ' || v_avg_sal);
exception
   when no_data_found then
     dbms_output.put_line('Not Record Found');
end;

set serveroutput on
exec query_sal('IT_PROG');
exec query_sal('FI_MGR');


/*在hr用户employees表下，
设计一个PL/SQL函数用来增加employees表中的具体第一个用户的工资增加计算（使工资增加到1.5倍）,
通过在命令行窗口处执行这个function,显示具体的查询过程，
通过在过程中添加错误机制处理，增加程序的容错性。*/
create or replace function raise_sal(name in employees.first_name%type) return number 
  as
     new_sal employees.salary%type;
begin
  select salary*1.5 into new_sal from employees where upper(first_name) = upper(name) and rownum =1;
  return new_sal;
exception
  when no_data_found then
    raise_application_error(-20000,'Current Employee does not exists');
end;

select salary，raise_sal('Lex') 
from employees 
where first_name='Lex'and rownum=1;

raise_sal('Steven') ;
