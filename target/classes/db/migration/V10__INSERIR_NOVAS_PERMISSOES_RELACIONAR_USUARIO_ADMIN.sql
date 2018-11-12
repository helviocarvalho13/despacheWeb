INSERT INTO permissao VALUES (2, 'ROLE_MODULO_ADMINISTRACAO');
INSERT INTO permissao VALUES (3, 'ROLE_MODULO_CADASTRO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 3);