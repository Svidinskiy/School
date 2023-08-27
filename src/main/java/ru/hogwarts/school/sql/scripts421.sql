ALTER TABLE student
ADD CONSTRAINT chk_student_age CHECK (age >= 16);

ALTER TABLE student
ADD CONSTRAINT un_student_name UNIQUE (name);

ALTER TABLE student
ADD CONSTRAINT def_student_age DEFAULT 20 FOR age;

ALTER TABLE faculty
ADD CONSTRAINT un_faculty_name_color UNIQUE (name, color);
