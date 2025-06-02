INSERT INTO TB_ROLE (authority) VALUES ('PUBLICO');
INSERT INTO TB_ROLE (authority) VALUES ('POLICIAL');
INSERT INTO TB_ROLE (authority) VALUES ('AGENTE DE SEGURANCA');
INSERT INTO TB_ROLE (authority) VALUES ('INVESTIGADOR');
INSERT INTO TB_ROLE (authority) VALUES ('GESTOR DE SEGURANCA PUBLICA');
INSERT INTO TB_ROLE (authority) VALUES ('ADMINISTRADOR');


INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157, § 1º','Subtração de coisa móvel, com grave ameaça ou violência.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157, § 2º','Roubo praticado com agravantes, como uso de arma, concurso de pessoas, entre outros.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157, § 1º, IV','Subtração de veículos com grave ameaça ou violência.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157','Roubo com a presença de um ladrão praticando o crime.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157','Roubo de veículo segurado.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 157','Roubo sem especificação de modalidade.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155','Subtração de coisa móvel alheia, sem violência ou grave ameaça.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155, § 4º','Furto realizado com agravantes, como o uso de chave falsa, rompimento de obstáculo, etc.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155','Furto de veículos.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155, § 2º','Furto de veículo em concurso de pessoas.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155, § 2º','Furto cometido por mais de uma pessoa em conjunto.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155','Furto sem especificação de modalidade.');
INSERT INTO TB_ARTIGOS_CRIMINAIS (COD_ARTIGO, DESCRICAO_ARTIGO) values ('Art. 155, § 4º','Furto qualificado devido a circunstâncias como uso de chave falsa, rompimento de obstáculo, etc.');


INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Perda ou extravio de documentos e objetos', 'Como carteira de identidade, CPF, CNH, cartão de crédito/débito, celular, bolsas, mochilas, etc.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Furto', 'Quando não há violência ou grave ameaça (ex: furto de celular do bolso, de bicicleta estacionada). É importante ressaltar que roubos (com violência ou grave ameaça) geralmente exigem registro presencial.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Estelionato', 'Principalmente aqueles realizados por meios digitais, como golpes de internet, fraude em compras online, clonagem de WhatsApp, etc.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Calúnia, injúria e difamação ', 'Crimes contra a honra, especialmente quando praticados em redes sociais ou outros meios digitais.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Ameaça', 'Dependendo da gravidade e do contexto, algumas ameaças que não envolvam risco iminente podem ser registradas online. No entanto, ameaças mais sérias ou contínuas geralmente demandam comparecimento à delegacia.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Acidentes de trânsito sem vítimas', 'Colisões leves em que houve apenas danos materiais e não há feridos.');

INSERT INTO tb_user (activated, cpf, email, password, user_name) VALUES (true, '11122233301', 'welder@gmail.com', '$2a$10$lMgYQtaN7x6fgTXPPgoP7.R6jE3twRMd8ClfJT4DkOYNCnNA3Y9N.', 'Welder Barbosa Paz')
INSERT INTO TB_USER_ROLE (user_id, role_id) VALUES (1, 6)
INSERT INTO TB_USER_AGENTE (USER_ID, BADGE, DELEGATE, RA) VALUES (1, 'BADGE', '73ª', 'B13')
INSERT INTO TB_USER_GESTOR (USER_ID, CARGO, DEPARTMENT) VALUES (1, 'Delegado', 'Administração')