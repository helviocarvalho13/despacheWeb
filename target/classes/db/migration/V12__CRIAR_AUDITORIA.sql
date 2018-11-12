CREATE TABLE protocolo_auditoria (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_hora DATETIME,
    status VARCHAR(30) NOT NULL,
    codigo_protocolo BIGINT(20) NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_protocolo) REFERENCES protocolo(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

