#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#!/bin/bash
########################################################################################
# Script para criar no keycloak, o client - backend
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
	
pause "Pressione [Enter] para adicionar o Cliente '"$CLIENT_NAME"' no Realm '"$REALM"' no Keycloak."

#Criação do cliente no Keycloak
CID=$(./kcadm.sh create clients -r $REALM -s clientId=$CLIENT_NAME -s 'rootUrl="$ROOT_URL"' -s 'redirectUris=["*"]' -s 'baseUrl="/$CLIENT_NAME"' -s 'webOrigins=["*"]' \
--server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD -i)

pause "Pressione [Enter] para recuperar o JSON de instalação do "$CLIENT_NAME

./kcadm.sh get clients/$CID/installation/providers/keycloak-oidc-keycloak-json --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

pause "Presione [Enter] para finalizar..."
