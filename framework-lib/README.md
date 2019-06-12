# Lib TJPE Framework

Projeto da biblioteca Angular para o framework.

## Download das dependências

- Execute o seguinte comando para baixar as dependências:

```
> npm install
```


## Test

- Execute o seguinte comando para testar a biblioteca no projeto de exemplo:

```
> ng serve framework-lib-app
```

## Build

- Execute o seguinte comando para empacotamento da biblioteca:

```
> npm run package
```

## Publicação

- Execute os comandos abaixo para publicação no Nexus:

```
> npm login --registry=http://www.tjpe.jus.br/nexus/repository/npm-tjpe/
```

```
> cd dist/framework-lib
```

```
> npm publish --registry=http://www.tjpe.jus.br/nexus/repository/npm-tjpe/
```