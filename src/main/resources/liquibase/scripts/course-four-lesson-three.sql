-- liquibase formatted sql
-- changeset svidinskiy:1

CREATE INDEX ind_student_name ON student (name);

CREATE INDEX ind_faculty_name_color ON faculty (name, color);
