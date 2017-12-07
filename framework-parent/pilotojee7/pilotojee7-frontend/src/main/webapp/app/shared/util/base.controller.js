(function () {
    'use strict';
    var inheritance = angular.module('app.shared');

    inheritance.controller('BaseController', BaseController);

    BaseController.$inject = ['vm', 'factory', '$routeParams', '$rootScope', '$scope', 'FlashFactory', '$filter', '$location', 'Constants', 'entityName', 'toastr', 'StringUtil'];


    /**
     * Controlador básico que fornece métodos de paginação e crud.
     */
    function BaseController(vm, factory, $routeParams, $rootScope, $scope, FlashFactory, $filter, $location, Constants, entityName, toastr, StringUtil) {

        vm.pageSize = Constants.maxPages;
        vm.registersPerPage = Constants.maxPageRegisters;
        vm.numberOfPages = 0;
        vm.totalRecords = 0;
        vm.entityName = entityName;
        vm.currentPage = 1;
        vm.editMode = false;
        vm.viewMode = false;
        vm.search = search;
        vm.clean = clean;
        vm.previous = previous;
        vm.next = next;
        vm.setPage = setPage;
        vm.original = {};
        vm.searchData = {};
        vm.searchResult = {};
        vm.filteredResults = [];
        vm.pageRange = [];

        vm.performSearch = performSearch;
        vm.getRestrictions = getRestrictions;
        vm.getOrderings = getOrderings;
        vm.insert = insert;
        vm.update = update;
        vm.save = save;
        vm.cancel = cancel;
        vm.editInView = editInView;
        vm.edit = edit;
        vm.view = view;
        vm.remove = remove;
        vm.getById = getById;
        vm.get = get;
        vm.isClean = isClean;
        vm.getEnumLabel = getEnumLabel;
        vm.loadEntityRelations = loadEntityRelations;
        vm.handleErrorResponse = handleErrorResponse;

        vm.factory = factory;

        vm.entity = vm.entity || new factory();

        vm.activate = activate;

        vm.removeBase64FromImages = removeBase64FromImages;

        console.debug('chamou o BaseController');

        function activate() {
            var path = $location.path();
            var isCurrent = path.indexOf('/' + vm.entityName + '/') > -1 || path.endsWith('/' + vm.entityName);
            vm.editMode = path.indexOf('/edit/') > -1;
            vm.viewMode = path.indexOf('/view/') > -1;
            if (vm.editMode || vm.viewMode) {
                if (isCurrent) {
                    vm.get();
                }
            } else {
                vm.search();
            }
        }

        function search() {
            vm.currentPage = 1;
            vm.performSearch();
        }

        function clean() {
            vm.searchData = {};
            vm.currentPage = 1;
            vm.performSearch();
        }

        function previous() {
            if (vm.currentPage > 1) {
                vm.currentPage--;
                vm.performSearch();
            }
        }

        function next() {
            if (vm.currentPage < (vm.numberOfPages - 1)) {
                vm.currentPage++;
                vm.performSearch();
            }
        }

        /*function setPage(n) {
            if (vm.currentPage != n) {
                vm.currentPage = n;
                vm.performSearch();
            }
        }*/
        
        function setPage() {
            vm.performSearch();
        }

        function getRestrictions() {
            return [];
        }

        function getOrderings() {
            return [];
        }

        function performSearch() {
            var vRestrictions = vm.getRestrictions();
            var vOrderings = vm.getOrderings();
            var postData = {
                first: (vm.currentPage - 1) * vm.registersPerPage,
                max: vm.registersPerPage,
                restrictions: vRestrictions != null ? vRestrictions : [],
                orderings: vOrderings != null ? vOrderings : []
            };

            vm.searchResult = factory.queryAll(postData, function () {
            	vm.totalRecords = vm.searchResult.totalRecords;
                vm.numberOfPages = Math.ceil(vm.totalRecords / vm.registersPerPage);
                vm.filteredResults = $filter('searchFilter')(vm.searchResult.results, vm);
            });
            return vm.searchResult;
        }

        function loadEntityRelations() {
        }

        function getById(id) {
            var successCallback = function (data) {
                vm.original = data;
                angular.forEach(data, function (value, key) {
                    if (value != null) {
                        if (moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid()) {
                            vm.entity[key] = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZ").toDate();
                        } else if (moment(value, "YYYY-MM-DD", true).isValid()) {
                            vm.entity[key] = moment(value, "YYYY-MM-DD").toDate();
                        } else if (moment(value, "HH:mm:ss.SSSZ", true).isValid()) {
                            vm.entity[key] = moment(value, "HH:mm:ss.SSSZ").toDate();
                        } else {
                            vm.entity[key] = value;
                        }
                    } else {
                        vm.entity[key] = value;
                    }
                }, vm.entity);
                //vm.entity = new factory(vm.original);
                if (vm.loadEntityRelations) {
                    vm.loadEntityRelations();
                }
            };
            var errorCallback = function () {
                toastr.error(entityName + ' não encontrado.');
                //FlashFactory.setMessage({'type': 'error', 'text': '' + entityName + ' não encontrado.'});
                $location.path("/" + entityName);
            };
            return factory.get({EntityId: id}, successCallback, errorCallback);
        }

        function get() {
            vm.getById($routeParams.EntityId);
        }

        function isClean() {
            return angular.equals(vm.original, vm.entity);
        }

        function cancel() {
            if (vm.editMode) {
                vm.editMode = false;
            }
            vm.viewMode = false;
            $location.path("/" + entityName);
        }

        function edit(obj) {
            vm.editMode = true;
            vm.viewMode = false;
            $scope.$broadcast('entity:edit', obj);
            $location.path("/" + entityName + "/edit/" + obj.id);
        }

        $scope.$on('entity:edit', function (event, data) {

            vm.editMode = true;
            vm.viewMode = false;
        });

        function editInView() {
            vm.editMode = true;
            vm.viewMode = false;
            $scope.$broadcast('entity:edit', vm.entity);
        }

        function view(obj) {
            $scope.$broadcast('entity:view', obj);
            vm.editMode = false;
            vm.viewMode = true;
            $location.path("/" + entityName + "/view/" + obj.id);
        }

        $scope.$on('entity:view', function (event, data) {
            vm.editMode = false;
            vm.viewMode = true;
        });

        function getEnumLabel(key, enumList) {
            if (enumList && enumList.length > 0) {
                for (var i = 0; i < enumList.length; i++) {
                    var item = enumList[i];
                    if (item.key == key) {
                        return item.label;
                    }
                }
            }
            return key;
        }

        function handleErrorResponse(response) {
            if (response) {
                if (response.status == 400) { // se for um Bad Request
                    if (response.data) {
                        if (angular.isArray(response.data)) {
                            for (var i = 0; response.data.length; i++) {
                                var message = response.data[i];
                                toastr.error(StringUtil.camelCaseToNormal(message.code) + ': ' + message.description);
                                //FlashFactory.setMessage({'type': 'error', 'text': message.code + ': ' + message.description}, true);
                            }
                        } else {
                            toastr.error(response.data.code + ': ' + response.data.description);
                            //FlashFactory.setMessage({'type': 'error', 'text': response.data.code + ': ' + response.data.description}, true);
                        }
                    }
                } else {
                    if (response.data) {
                        toastr.error(response.data.code + ': ' + response.data.description);
                        //FlashFactory.setMessage({'type': 'error', 'text': response.data.code + ': ' + response.data.description}, true);
                    } else {
                        toastr.error('Algo deu errado. Por favor, tente novamente!');
                        //FlashFactory.setMessage({'type': 'error', 'text': 'Algo deu errado. Por favor, tente novamente!'}, true);
                    }
                }
            } else {
                toastr.error('Algo deu errado. Por favor, tente novamente!');
                //FlashFactory.setMessage({'type': 'error', 'text': 'Algo deu errado. Por favor, tente novamente!'}, true);
            }
        }

        function insert() {
            var successCallback = function (data, responseHeaders) {
                toastr.success(StringUtil.camelCaseToNormal(entityName) + ' criado com sucesso.');
                //FlashFactory.setMessage({'type':'success','text': '' + entityName + ' criado com sucesso.'}, false);
                $location.path('/' + entityName);
            };
            var errorCallback = function (response) {
                console.error("Erro no insert: ", response);
                vm.handleErrorResponse(response);
            };
            factory.save(vm.entity, successCallback, errorCallback);
        }


        function update() {
            var successCallback = function () {
                toastr.success(StringUtil.camelCaseToNormal(entityName) + ' atualizado com sucesso.');
                //FlashFactory.setMessage({'type':'success','text': '' + entityName +' atualizado com sucesso.'}, true);
                //vm.activate();
                vm.cancel();
            };
            var errorCallback = function (response) {
                vm.handleErrorResponse(response);
            };
            vm.entity.$update(successCallback, errorCallback);
        }

        function save() {
            vm.removeBase64FromImages();
            if (vm.entity.id) {
                vm.update();
            } else {
                vm.insert();
            }
        }

        function remove(obj) {
            var successCallback = function () {
                toastr.warning(StringUtil.camelCaseToNormal(entityName) + ' removido com sucesso.');
                //FlashFactory.setMessage({'type': 'error', 'text': '' + entityName +' removido com sucesso.'}, true);
                vm.performSearch();
            };
            var errorCallback = function (response) {
                vm.handleErrorResponse(response);
            };
            vm.entity = new factory(obj);
            vm.entity.$remove(successCallback, errorCallback);
        }

        function removeBase64FromImages() {
            if (vm.entity.hasOwnProperty("imagens")) {
                if(vm.entity.imagens != null) {
                    if(vm.entity.imagens.length > 0) {
                        vm.entity.imagens = vm.entity.imagens.map(imagem => {
                                return {
                                    nome: imagem.nome,
                                    contentType: imagem.contentType,
                                    fileExtension: imagem.fileExtension,
                                    length: imagem.length,
                                    data: ((imgm = imagem.data) => {
                                        if(imgm && imgm != null && imgm != ''){
                            var b64 = imgm;
                            var index = b64.lastIndexOf(';base64,');
                            return b64.substr(index+8);
                        }
                        return imgm;
                    })()
                    };
                    });
                    }
                }
            }
        }

    }

})();