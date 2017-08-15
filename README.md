# nsservices

<h2>Tecnologias Utilizadas </h2>

Devido aos requisitos não funcionais descritos, fui encorajado a tentar realizar este desafio utilizando um pouco da programação reativa e um banco não relacional. Para este próposito, o projeto foi realizado com Spring Boot 2 com Spring Framework 5.0, utilizando Banco de dados MongoDB e servidor Netty.

<h2>Como executar o projeto</h2>

1- Banco de dados
Você precisa ter um banco com o nome test no MongoDB.

2- Serviço Campaign

Para rodar o serviço execute os comandos:

gradlew clean build
java -jar build/libs/Campaign.jar --server.port=9000

3- Serviço PartnerFan

gradlew clean build
java -jar build/libs/PartnerFan.jar --server.port=9001

<h2>Mocks de Testes</h2>
Os mocks foram realizados utilizandos o Postman e o teste completo está disponível em https://documenter.getpostman.com/view/2415521/nstest/6n61tZR

