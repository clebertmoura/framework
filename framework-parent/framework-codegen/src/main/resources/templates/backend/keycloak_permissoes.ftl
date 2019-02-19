<#list entitiesMetamodel as entity>

REM Permissoes da entidade ${entity.simpleName}

./kcadm.sh create clients/$CID/roles -s name=${entity.simpleName}.list --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=${entity.simpleName}.view --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=${entity.simpleName}.insert --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=${entity.simpleName}.update --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
./kcadm.sh create clients/$CID/roles -s name=${entity.simpleName}.remove --server $AUTH_SERVER_URL --realm $REALM --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

</#list>

