set KEYCLOAK_AUTH_SERVER=${keycloak.auth-server-url}
set KEYCLOAK_REALM=${keycloak.realm}
set KEYCLOAK_USER=${keycloak.username}
set KEYCLOAK_PASSWORD=${keycloak.password}
set BACKEND_CLIENT_ID=${keycloak.clientId.backend}
set FRONTEND_CLIENT_ID=${keycloak.clientId.frontend}

REM Navega para o diretório bin do JBoss
cd %JBOSS_HOME%\bin

REM Permissoes da entidade Cliente

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cliente.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cliente.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cliente.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cliente.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cliente.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Dispositivo

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Dispositivo.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Dispositivo.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Dispositivo.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Dispositivo.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Dispositivo.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Empresa

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Empresa.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Empresa.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Empresa.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Empresa.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Empresa.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Noticia

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Noticia.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Noticia.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Noticia.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Noticia.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Noticia.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade KeycloakUsuario

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakUsuario.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakUsuario.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakUsuario.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakUsuario.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakUsuario.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade PessoaContato

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaContato.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaContato.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaContato.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaContato.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaContato.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Cidade

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cidade.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cidade.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cidade.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cidade.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Cidade.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Fabricante

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Fabricante.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Fabricante.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Fabricante.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Fabricante.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Fabricante.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade CategoriaImagem

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaImagem.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaImagem.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaImagem.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaImagem.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaImagem.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Imagem

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Imagem.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Imagem.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Imagem.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Imagem.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Imagem.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade ParametroSistema

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=ParametroSistema.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=ParametroSistema.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=ParametroSistema.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=ParametroSistema.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=ParametroSistema.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade CategoriaAtividade

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaAtividade.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaAtividade.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaAtividade.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaAtividade.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaAtividade.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Funcionario

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Funcionario.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Funcionario.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Funcionario.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Funcionario.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Funcionario.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Pessoa

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pessoa.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pessoa.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pessoa.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pessoa.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pessoa.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade TipoLogradouro

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoLogradouro.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoLogradouro.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoLogradouro.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoLogradouro.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoLogradouro.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade KeycloakToken

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakToken.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakToken.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakToken.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakToken.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakToken.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Uf

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Uf.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Uf.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Uf.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Uf.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Uf.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade PessoaFisica

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaFisica.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaFisica.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaFisica.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaFisica.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaFisica.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Logradouro

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Logradouro.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Logradouro.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Logradouro.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Logradouro.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Logradouro.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade TipoFuncionario

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoFuncionario.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoFuncionario.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoFuncionario.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoFuncionario.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=TipoFuncionario.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade CategoriaNoticia

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaNoticia.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaNoticia.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaNoticia.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaNoticia.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=CategoriaNoticia.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Filial

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Filial.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Filial.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Filial.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Filial.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Filial.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Operadora

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Operadora.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Operadora.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Operadora.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Operadora.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Operadora.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Pais

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pais.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pais.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pais.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pais.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Pais.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Bairro

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Bairro.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Bairro.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Bairro.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Bairro.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Bairro.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade Endereco

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Endereco.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Endereco.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Endereco.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Endereco.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=Endereco.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade KeycloakGrupo

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakGrupo.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakGrupo.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakGrupo.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakGrupo.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=KeycloakGrupo.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%


REM Permissoes da entidade PessoaJuridica

call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaJuridica.list --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaJuridica.view --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaJuridica.insert --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaJuridica.update --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%
call kcadm.bat create clients/%BACKEND_CLIENT_ID%/roles -s name=PessoaJuridica.remove --server %KEYCLOAK_AUTH_SERVER% --realm %KEYCLOAK_REALM% --user %KEYCLOAK_USER% --password %KEYCLOAK_PASSWORD%

