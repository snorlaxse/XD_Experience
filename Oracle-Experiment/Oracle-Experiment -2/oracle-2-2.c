#触发器语法
create [OR REPLACE] TRIGGER trigger_name
{BEFORE | AFTER | INSTEAD OF} triggering_event
referencing_clause
[WHEN trigger_condition]
[FOR EACH ROW]
trigger_body;


#BEFORE型语句级触发器
CREATE OR REPLACE TRIGGER secure_holo
BEFORE INSERT ON holo
BEGIN
  IF ( TO_CHAR(SYSDATE,'DY') in ('SAT','SUN'))
         OR ( to_number(TO_CHAR(sysdate,'HH24')) 
         NOT BETWEEN 8 AND 18 )  THEN RAISE_APPLICATION_ERROR(-20500,'you may only insert into holo during normal hours.');
  END IF;
END;
/

insert into holo values(099,'momomo');

#DML触发器-行级触发器
CREATE OR REPLACE TRIGGER holo1_cascade_update
AFTER UPDATE OR DELETE ON holo
FOR EACH ROW
BEGIN
   UPDATE holo1 SET holo1.holo1_id = :new.holo_id
      WHERE holo1.holo1_id = :old.holo_id;
END; 
/

update holo set holo_id = 100
	WHERE holo_id = 123;

select * from holo;
select * from holo1;
