INSERT INTO permissao VALUES (4, 'ROLE_MODULO_ADMINISTRACAO_PERFIL_ACESSO');
INSERT INTO permissao VALUES (5, 'ROLE_MODULO_ADMINISTRACAO_USUARIO');
INSERT INTO permissao VALUES (6, 'ROLE_MODULO_CADASTRO_CLIENTE');
INSERT INTO permissao VALUES (7, 'ROLE_MODULO_PROTOCOLO_PROTOCOLO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 4);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 5);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 6);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 7);