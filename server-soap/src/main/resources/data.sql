DELETE FROM employees;
DELETE FROM departments;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO departments (name)
VALUES ('Marketing'),
  ('Production');

INSERT INTO employees (FULLNAME, BIRTHDAY, SALARY, DEPARTMENT_ID)
VALUES
  ('Ivanov Ivan', '1980-05-17', 400, 100000),
  ('Petrov Petr', '1992-12-31', 250, 100000),
  ('Sidorov Anton', '1992-11-05', 210, 100000),
  ('Jon W', '1993-01-01', 250, 100001),
  ('Bob A', '1993-06-12', 300, 100001);