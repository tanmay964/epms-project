INSERT INTO departments (id, name, budget) VALUES (1, 'Engineering', 500000);
INSERT INTO departments (id, name, budget) VALUES (2, 'Marketing', 200000);

INSERT INTO employees (id, name, email, department_id, date_of_joining, salary) VALUES
(1, 'Alice Johnson', 'alice@example.com', 1, '2021-01-15', 80000),
(2, 'Bob Smith', 'bob@example.com', 1, '2022-03-10', 75000),
(3, 'Charlie Brown', 'charlie@example.com', 2, '2020-07-01', 65000);

INSERT INTO projects (id, name, start_date, end_date, department_id) VALUES
(1, 'Project Apollo', '2023-01-01', '2023-12-31', 1),
(2, 'Project Zeus', '2023-06-01', '2024-06-01', 2);

INSERT INTO employee_projects (employee_id, project_id, assigned_date, role) VALUES
(1, 1, '2023-01-15', 'Lead Developer'),
(2, 1, '2023-02-01', 'Developer'),
(3, 2, '2023-06-15', 'Marketing Lead');

INSERT INTO performance_reviews (id, employee_id, review_date, score, review_comments) VALUES
(1, 1, '2024-12-01', 5, 'Excellent performance'),
(2, 1, '2024-06-01', 4, 'Good work'),
(3, 1, '2023-12-01', 3, 'Satisfactory'),
(4, 2, '2024-12-01', 4, 'Solid contributions'),
(5, 3, '2024-12-01', 2, 'Needs improvement');
