#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#!/bin/bash
########################################################################################
# Script para criar no keycloak, as roles do client - backend
# @author Cleber Moura <cleber.t.moura@gmail.com> - 05/Abr/2017
########################################################################################

function pause(){
   read -p "$*"
}

#Configuração da autenticação no Keycloak
REALM=${symbol_dollar}{keycloak.realm}
CLIENT_NAME=${symbol_dollar}{project.artifactId}-backend
AUTH_SERVER_URL=${symbol_dollar}{keycloak.auth-server-url}
ROOT_URL=${symbol_dollar}{keycloak.rootUrl}
KEYCLOAK_USER=admin
KEYCLOAK_PASSWORD=123123

# Verifica se a variável KEYCLOAK_HOME está definida
if [ "x$KEYCLOAK_HOME" = "x" ]; then
    echo "A variavel de ambiente KEYCLOAK_HOME precisa estar definida ."
    exit
else
	KEYCLOAK_BIN_FOLDER=$KEYCLOAK_HOME/bin
fi

cd $KEYCLOAK_BIN_FOLDER

# Recupera o clientId para inclusão das roles.

CLIENTS=$(./kcadm.sh get clients --fields id,clientId --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD)
CID=$(echo $CLIENTS | jq -c '.[] | select(.clientId | contains("${symbol_dollar}{project.artifactId}-backend")) | .id' | sed -e 's/^"//' -e 's/"$//')

pause "Pressione [Enter] para adicionar os roles do "$CLIENT_NAME

# Criação das roles
./kcadm.sh create clients/$CID/roles -s name=${rootArtifactId}.logar.sistema -s 'description=Logar no sistema' --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

pause "Pressione [Enter] para adicionar os composite roles (${rootArtifactId}.admin, ${rootArtifactId}.usuario) do "$CLIENT_NAME

# Criação das composite roles (perfis)
./kcadm.sh create clients/$CID/roles -s name=${rootArtifactId}.usuario -s 'description=Composite role de Usuário' -s composite=true --server \
 $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

C_ROLE_USUARIO_ID=$(echo $(./kcadm.sh get-roles --cclientid $CLIENT_NAME --rolename ${rootArtifactId}.usuario --fields id \
--server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD) | cut -d '"' -f 4)

./kcadm.sh add-roles --rid $C_ROLE_USUARIO_ID --cclientid $CLIENT_NAME --rolename ${rootArtifactId}.logar.sistema \
--server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

./kcadm.sh create clients/$CID/roles -s name=${rootArtifactId}.admin -s 'description=Composite role de Administrador' -s composite=true --server $AUTH_SERVER_URL \
--realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

C_ROLE_ADMIN_ID=$(echo $(./kcadm.sh get-roles --cclientid $CLIENT_NAME --rolename ${rootArtifactId}.admin --fields id \
--server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD) | cut -d '"' -f 4)

./kcadm.sh add-roles --rid $C_ROLE_ADMIN_ID --cclientid $CLIENT_NAME --rolename ${rootArtifactId}.logar.sistema \
--server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

pause "Presione [Enter] para finalizar..."