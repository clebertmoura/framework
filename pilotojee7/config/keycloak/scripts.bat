set JBOSS_HOME=C:\DEV\AppServer\wildfly-15.0.1.Final
set JBOSS_BIN=%JBOSS_HOME%\bin
set KEYCLOAK_SERVER=http://localhost:8080/auth
set KEYCLOAK_ADMIN_USER=admin
set KEYCLOAK_ADMIN_PASS=admin
set REALM_NAME=pilotojee7
set CLIENT_BACKEND=pilotojee7-backend
set CLIENT_FRONTEND=pilotojee7-frontend

echo "Realizando autenticação no Keycloak"

call %JBOSS_BIN%\kcadm config credentials --server %KEYCLOAK_SERVER% --realm master --user %KEYCLOAK_ADMIN_USER% --password %KEYCLOAK_ADMIN_PASS%

echo "Criando realm..."

call %JBOSS_BIN%\kcadm create realms -s realm=%REALM_NAME% -s enabled=true -o

echo "Criando client para o backend..."

call %JBOSS_BIN%\kcadm create clients -r %REALM_NAME% -f cli_backend.json -s clientId=%CLIENT_BACKEND% -s "name=%CLIENT_BACKEND%" -i > %CLIENT_BACKEND%_clientId.txt

echo "Criando client para o frontend..."

call %JBOSS_BIN%\kcadm create clients -r %REALM_NAME% -f cli_frontend.json -s clientId=%CLIENT_FRONTEND% -s "name=%CLIENT_FRONTEND%" -i > %CLIENT_FRONTEND%_clientId.txt

echo "Setando variaveis BE_CID e FE_CID com os respectivos clientID..."

set /p BE_CID=<%CLIENT_BACKEND%_clientId.txt
set /p FE_CID=<%CLIENT_FRONTEND%_clientId.txt

echo "Criar roles para backend..."

call %JBOSS_BIN%\kcadm create clients/%BE_CID%/roles -r %REALM_NAME% -s name=%CLIENT_BACKEND%.acesso.api

echo "Criar roles para frontend..."

call %JBOSS_BIN%\kcadm create clients/%FE_CID%/roles -r %REALM_NAME% -s name=%CLIENT_FRONTEND%.login

echo "Deve vincular manualmente a role '%CLIENT_BACKEND%.acesso.api' ao client frontend"
echo "Deve vincular manualmente a role 'view-profile' ao client frontend"