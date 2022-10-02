CREATE TABLE schedule (id uuid default random_uuid() primary key, sport varchar(50), appointment_date datetime, place varchar(100), description varchar(150));

INSERT INTO schedule (sport, appointment_date, place, description) VALUES ('NATACAO', '2022-10-04T09:26:51', 'Hidro Fitness', 'Natação da Turma A');