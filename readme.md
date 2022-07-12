# Read Me First
A presente API REST foi desenvolvida a partir das especificações presentes no arquivo:

* Especificação Backend.pdf

# Getting Started

##### Configurando o arquivo movieslist.csv
Requisitos para executar a API:

* deve existir na pasta raiz do projeto o arquivo:

> - movielist.csv

Atentar-se para o seguinte detalhe: 
* o arquivo deve possuir a estrutura de dados definida a seguir:

> - year;title;studios;producers;winner

##### Executando a aplicação

Para iniciar a aplicação, você deve rodar o comando abaixo na pasta raiz do projeto no terminal do seu sistema operacional:

`mvn spring-boot:run`

##### Executando os testes de integração

Para executar os testes da API, execute o comando abaixo:

`mvn clean test`
