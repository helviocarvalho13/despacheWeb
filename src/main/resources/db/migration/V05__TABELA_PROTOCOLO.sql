CREATE TABLE protocolo (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(200) NOT NULL,
    data DATE NOT NULL,
    observacao VARCHAR(500),
	codigo_cliente BIGINT(20) NOT NULL,
	assinatura LONGBLOB NOT NULL,
	status VARCHAR(30) NOT NULL,
	FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;