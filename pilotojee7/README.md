Projeto Piloto da Arquiteture de Referência
========================
* Autor: Cleber Moura

# O que é?

Este é um projeto de exemplo, implementado na Arquitetura de Referência, utilizando os frameworks que a compem.

# Módulos

* **pilotojee7**: Projeto Root
    * **pilotojee7-backend**: Projeto do backend que disponibiliza as APIs
    * **pilotojee7-frontend**: Projeto do frontend que disponibiliza a interface do usuário e consome as APIs do backend.
    
# Tecnologias

* **pilotojee7-backend**:
    * Java 8 ou superior
    * Maven 3
    * Java EE 7 (CDI, EJB, JPA, JTA, JAX-WS, JAX-RS, BV, JAAS, JMS)
    
* **pilotojee7-frontend**:
    * NodeJS 10.x
    * Angular 7.x
     
# Requisitos de Sistema

Java 8.0 (Java SDK 1.8) ou superior, Maven 3.1 ou superior.

# Segurança

Intergração com Keycloak para Autenticação e Autorização de usuários e suporte a Single Sign-On. Utiliza protocolo oAuth2.


# Geração automática de código-fonte

O projeto  possui integração com o framework Celerio (desenvolvido pela Jaxio) que permite gerar artefatos de código fonte com base no modelo de dados. A arquitetura possui 02 packs de templates (backend e frontend) que possibilita gerar os artefatos de acordo com as tabelas existentes no modelo.

[Documentação do Celerio](http://www.jaxio.com/documentation/celerio/)

## Passos para geração dos artefatos

> ### Configurar os dados de acesso ao banco de dados

> Os dados de acesso ao banco de desenvolvimento devem ser informados no arquivo **pom.xml** do projeto raiz, para que o celerio possa conectar com o banco e fazer a geração dos metadados. O **pom.xml** suporta 02 profiles de banco: **oracle** e **postgres**, que deverá ser informado em conjunto aos demais profiles utilizados nos comandos do celerio.

> ### Mapeamento das entidades no Celerio

> Para que o Celerio faça a geração dos artefatos, se faz necessário também realizar o mapeamento das entidades no arquivo XML disponível em: **./config/celerio-maven-plugin/celerio-maven-plugin.xml** 

> ### Comandos para geração dos artefatos

> Abaixo estão listados os comandos do celerio para geração dos artefatos:

> * Gerar dos metadados
    
> ```sh
$ mvn -P metadata,oracle
```
> * Apagar arquivos gerados
    
> ```sh
$ mvn -P cleanGen
```
> * Gerar os artefatos
    
> ```sh
$ mvn -P gen
```
> * Limpar arquivos gerados, gerar metadados e gerar artefatos
    
> ```sh
$ mvn -P cleanGen,metadata,gen,oracle
```


# Configurações do Keycloak

Para que a aplicação faça uso dos recursos de segurança do Keycloak, é necessário a utilização de um Keycloak Adapter.
Para o backend, estamos utilizando o Adapter instalado no servidor. E para o frontend, utilizamos um Adapter Javascript.


> ## Instalação do Keycloak Adapter

> O guia de instalação do pode ser consultado na documentação do Keycloak, disponível no link: [Keycloak Adapters](https://www.keycloak.org/docs/3.4/securing_apps/index.html#openid-connect-3)

> ## Configuração do client - Backend

> No arquivo **./pilotojee7-backend/src/main/webapp/WEB-INF/keycloak.json** devem ser definidas as informações do client. 

> É necessário definir o *auth-method* para **KEYCLOAK** no arquivo **web.xml**. É preciso definir os *roles* para garantir a segurança de acesso aos serviços REST. Abaixo, segue um exemplo desta configuração supondo a existência do REALM **pilotojee7** e de 02 (dois) *roles* como sendo "admin" e "user":

> ````xml
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
```

> ## Configuração do client - Frontend

> No arquivo **./pilotojee7-frontend/src/app/keycloak/app.init.ts** devem ser definidas as informações do client. 
