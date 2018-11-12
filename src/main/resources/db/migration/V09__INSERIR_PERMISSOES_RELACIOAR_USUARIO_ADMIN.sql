INSERT INTO permissao VALUES (1, 'ROLE_MODULO_PROTOCOLO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'admin@admin.com'), 1);