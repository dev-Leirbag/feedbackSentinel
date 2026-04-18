CREATE TABLE tb_feedback(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto_original VARCHAR(1000) NOT NULL,
    sentimento VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    data_criacao TIMESTAMP NOT NULL
);