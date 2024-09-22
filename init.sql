DO
$$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'oglasojad-db') THEN
      EXECUTE 'CREATE DATABASE "oglasojad-db";';
END IF;
END
$$;

\c oglasojad-db;

CREATE TABLE IF NOT EXISTS category (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
    );

INSERT INTO category (name, description)
VALUES
    ('Живеалиште', 'Category for living accommodations'),
    ('Работа', 'Category for job postings'),
    ('Автомобили', 'Category for vehicles'),
    ('Електроника', 'Category for electronics')
    ON CONFLICT (name) DO NOTHING;

