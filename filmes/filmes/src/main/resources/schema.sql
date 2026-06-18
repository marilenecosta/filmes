CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS genero (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS filme (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    titulo VARCHAR(200) NOT NULL,
    ano INTEGER,
    diretor VARCHAR(150),
    genero_id INTEGER,
    FOREIGN KEY (genero_id) REFERENCES genero(id)
);

CREATE TABLE IF NOT EXISTS usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS favorito (
    usuario_id UUID REFERENCES usuario(id) ON DELETE CASCADE,
    filme_id UUID REFERENCES filme(id) ON DELETE CASCADE,
    data_favoritado DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (usuario_id, filme_id)
);

INSERT INTO usuario (nome, email, password) 
VALUES ('Mari', 'mari@email.com', '123456')
ON CONFLICT (email) DO NOTHING;

-- ADICIONE ESTES ÍNDICES PARA MELHORAR A PERFORMANCE E ORGANIZAÇÃO:
CREATE INDEX IF NOT EXISTS idx_filme_titulo ON filme(titulo);
CREATE INDEX IF NOT EXISTS idx_genero_nome ON genero(nome);