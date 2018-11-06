# Piloto

O Piloto (**piloto-root**) é composto por 02 (duas) aplicações, como pode ser vista listada abaixo, e por uma solução
SSO (*Single-Sign-On*) para autenticação chamada [Keycloak]:

**Piloto-Backend:** aplicação de acesso aos dados através de uma API Rest.

**Piloto-Frontend:** aplicação de interação com o usuário desenvolvida em AngularJS (versão 1.x.x).
Esta aplicação se comunicará com o backend através de uma API Rest.

Este piloto utilizará ainda os frameworks desenvolvidos pela Unidade de Arquitetura de Software do [TJPE] de
versão 2.0.0.

### Versão
2.0.0

### Tecnologias

Piloto-Backend utiliza:

* Java 8
* Maven
* Widlfly 9.0.2.final

Piloto-Frontend utiliza:

* [AngularJS] - Framework MVC em Javascript
* [Twitter Bootstrap] - Biblioteca
* [node.js] - evented I/O for the backend
* [jQuery] - duh
* [Npm] - Gerenciador de Dependências
* [Bower] - Gerenciador de Dependências
* [Grunt] - Gerenciador de Tarefas

### Instalação das Bibliotecas

```sh
$ cd piloto-root/piloto-frontend/src/main/scripts/frontend
```

```sh
$ npm install
```

```sh
$ bower install
```

```sh
$ grunt install
```

Nota: Importante lembrar que quando alguma dependência for modificada/adicionada nos arquivos package.json, bower.json
ou gruntfile.js, faz-se necessário executar os comandos acima novamente.

### Keycloak

O [Keycloak] servirá como uma entidade de autenticação e autorização entre a comunicação do frontend e backend. A versão
utilizada é a **1.9.1.Final**.

Esta é uma solução para prover o SSO (Single-Sign-On) para diversos tipos de aplicações web, móveis e serviços REST. Nele, é possível centralizar o gerenciamento dos logins, logouts, cadastros de permissões e gerenciamentos das contas dos usuários e sessões.

A página de administração do Keycloak pode ser acessada em: http://localhost:8080/auth/realms/master

### Instalação do Keycloak

Há 03 (três) formas de instalação do Keycloak, porém para este piloto é importante destacar duas delas:

1. keycloak-1.9.1.Final.[zip|tar.gz] - Servidor Standalone
2. keycloak-overlay-1.9.1.Final.[zip|tar.gz] - Instalação para o WildFly ou JBoss EAP (**Este adaptador que estamos utilizando**)

A instalação no servidor standalone é recomendado para produção e desenvolvedores que não utilizam o JavaEE. Após a descompactação do arquivo baixado no servidor, é preciso rodar o comando abaixo:

Linux/MacOS:
```sh
$ keycloak-1.9.1.Final/bin/standalone.sh
```
ou Windows:
```sh
$ keycloak-1.9.1.Final/bin/standalone.bat
```

Já para instalar no Wildfly ou JBoss EAP, é necessário fazer o download do **keycloak-overlay-1.9.1.Final.zip** e extrair no diretório raiz do servidor. Em seguida, para adicionar o Keycloak no arquivo de configuração do servidor standalone.xml é necessário:

Linux/MacOS:
```sh
$ bin/jboss-cli.sh --file=bin/keycloak-install.cli
```
ou Windows:
```sh
$ bin/jboss-cli.BAT --file=bin/keycloak-install.cli
```

**Obs:** Caso queira configurar para outro nome diferente de arquivo de configuração do servidor (standalone-ha.xml ou outro), basta modificar o arquivo keycloak-install.cli.  Para maiores informações, é importante a leitura do **capítulo 3** da documentação do Keycloak (http://keycloak.jboss.org/docs)

### Adaptador dos Clientes Keycloak

Keycloak pode ser usado para diferentes tipos de aplicações. Neste piloto, foram criadas 02 (dois) clientes: o primeiro uma aplicação backend desenvolvida em JavaEE que serve como uma Api REST para outras aplicações; e por fim, uma aplicação frontend implementada em [AngularJS].

Portanto, é necessário configurar os adaptadores desses clientes do piloto. Para maiores informações desses adaptadores, é importante a leitura do **capítulo 8** da documentação do Keycloak (http://keycloak.jboss.org/docs).

Links de download: http://keycloak.jboss.org/downloads.html?dir=0%3Dadapters/keycloak-oidc%3B

##### Adaptador do Piloto-Backend

Arquivo para download: **keycloak-wildfly-adapter-dist-1.9.0.Final.zip**;

No backend, após o download do arquivo, é necessário executar:
```
$ cd $WILDFLY_HOME
$ unzip keycloak-wildfly-adapter-dist.zip
```
A descompactação deste arquivo cria um novo módulo no JBoss do Keycloak. Em seguida, é preciso habilizar este módulo no arquivo de configuração do servidor (standalone.xml). Para isso, executa-se o comando abaixo com o servidor rodando:
```
$ cd $JBOSS_HOME/bin
$ jboss-cli.sh -c --file=adapter-install.cli
```
Caso queira executar com o JBoss parado, executa-se o comando:
```
$ cd $JBOSS_HOME/bin
$ jboss-cli.sh -c --file=adapter-install-offline.cli
```

Em seguida, é necessário criar um arquivo ***keycloak.json*** dentro do diretório WEB-INF do projeto. O conteúdo deste arquivo será definido na aplicação administrativa do keycloak (http://localhost:8080/auth/), onde é criado os clientes, usuários, roles, etc.

E finalmente, é necessário definir o *auth-method* para **KEYCLOAK** no arquivo **web.xml**. É preciso definir os *roles* para garantir a segurança de acesso dos serviços REST. Abaixo, segue um exemplo desta configuração supondo a existência do REALM **piloto** e de 02 (dois) *roles* como sendo "admin" e "user":

````xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
      version="3.0">
	<display-name>piloto-backend</display-name>
	<security-constraint>
		<web-resource-collection>
			<web-resource-name></web-resource-name>
			<url-pattern>/api/*</url-pattern>
		</web-resource-collection>
		<auth-constraint>
			<role-name>admin</role-name>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>
	<login-config>
		<auth-method>KEYCLOAK</auth-method>
		<realm-name>piloto</realm-name>
	</login-config>
	<security-role>
		<role-name>admin</role-name>
		<role-name>user</role-name>
	</security-role>
</web-app>
````

**Obs1:** Mais informações podem ser encontradas na **seção 8.2** da documentação do keycloak.

**Obs2:** Importante destacar que esses 02 (dois) roles "admin" e "user" devem ser atribuídos ao usuário de autenticação da aplicação. Para atribuir, entre no console de administração do Keycloak e atribua os roles ao usuário em questão. No exemplo do piloto, esses roles podem ser usados para restringir as rotas, como pode ser visto no arquivo **webapp\scripts\router.js**, bem como na visualização de componentes HTML, como visto no arquivo **webapp\templates\sidebar\menu.html**. 

##### Adaptador do Piloto-Frontend

Após o download do arquivo **keycloak-js-adapter-dist-1.9.1.Final.zip**, é preciso adicionar as bibliotecas keycloak.js e keycloak.min.js no piloto-frontend. No arquivo principal do projeto, é preciso incluir a referência como pode ser visto no index.html:


	<!-- Keycloak Adapter -->
	<script src="lib/keycloak/keycloak.js"></script>


Da mesma forma que o backend, é necessário criar um arquivo ***keycloak.json*** dentro de algum diretório do projeto (pode ser qualquer um, pois este local será especificado mais adiante). O conteúdo deste arquivo também será definido na aplicação administrativa do keycloak (http://localhost:8080/auth/).

Em seguida, para configurar a autenticação pelo keycloak, segue alguns exemplos de uso no **app.js**:

```
 	// Carrega as informações do usuário autenticado pelo Keycloak.
 	//Cria a factory chamada AuthenticationService que contém os dados da autenticação.

	angular.element(document).ready(function (Constants) {
    var auth = {};
    // Arquivo keycloak.json criado na raiz do projeto, ex: D:\DevTJPE\workspaceReferencia\piloto-root\piloto-frontend\src\main\webapp\keycloak.json
    var keycloakAuth = new Keycloak('keycloak.json');
    auth.loggedIn = false;
    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        keycloakAuth.loadUserInfo().success(function (userInfo) {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.userInfo = userInfo;
            auth.userInfo.permissions = auth.authz.realmAccess.roles;
            var userToken = 'Bearer ' + auth.authz.token;
            localStorage.setItem(Constants.headerTokenName, userToken);
            app.factory('AuthenticationService', function() {
                return auth;
            });
            angular.bootstrap(document, ["piloto-frontend"]);
        });
    }).error(function () {
        window.location.reload();
    }); 
    });

```

**Obs:** Mais informações podem ser encontradas na **seção 8.8** da documentação do keycloak.

### Configuração dos Clientes

Primeiramente, é importante criar um REALM novo para que o REALM **master** padrão não seja modificado.

Em seguida, é importante criar os clientes piloto-frontend e piloto-backend. É importante salientar que após a criação dos mesmos, tanto o conteúdo dos arquivos **keycloak.json** poderão ser recuperados quanto os arquivos em si poderão ser baixados pela página de administração.

O **piloto-backend** será uma aplicação que disponibilizará Endpoints via serviços REST. Logo, segue abaixo as configurações necessárias:

- *Access Type*: bearer-only
- *Client Protocol*: openid-Connect

Na aba *Installation*, é possível recuperar o arquivo keycloak.json, como por exemplo:

```
{
  "realm": "piloto",
  "realm-public-key": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAicCUheAK8aqP3XG/2lDCsLhRlxsqGzSNYiGahP3Hq8+4BDr36RcXbcGGvqh7fLhxVoSxLqZxFiXvkqGqWDahViGAJi/CADOiWjyxRtOxzRci1uG5r2wI2bBb1o6LLgZsB9HVLg9Loda6hKzyjzTidRSpmhXPXNl1CrmBVSIYXzZ+qrAVh7LykfcJvBVTzswiG42tUYru86jeslJyzvpvr0vC/cOKBSe3qzupAnCi4pvRJdjULjBMQp+AV3pC5gF/5IWCCsE55m0k4jFRcfM/aXHrfPlVFWCUSwQu0nM1NmanREN4/dl6erxtnvdpwVHy36lLZ8Zz8g+tTgYx7wPU7wIDAQAB",
  "bearer-only": true,
  "auth-server-url": "http://localhost:8080/auth",
  "ssl-required": "external",
  "resource": "piloto-backend"
}
```
O **piloto-frontend** será uma aplicação web que acessará os serviços REST do **piloto-backend**. Logo, segue abaixo as configurações necessárias:

- *Access Type*: confidential
- *Client Protocol*: openid-Connect
- *Root URL*: http://localhost:8080
- *Valid Redirect URIs*: /piloto-frontend/*
- *Base URL*: /piloto-frontend

Na aba *Installation*, é possível recuperar o arquivo keycloak.json, como por exemplo:

```
{
  "realm": "piloto",
  "realm-public-key": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAicCUheAK8aqP3XG/2lDCsLhRlxsqGzSNYiGahP3Hq8+4BDr36RcXbcGGvqh7fLhxVoSxLqZxFiXvkqGqWDahViGAJi/CADOiWjyxRtOxzRci1uG5r2wI2bBb1o6LLgZsB9HVLg9Loda6hKzyjzTidRSpmhXPXNl1CrmBVSIYXzZ+qrAVh7LykfcJvBVTzswiG42tUYru86jeslJyzvpvr0vC/cOKBSe3qzupAnCi4pvRJdjULjBMQp+AV3pC5gF/5IWCCsE55m0k4jFRcfM/aXHrfPlVFWCUSwQu0nM1NmanREN4/dl6erxtnvdpwVHy36lLZ8Zz8g+tTgYx7wPU7wIDAQAB",
  "auth-server-url": "http://localhost:8080/auth",
  "ssl-required": "external",
  "resource": "piloto-frontend",
  "credentials": {
    "secret": "a1710409-24ee-4058-8fc0-f019db831303"
  }
}
```

Licença
----

TJPE (privado)


Licença
----

TJPE (privado)


**[TJPE] - Tribunal de Justiça de Pernambuco @2016**

[//]: # (Arquivos de referência como links - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [TJPE]: <http://www.tjpe.jus.br>
   [Keycloak]: <http://keycloak.jboss.org>
   [Bower]: <http://bower.io>
   [Grunt]: <http://gruntjs.com>
   [Npm]: <https://www.npmjs.com>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [AngularJS]: <http://angularjs.org>