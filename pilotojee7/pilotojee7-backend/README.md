Piloto Backend
========================
* Autor: Unidade de Arquitetura de Software
* Ambiente de Execução: JBoss EAP 7.x ou Wildfly 10.x 

# Requisitos de Sistema

Java 8.0 (Java SDK 1.8) ou superior, Maven 3.1 ou superior.

# Execução

Para rodar a aplicação execute os segintes passos:

- Iniciar o JBoss usando o **Eclipse** ou executando linha de comando abaixo:

```
> $JBOSS_HOME\bin\standalone.sh --server-config=standalone-full.xml
```

- Execute o comando abaixo para empacotar o projeto:

```
> mvn clean package
```

> Observação: O profile padrão é o de **desenvolvimento**, os demais são: **teste**, **homologacao** e **producao**.

```
> mvn clean package -Phomologacao
```

- Execute o comando abaixo para fazer o deploy

```
> mvn wildfly:deploy
```

- Execute o comando abaixo para fazer o undeploy

```
> mvn wildfly:undeploy
```