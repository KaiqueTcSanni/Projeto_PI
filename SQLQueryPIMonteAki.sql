CREATE DATABASE MonteAki

USE MonteAki


CREATE TABLE tbl_funcionarios (
	 id_funcionarios INT IDENTITY PRIMARY KEY ,
	 nomeCompleto VARCHAR (255),
	 cargo VARCHAR (255),
	 dataIngresso DATE,
	 status_usuario BIT,
	 email VARCHAR (255),
	 senha VARCHAR(255),
)

CREATE TABLE tbl_cliente(
	id_cliente INT IDENTITY PRIMARY KEY,
	email_cliente VARCHAR (255),
	senha VARCHAR(100)
)


CREATE TABLE tbl_cartaoCredito(
	id_cartaoCredito INT IDENTITY PRIMARY KEY,
	id_cliente INT,
	vencimento DATE,
	numero INT,
	nomeNoCartao VARCHAR (255),
	cvv CHAR (3),
	status_cartao BIT,
	FOREIGN KEY (id_cliente) REFERENCES tbl_cliente(id_cliente),
)


CREATE TABLE tbl_endereco(
	id_endereco INT IDENTITY PRIMARY KEY,
	id_cliente INT,
	CEP INT, --será necessário o usuario informar apenas números
	bairro VARCHAR (255),
	cidade VARCHAR (255),
	estado VARCHAR (255),
	FOREIGN KEY (id_cliente) REFERENCES tbl_cliente(id_cliente),
)


CREATE TABLE tbl_produto(
	id_produto INT IDENTITY PRIMARY KEY,
	nome_produto VARCHAR (255),
	tipo_produto VARCHAR (255),
	fornecedor VARCHAR (255),
	descrição_produto VARCHAR (255),
	valor DECIMAL (7,2),
	foto_produto VARCHAR(MAX),
	id_fornecedor INT,
	data_cadastro DATE,
	FOREIGN KEY (id_fornecedor) REFERENCES tbl_fornecedor (id_fornecedor)
)

/*

ID:			6
NOME:		Barra de Chocolate
VALOR:		R$16,90
DESCRICAO:	Barra de Chocolate com Pistache
FORNECEDOR: Cacau Show

ID:			7
NOME:		Barra de Chocolate
VALOR:		R$11,90
DESCRICAO:	Barra de Chocolate
FORNECEDOR: Lacta

*/

CREATE TABLE tbl_estoque(
	id_estoque INT IDENTITY PRIMARY KEY,
	id_produto INT,
	id_fornecedor INT,
	quantidade_produto INT,
	FOREIGN KEY (id_fornecedor) REFERENCES tbl_fornecedor (id_fornecedor),
	FOREIGN KEY (id_produto) REFERENCES tbl_produto (id_produto)
)

/*

ID:			1
ID PROD:	6
ID FORN:	1
QNT:		100

*/

CREATE TABLE tbl_fornecedor (
	id_fornecedor INT IDENTITY PRIMARY KEY,
	nome_fornecedor VARCHAR (100),
	CNPJ INT,
	matrizOuFilial CHAR (1),
	status_fn BIT
)

/*

ID :			1 
NOME:			cacau show
CNPJ:			45125454210001
MatrizOuFilial: M

ID :			2
NOME:			LACTA
CNPJ:			45125584210001
MatrilOuFilial: M


*/
CREATE TABLE tbl_carrinho(
    id_carrinho INT IDENTITY PRIMARY KEY,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES tbl_cliente(id_cliente)
)
 
CREATE TABLE tbl_itemCarrinho(
    id_itemCarrinho INT IDENTITY PRIMARY KEY,
    id_carrinho INT,
    id_produto INT,
    quantidade INT,
    FOREIGN KEY (id_carrinho) REFERENCES tbl_carrinho(id_carrinho),
    FOREIGN KEY (id_produto) REFERENCES tbl_produto(id_produto)
)
 
CREATE TABLE tbl_DetalhesPedido(
    id_pedido INT IDENTITY PRIMARY KEY,
    id_carrinho INT,
    quantidade INT,
    subtotal DECIMAL(7,2),
    emissao_nf BIT,
	forma_pagamento VARCHAR (100),
    status_pagamento BIT,
    FOREIGN KEY (id_carrinho) REFERENCES tbl_carrinho(id_carrinho)
)


CREATE TABLE tbl_InfoEnvio(
	id_envio INT IDENTITY PRIMARY KEY,
	tipo_envio VARCHAR (100),
	custo_envio DECIMAL (7,2), 
	dt_envio DATE,
	dt_entrega DATE,
	emissao_nf BIT,
	id_pedido INT,
	FOREIGN KEY (id_pedido) REFERENCES tbl_DetalhesPedido(id_pedido),
)




--mysqldump, o sqlcmd e o db2Backup