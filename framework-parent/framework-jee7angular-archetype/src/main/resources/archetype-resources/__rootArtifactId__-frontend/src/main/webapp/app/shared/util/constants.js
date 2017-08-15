#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
'use strict';

angular.module('app.shared').constant("Constants", {
    backendUrl: "${symbol_dollar}{backendUrl}",
    maxPages: 5, //quantidade máxima de páginas na paginação das telas de listagem
    maxPageRegisters: 10, //quantidade máxima de registros por página,
    headerTokenName: 'X-Auth-Token', //nome do header do request que armazena o token de autenticação
    enums: {
        authorised: {
            authorised: 0,
            loginRequired: 1,
            notAuthorised: 2
        },
        permissionCheckType: {
            atLeastOne: 0,
            combinationRequired: 1
        }
    },
    routes: {
        login: '/',
        notAuthorised: '/not-authorised'
    }
});