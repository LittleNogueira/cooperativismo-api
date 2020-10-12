# Cooperativismo API Rest

## Sobre o projeto

O Cooperativismo API Rest é um projeto desenvolvido em Spring Boot 2 usando Maven, tem como principal objetivo servir dados para a aplicação

## Intruções para configuração

Requisitos

- <a href="https://docs.docker.com/docker-for-windows/install/" target="_blank" >Docker</a>
- <a href="https://docs.docker.com/compose/install/" target="_blank" >Docker compose</a>
- <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html"  target="_blank">JDK</a>
- <a href="https://maven.apache.org/" target="_blank">Maven</a>

Para subir todo o sistema usando o docker basta seguir os passos abaixo, ao executar o comando o docker-compose ira criar dois containers um para a aplicacao em Spring Boot e o outro container para o nosso banco de dados postgres.  

```
 cd /caminho/do/projeto/cooperativismo-api/
 docker-compose up -d
```

Baixe todas as dependencias necessarias com o maven

```
 cd /caminho/do/projeto/cooperativismo-api/
 mvn install -DskipTests
```

Importe e excute o projeto em uma IDE Java, Eclipse de preferencia.


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
