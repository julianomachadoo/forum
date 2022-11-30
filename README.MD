<h1>Fórum API</h1> 

<p align="center">
  <img src="https://img.shields.io/static/v1?label=Spring%20Boot&message=framework&color=blue&style=for-the-badge&logo=REACT"/>
  <img src="https://img.shields.io/static/v1?label=Java&message=language&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="https://img.shields.io/static/v1?label=H2&message=Database&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="https://img.shields.io/static/v1?label=Maven&message=build&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge"/>
  </p>

> Status do Projeto: :heavy_check_mark: (Em desenvolvimento)

### Tópicos

:small_blue_diamond: [Descrição do projeto](#descrição-do-projeto)

:small_blue_diamond: [Funcionalidades](#funcionalidades)

:small_blue_diamond: [Layout da Aplicação](#layout-da-aplicação-dash)

:small_blue_diamond: [Pré-requisitos](#pré-requisitos)

:small_blue_diamond: [Como rodar a aplicação](#como-rodar-a-aplicação-arrow_forward)

...

## Descrição do projeto

<p align="justify">
  Api com base no fórum da Alura para fazer postagens referentes aos cursos ministrados pela plataforma. 
</p>

...

## Funcionalidades

:ballot_box_with_check: Listagem, detalhamento, cadastro, atualização e exclusão de postagens;

:ballot_box_with_check: Adicionada camada de segurança utilizando Tokens JWT para autenticação;

:ballot_box_with_check: Autorização baseada em sistema de ROLES;

...

## Layout da Aplicação :dash:

[//]: # (endpoints.png)

## Pré-requisitos

:warning: [Java Runtime Environment](https://www.java.com/pt-BR/download/) <br>
:warning: [Maven](https://maven.apache.org/download.cgi) <br>
:warning: <b>Opcional:</b> [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)
ou [Eclipse](https://www.eclipse.org/downloads/)

...

## Como rodar a aplicação :arrow_forward:

No terminal, clone o projeto:

```
git clone https://github.com/julianomachadoo/certificate-generator
```

Com o projeto baixado, execute o seguinte comando para criar o jar:

```
mvn clean package
```

E execute o comando abaixo para executar o projeto: (:warning: atenção o projeto utiliza a porta 8080)

```
java -jar ./target/forum.jar --spring.profiles.active=prod
```

## Como utilizar:

<p align="justify">
Em ambiente de produção, estão liberados publicamente apenas o acesso aos endpoints de listagem e detalhamento dos tópicos:
</p>

[//]: # (getTopicos.gif e getTopicosById.gif)

<p align="justify">
Para o acesso aos outros endpoints, é necessario realizar a autenticação. Passando o email e a senha listados no tópico 
abaixo é possível obter o token JWT de autenticação: </p>

[//]: # (autenticandoAluno.gif)

<p align="justify">
O token é valido por 24h e deve ser passado no header Authorization juntamente com o nome Bearer que representa o formato da autenticação:
</p>

[//]: # (postExemplo.gif)

<p align="justify">
Para realizar a remoção de tópicos é necessario ter a ROLE de MODERADOR, caso contrário, o código recebido será o <font color=\"green\">403 Forbidden</font>:  
</p>

[//]: # (deleteNaoAutorizado)

## JSON :floppy_disk:

### Usuários:

| nome      | email               | password | role      ||
|-----------|---------------------|----------|-----------|-------- |
| Aluno     | aluno@email.com     | 123456   | ALUNO     |
| Moderador | moderador@email.com | 123456   | MODERADOR |

### Cursos:


| nome      | categoria            |
|-----------|---------------------|
| Spring Boot     | Programação     |
| HTML 5 | Front-end |

...


## Tarefas em aberto


:memo: Endpoints de respostas e atualização de status do tópico

:memo: Fazer migração para banco mysql ou postgreSQL

:memo: Melhorar a cobertura de testes


## Desenvolvedores/Contribuintes :octocat:



| [<img src="https://avatars.githubusercontent.com/u/102674195?v=4" width=115><br><sub>Juliano Machado</sub>](https://github.com/julianomachadoo) |
|:-----------------------------------------------------------------------------------------------------------------------------------------------:|

