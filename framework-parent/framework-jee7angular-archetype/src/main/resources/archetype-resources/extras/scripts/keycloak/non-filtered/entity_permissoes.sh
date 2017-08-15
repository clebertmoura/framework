#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#!/bin/bash
# Script para criar no keycloak, as permissoes
# @author Cleber Moura <cleber.t.moura@gmail.com> - 05/Abr/2017

AUTH_SERVER_URL=${symbol_dollar}{keycloak.auth-server-url}
REALM=${symbol_dollar}{keycloak.realm}
KEYCLOAK_USER=${symbol_dollar}{keycloak.username}
KEYCLOAK_PASSWORD=${symbol_dollar}{keycloak.password}

cd ${symbol_dollar}JBOSS_HOME/bin

CLIENTS=${symbol_dollar}(./kcadm.sh get clients --fields id,clientId --server ${symbol_dollar}AUTH_SERVER_URL --realm ${symbol_dollar}REALM --user ${symbol_dollar}KEYCLOAK_USER --password ${symbol_dollar}KEYCLOAK_PASSWORD)
CID=${symbol_dollar}(echo ${symbol_dollar}CLIENTS | jq -c '.[] | select(.clientId | contains("${symbol_dollar}{project.artifactId}-backend")) | .id' | sed -e 's/^"//' -e 's/"${symbol_dollar}//')

