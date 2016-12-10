DELETE FROM employees;
DELETE FROM department;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO department (fullName)
VALUES ('Marketing'),
  ('Production')
;
INSERT INTO employees (fullName, birthday,salary,deaprtment_id)
    VALUES ('Ivanov Ivan','1980-05-17',400,100000),
 ('Petrov Petr','1992-12-31',250,100000),
 ('Sidorov Anton','1992-11-05',210,100000),
      ('Jon W','1993-01-01',210,100001),
      ('Bob A','1993-06-12',210,100001)
;