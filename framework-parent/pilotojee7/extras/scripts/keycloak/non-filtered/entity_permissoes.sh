#!/bin/bash
# Script para criar no keycloak, as permissoes
# @author Cleber Moura <cleber.t.moura@gmail.com> - 05/Abr/2017

AUTH_SERVER_URL=${keycloak.auth-server-url}
REALM=${keycloak.realm}
KEYCLOAK_USER=${keycloak.username}
KEYCLOAK_PASSWORD=${keycloak.password}

cd $JBOSS_HOME/bin

CLIENTS=$(./kcadm.sh get clients --fields id,clientId --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD)
CID=$(echo $CLIENTS | jq -c '.[] | select(.clientId | contains("${project.artifactId}-backend")) | .id' | sed -e 's/^"//' -e 's/"$//')

# Permissoes da entidade Fornecedor

./kcadm.sh create clients/$CID/roles -s name=Fornecedor.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fornecedor.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fornecedor.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fornecedor.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fornecedor.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Endereco

./kcadm.sh create clients/$CID/roles -s name=Endereco.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Endereco.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Endereco.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Endereco.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Endereco.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Funcao

./kcadm.sh create clients/$CID/roles -s name=Funcao.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Funcao.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Funcao.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Funcao.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Funcao.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Bairro

./kcadm.sh create clients/$CID/roles -s name=Bairro.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Bairro.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Bairro.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Bairro.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Bairro.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Fabricante

./kcadm.sh create clients/$CID/roles -s name=Fabricante.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fabricante.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fabricante.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fabricante.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Fabricante.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade ClienteUsuario

./kcadm.sh create clients/$CID/roles -s name=ClienteUsuario.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ClienteUsuario.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ClienteUsuario.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ClienteUsuario.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ClienteUsuario.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade PessoaContato

./kcadm.sh create clients/$CID/roles -s name=PessoaContato.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaContato.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaContato.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaContato.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaContato.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Cliente

./kcadm.sh create clients/$CID/roles -s name=Cliente.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cliente.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cliente.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cliente.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cliente.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade RequisicaoMaterialItem

./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterialItem.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterialItem.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterialItem.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterialItem.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterialItem.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Responsavel

./kcadm.sh create clients/$CID/roles -s name=Responsavel.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Responsavel.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Responsavel.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Responsavel.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Responsavel.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade PessoaFisica

./kcadm.sh create clients/$CID/roles -s name=PessoaFisica.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaFisica.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaFisica.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaFisica.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaFisica.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade TipoLogradouro

./kcadm.sh create clients/$CID/roles -s name=TipoLogradouro.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=TipoLogradouro.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=TipoLogradouro.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=TipoLogradouro.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=TipoLogradouro.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Servico

./kcadm.sh create clients/$CID/roles -s name=Servico.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Servico.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Servico.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Servico.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Servico.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Diario

./kcadm.sh create clients/$CID/roles -s name=Diario.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Diario.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Diario.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Diario.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Diario.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Pessoa

./kcadm.sh create clients/$CID/roles -s name=Pessoa.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pessoa.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pessoa.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pessoa.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pessoa.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade DiarioFoto

./kcadm.sh create clients/$CID/roles -s name=DiarioFoto.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioFoto.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioFoto.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioFoto.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioFoto.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Aplicacao

./kcadm.sh create clients/$CID/roles -s name=Aplicacao.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Aplicacao.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Aplicacao.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Aplicacao.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Aplicacao.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Dispositivo

./kcadm.sh create clients/$CID/roles -s name=Dispositivo.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Dispositivo.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Dispositivo.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Dispositivo.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Dispositivo.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Material

./kcadm.sh create clients/$CID/roles -s name=Material.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Material.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Material.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Material.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Material.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Usuario

./kcadm.sh create clients/$CID/roles -s name=Usuario.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Usuario.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Usuario.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Usuario.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Usuario.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Logradouro

./kcadm.sh create clients/$CID/roles -s name=Logradouro.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Logradouro.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Logradouro.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Logradouro.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Logradouro.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Uf

./kcadm.sh create clients/$CID/roles -s name=Uf.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Uf.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Uf.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Uf.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Uf.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade ModeloCelular

./kcadm.sh create clients/$CID/roles -s name=ModeloCelular.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ModeloCelular.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ModeloCelular.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ModeloCelular.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=ModeloCelular.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade UnidadeMedida

./kcadm.sh create clients/$CID/roles -s name=UnidadeMedida.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=UnidadeMedida.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=UnidadeMedida.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=UnidadeMedida.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=UnidadeMedida.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade DiarioColaborador

./kcadm.sh create clients/$CID/roles -s name=DiarioColaborador.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioColaborador.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioColaborador.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioColaborador.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioColaborador.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade DiarioServico

./kcadm.sh create clients/$CID/roles -s name=DiarioServico.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioServico.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioServico.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioServico.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioServico.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Cotacao

./kcadm.sh create clients/$CID/roles -s name=Cotacao.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cotacao.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cotacao.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cotacao.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cotacao.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Colaborador

./kcadm.sh create clients/$CID/roles -s name=Colaborador.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Colaborador.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Colaborador.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Colaborador.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Colaborador.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade DiarioPrestador

./kcadm.sh create clients/$CID/roles -s name=DiarioPrestador.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioPrestador.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioPrestador.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioPrestador.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=DiarioPrestador.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade PessoaJuridica

./kcadm.sh create clients/$CID/roles -s name=PessoaJuridica.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaJuridica.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaJuridica.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaJuridica.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=PessoaJuridica.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Obra

./kcadm.sh create clients/$CID/roles -s name=Obra.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Obra.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Obra.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Obra.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Obra.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Imagem

./kcadm.sh create clients/$CID/roles -s name=Imagem.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Imagem.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Imagem.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Imagem.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Imagem.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Empresa

./kcadm.sh create clients/$CID/roles -s name=Empresa.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Empresa.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Empresa.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Empresa.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Empresa.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade RequisicaoMaterial

./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterial.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterial.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterial.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterial.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=RequisicaoMaterial.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Pais

./kcadm.sh create clients/$CID/roles -s name=Pais.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pais.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pais.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pais.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Pais.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Cidade

./kcadm.sh create clients/$CID/roles -s name=Cidade.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cidade.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cidade.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cidade.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Cidade.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


# Permissoes da entidade Prestador

./kcadm.sh create clients/$CID/roles -s name=Prestador.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Prestador.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Prestador.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Prestador.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=Prestador.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


