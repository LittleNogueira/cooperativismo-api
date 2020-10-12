# Cooperativismo API Rest

## Sobre o projeto

O Cooperativismo API Rest é um pequeno desafio que eu recebi, foi desenvolvido em Spring Boot 2 usando Maven, tem como principal objetivo gerenciar sessões, votos, resultados, pautas e associados, no momento a API permite cadastrar N associados com o mesmo CPF por motivos de facilidade nos testes, tentei utilizar uma maneira simples para resolver o problema, foi implementando teste para validar as regras de negócios no contexto apresentado, e também pensei em uma maneira de conseguir atender muitas chamadas no serviço de voto, basicamente faz uma pequena validação nos dados informados e logo em seguida é mandado para uma fila e assim rodar em segundo plano e possui bastante logs para facilitar o rastreamento. 

## Intruções para configuração

Requisitos

- <a href="https://docs.docker.com/docker-for-windows/install/" target="_blank" >Docker</a>
- <a href="https://docs.docker.com/compose/install/" target="_blank" >Docker compose</a>
- <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html"  target="_blank">JDK</a>
- <a href="https://maven.apache.org/" target="_blank">Maven</a>

Para subir todo o sistema usando o docker basta seguir os passos abaixo, ao executar o comando o docker-compose ira criar três containers um para p zookeeper, outro para Kafka e o ultimo container para o nosso banco de dados postgres.  

```
 cd /caminho/do/projeto/cooperativismo-api/
 docker-compose up -d
```

Baixe todas as dependencias necessarias com o maven e execute o projeto

```
 cd /caminho/do/projeto/cooperativismo-api/
 mvn install -DskipTests
 mvn clean spring-boot:run
```
Pronto, o aplicativo está pronto para uso em http://localhost:8080/api 🎉 🎊 🎈

Para entrar na documentação gerada automaticamente do swagger basta acessar http://localhost:8080/api/swagger-ui.html

No projeto se encontra uma collections para importar no Insomnia, contém todos os endpoints e possiveis dados para mandar na requisição.   

## Sobre as tecnologias

#### Spring Boot 2

O Spring Boot é um projeto da Spring que utiliza a linguagem Java e veio para facilitar o processo de configuração e publicação de nossas aplicações. A intenção é ter o seu projeto rodando o mais rápido possível e sem complicação.

#### Maven

O Apache Maven é uma ferramenta de gerenciamento e compreensão de projetos de software. Com base no conceito de um modelo de objeto de projeto (POM), o Maven pode gerenciar a criação, o relatório e a documentação de um projeto a partir de uma informação central.

#### PostgreSQL

PostgreSQL é um sistema gerenciador de banco de dados objeto relacional (SGBD), desenvolvido como projeto de código aberto.

#### Docker

O docker é uma alternativa de virtualização em que o kernel da máquina hospedeira é compartilhado com a máquina virtualizada ou o software em operação, portanto um desenvolvedor pode agregar a seu software a possibilidade de levar as bibliotecas e outras dependências do seu programa junto ao software com menos perda de desempenho do que a virtualização do hardware de um servidor completo.

#### Docker compose

È uma ferramenta para definir e executar aplicativos Docker com vários contêineres. Ele usa arquivos YAML para configurar os serviços do aplicativo e executa o processo de criação e inicialização de todos os contêineres com um único comando. O utilitário CLI do docker-compose permite que os usuários executem comandos em vários contêineres ao mesmo tempo, por exemplo, criando imagens, dimensionando contêineres, executando contêineres que foram interrompidos e muito mais.

#### Kafka

Apache Kafka é uma plataforma de streaming de eventos distribuídos de código aberto usada por milhares de empresas para pipelines de dados de alto desempenho, análise de streaming, integração de dados e aplicativos de missão crítica.

#### Swagger

Swagger é em essência uma linguagem de descrição de interface para descrever APIs RESTful expressas usando JSON . Swagger é usado junto com um conjunto de ferramentas de software de código aberto para projetar, construir, documentar e usar serviços da Web RESTful . Swagger inclui documentação automatizada, geração de código (em muitas linguagens de programação) e geração de casos de teste.
