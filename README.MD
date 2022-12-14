<h1>Fórum API</h1> 

<p align="center">
  <img src="https://img.shields.io/static/v1?label=Spring%20Boot&message=framework&color=blue&style=for-the-badge&logo=REACT"/>
  <img src="https://img.shields.io/static/v1?label=Java&message=language&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="https://img.shields.io/static/v1?label=H2&message=Database&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="https://img.shields.io/static/v1?label=Maven&message=build&color=blue&style=for-the-badge&logo=netlify"/>
  <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge"/>
  </p>

> Status do Projeto: :heavy_check_mark: (Em desenvolvimento)

...

### Tópicos

:small_blue_diamond: [Descrição do projeto](#descrição-do-projeto)

:small_blue_diamond: [Funcionalidades](#funcionalidades)

:small_blue_diamond: [Layout da Aplicação](#layout-da-aplicação--dash-)

:small_blue_diamond: [Pré-requisitos](#pré-requisitos-para-rodar-a-aplicação-localmente)

:small_blue_diamond: [Como rodar a aplicação](#como-rodar-a-aplicação-localmente--arrowforward-)

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

:ballot_box_with_check: Deploy em um servidor de nuvem;

:ballot_box_with_check: Endpoint de respostas.


...

## Layout da aplicação :dash:

> Link do deploy da aplicação: http://forumapi-env.eba-guqgabap.us-east-1.elasticbeanstalk.com/

...

![](https://github.com/julianomachadoo/forum/blob/main/img/Projeto_Overview.gif?raw=true)

...

## Pré-requisitos para rodar a aplicação localmente

:warning: [Java Runtime Environment](https://www.java.com/pt-BR/download/) <br>
:warning: [Maven](https://maven.apache.org/download.cgi) <br> 
:warning: <b>Opcional:</b> [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)
ou [Eclipse](https://www.eclipse.org/downloads/)

...

## Como rodar a aplicação localmente :arrow_forward:

No terminal, clone o projeto:

```
git clone https://github.com/julianomachadoo/forum.git
```

Com o projeto baixado, execute o seguinte comando para criar o jar:

```
mvn clean package
```

E execute o comando abaixo para executar o projeto: (:warning: atenção o projeto utiliza a porta 5000)

```
java -jar ./target/forum.jar 
```

...

## Como utilizar:

<p align="justify">
Estão liberados publicamente apenas o acesso aos endpoints de listagem e detalhamento dos tópicos:
</p>

![](https://github.com/julianomachadoo/forum/blob/main/img/getTopicos.gif?raw=true)
![](https://github.com/julianomachadoo/forum/blob/main/img/getTopicosById.gif?raw=true)

<p align="justify">
Para o acesso aos outros endpoints, é necessario realizar a autenticação. Passando o email e a senha listados no tópico 
abaixo é possível obter o token JWT de autenticação: </p>

![](https://github.com/julianomachadoo/forum/blob/main/img/autenticandoAluno.gif?raw=true)

<p align="justify">
O token é valido por 24h e deve ser passado no header Authorization juntamente com o nome Bearer que representa o formato da autenticação:
</p>

![](https://github.com/julianomachadoo/forum/blob/main/img/postExemplo.gif?raw=true)

<p align="justify">
Para realizar a remoção de tópicos é necessario ter a ROLE de MODERADOR, caso contrário, o código recebido será o <font color=\"green\">403 Forbidden</font>:  
</p>

[//]: # (deleteNaoAutorizado)
![](https://github.com/julianomachadoo/forum/blob/main/img/deleteNaoAutorizado.gif?raw=true)

### Endpoint de Respostas

<p align="justify">
Estão liberados os endpoints de cadastro, atualização, remoção e listagem por tópico id, de respostas:
</p>

[//]: # (repostas_por_topico_id)
![](https://github.com/julianomachadoo/forum/blob/main/img/get_respostas_por_topicoId.gif?raw=true)



...

## JSON :floppy_disk:

### Usuários:

| nome      | email               | password | role      ||
|-----------|---------------------|----------|-----------|-------- |
| Aluno     | aluno@email.com     | 123456   | ALUNO     |
| Moderador | moderador@email.com | 123456   | MODERADOR |

### Cursos:


| nome        | categoria   |
|-------------|-------------|
| Spring Boot | Programação |
| HTML 5      | Front-end   |

...


## Tarefas em aberto

:memo: Criar endpoint para cadastro de aluno;

:memo: Criar endpoint para tornar resposta solução;

:memo: Criar endpoint para cadastrar curso (disponível apenas para MODERADOR);

:memo: Continuar melhorarando a cobertura de testes

:memo: Fazer migração para banco mysql ou postgreSQL


...

## Desenvolvedores/Contribuintes :octocat:



| [<img src="https://avatars.githubusercontent.com/u/102674195?v=4" width=115><br><sub>Juliano Machado</sub>](https://github.com/julianomachadoo) |
|:-----------------------------------------------------------------------------------------------------------------------------------------------:|

## Licença

The [MIT License](https://github.com/julianomachadoo/forum/blob/main/LICENSE) (MIT)

Copyright :copyright: 2022 - Forum API
