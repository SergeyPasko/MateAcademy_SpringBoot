CREATE SCHEMA IF NOT EXISTS MA_STUDENT;

CREATE SEQUENCE IF NOT EXISTS ORDER_SEQ
	 MINVALUE 5555555555
	 MAXVALUE 9999999999
	 START WITH 5555555556
	INCREMENT BY 1;

DROP TABLE IF EXISTS MA_STUDENT.ORDERS;

 CREATE TABLE MA_STUDENT.ORDERS 
   (	ORDER_NUM DECIMAL(38,0), 
	ORDER_DATE TIMESTAMP(0), 
	CUST DECIMAL(38,0), 
	REP DECIMAL(38,0), 
	MFR VARCHAR(3), 
	PRODUCT VARCHAR(5), 
	QTY DECIMAL(38,0), 
	AMOUNT DOUBLE
   )
   
 