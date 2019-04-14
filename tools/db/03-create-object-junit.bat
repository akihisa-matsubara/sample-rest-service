db2 connect to MYDB

db2 CREATE SCHEMA TEST

db2 set schema TEST

db2 -tvf ddl\create-object.sql
