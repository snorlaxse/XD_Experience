

#B树索引
CREATE INDEX EMP_B_IDX on employees(salary ASC);
#删除索引
DROP INDEX EMP_B_IDX ;

set autotrace on;

select employee_id from employees where salary>2000;