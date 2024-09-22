DO
$$
BEGIN
   -- Create the database if it doesn't exist
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'oglasojad-db') THEN
      EXECUTE 'CREATE DATABASE "oglasojad-db";';
END IF;
END
$$;

-- Connect to the created database
\c oglasojad-db;

-- Create the categories table if it doesn't exist
CREATE TABLE IF NOT EXISTS category (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE,  -- Add UNIQUE constraint here
    description TEXT
    );

-- Insert default categories, ignoring duplicates
INSERT INTO category (name, description)
VALUES
    ('Живеалиште', 'Category for living accommodations'),
    ('Работа', 'Category for job postings'),
    ('Автомобили', 'Category for vehicles'),
    ('Електроника', 'Category for electronics')
    ON CONFLICT (name) DO NOTHING;  -- Avoid duplicate entries

