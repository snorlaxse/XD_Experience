/*调用函数*/
OCI_ExecuteStmt(st, MT("begin ")

						MT("dbms_output.put_line(raise_sal('Steven'));")
					
						MT("end;")
                   );