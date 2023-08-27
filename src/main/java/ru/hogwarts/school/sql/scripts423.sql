SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM student s
JOIN faculty f ON s.faculty_id = f.id
WHERE f.name = 'Хогвартс';

SELECT s.name AS student_name, a.filePath AS avatar_filePath
FROM student s
JOIN avatar a ON s.id = a.student_id;
