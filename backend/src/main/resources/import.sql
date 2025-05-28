INSERT INTO TB_ROLE (authority) VALUES ('PUBLICO');
INSERT INTO TB_ROLE (authority) VALUES ('POLICIAL');
INSERT INTO TB_ROLE (authority) VALUES ('AGENTE DE SEGURANCA');
INSERT INTO TB_ROLE (authority) VALUES ('INVESTIGADOR');
INSERT INTO TB_ROLE (authority) VALUES ('GESTOR DE SEGURANCA PUBLICA');

## TIPO DE OCORRÊNCIAS
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Perda ou extravio de documentos e objetos', 'Como carteira de identidade, CPF, CNH, cartão de crédito/débito, celular, bolsas, mochilas, etc.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Furto', 'Quando não há violência ou grave ameaça (ex: furto de celular do bolso, de bicicleta estacionada). É importante ressaltar que roubos (com violência ou grave ameaça) geralmente exigem registro presencial.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Estelionato', 'Principalmente aqueles realizados por meios digitais, como golpes de internet, fraude em compras online, clonagem de WhatsApp, etc.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Calúnia, injúria e difamação ', 'Crimes contra a honra, especialmente quando praticados em redes sociais ou outros meios digitais.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Ameaça', 'Dependendo da gravidade e do contexto, algumas ameaças que não envolvam risco iminente podem ser registradas online. No entanto, ameaças mais sérias ou contínuas geralmente demandam comparecimento à delegacia.');
INSERT INTO TB_TIPO_OCORRENCIA (NAME_TIPO_OCORRENCIA, DESCRICAO_TIPO_OCORRENCIA) VALUES ('Acidentes de trânsito sem vítimas', 'Colisões leves em que houve apenas danos materiais e não há feridos.');
