CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS genero (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS filme (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(), 
    titulo VARCHAR(200) NOT NULL,
    ano INTEGER,                    
    diretor VARCHAR(150),           
    genero_id INTEGER,               
    FOREIGN KEY (genero_id) REFERENCES genero(id)
);