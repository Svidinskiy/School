ALTER TABLE student
ADD CONSTRAINT chk_student_age CHECK (age >= 16);

ALTER TABLE student
ADD CONSTRAINT un_student_name UNIQUE (name);

ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
ADD CONSTRAINT un_faculty_name_color UNIQUE (name, color);