INSERT INTO category (name) VALUES
('Живеалиште'),
('Работа'),
('Автомобили'),
('Електроника')
ON CONFLICT (name) DO NOTHING;

