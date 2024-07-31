DO
$$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'oglasojad-db') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "oglasojad-db";');
END IF;
END
$$;