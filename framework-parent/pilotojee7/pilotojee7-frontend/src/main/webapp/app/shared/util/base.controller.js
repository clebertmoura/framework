(function () {
  'use strict';
  var inheritance = angular.module('app.shared');

  inheritance.controller('BaseController', BaseController);

  BaseController.$inject = ['vm', 'factory', '$routeParams', '$rootScope', '$scope', 'FlashFactory', '$filter', '$location', 'Constants', 'entityName'];


  /**
   * Controlador básico que fornece métodos de paginação e crud.
   */
  function BaseController(vm, factory, $routeParams, $rootScope, $scope, FlashFactory, $filter, $location, Constants, entityName) {

    vm.pageSize = Constants.maxPageRegisters;
    vm.numberOfPages = 0;
    vm.entityName = entityName;
    vm.currentPage = 0;
    vm.editMode = false;
    vm.viewMode = false;
    vm.search = search;
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
    vm.editInView  = editInView;
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
    };

    function search() {
      vm.currentPage = 0;
      vm.performSearch();
    };


    function previous() {
      if(vm.currentPage > 0) {
          vm.currentPage--;
          vm.performSearch();
      }
    };
    
    function next() {
      if(vm.currentPage < (vm.numberOfPages - 1) ) {
          vm.currentPage++;
          vm.performSearch();
      }
    };
    
    function setPage(n) {
      if (vm.currentPage != n) {
          vm.currentPage = n;
          vm.performSearch();
      }
    };
    
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
          first: vm.currentPage * vm.pageSize,
          max: vm.pageSize,
          restrictions: vRestrictions != null ? vRestrictions : [],
          orderings: vOrderings != null ? vOrderings : []
      };

      vm.searchResult = factory.queryAll(postData, function(){
        vm.numberOfPages = Math.ceil(vm.searchResult.totalRecords / vm.pageSize);
        var max = (vm.numberOfPages == 0) ? 1 : vm.numberOfPages;
        vm.pageRange = [];
        for(var ctr=0; ctr<max; ctr++) {
            vm.pageRange.push(ctr);
        }
        vm.filteredResults = $filter('searchFilter')(vm.searchResult.results, vm);
      });
    };
	
	function loadEntityRelations(){
    };
    
    function getById(id) {
        var successCallback = function(data){
        	vm.original = data;
            angular.forEach(data, function(value, key) {
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
        var errorCallback = function() {
            FlashFactory.setMessage({'type': 'error', 'text': '' + entityName + ' não encontrado.'});
            $location.path("/" + entityName);
        };
        return factory.get({EntityId:id}, successCallback, errorCallback);
    };
    
    function get() {
    	vm.getById($routeParams.EntityId);
    };
        
    function isClean() {
      return angular.equals(vm.original, vm.entity);
    };

    function cancel() {
      if (vm.editMode) {
    	  vm.editMode = false;
      }
      vm.viewMode = false;
      $location.path("/" + entityName);
    };
    
    function edit(obj) {
      vm.editMode = true;
      vm.viewMode = false;
      $scope.$broadcast('entity:edit', obj);
      $location.path("/" + entityName + "/edit/" + obj.id);
    };
    
    $scope.$on('entity:edit', function (event, data) {
    	console.log("listen entity:edit", data);
    	vm.editMode = true;
        vm.viewMode = false;
	});
    
    function editInView() {
      vm.editMode = true;
      vm.viewMode = false;
      $scope.$broadcast('entity:edit', vm.entity);
    };

    function view(obj) {
      $scope.$broadcast('entity:view', obj);
      vm.editMode = false;
      vm.viewMode = true;
      $location.path("/" + entityName + "/view/" + obj.id);
    };
    
    $scope.$on('entity:view', function (event, data) {
    	console.log("listen entity:edit", data);
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
    };
    
    function handleErrorResponse(response) {
    	if (response) {
  		  if (response.status == 400) { // se for um Bad Request
  			  if (response.data) {
  				  if (angular.isArray(response.data)) {
  					  for(var i=0; response.data.length; i++) {
  						  var message = response.data[i];
  						  FlashFactory.setMessage({'type': 'error', 'text': message.code + ': ' + message.description}, true);
  					  }
  				  } else {
  					  FlashFactory.setMessage({'type': 'error', 'text': response.data.code + ': ' + response.data.description}, true);
  				  }
  			  }
  		  } else {
  			  if (response.data) {
  				  FlashFactory.setMessage({'type': 'error', 'text': response.data.code + ': ' + response.data.description}, true);
  			  } else {
  				  FlashFactory.setMessage({'type': 'error', 'text': 'Algo deu errado. Por favor, tente novamente!'}, true);
  			  }
  		  }
  	  } else {
        FlashFactory.setMessage({'type': 'error', 'text': 'Algo deu errado. Por favor, tente novamente!'}, true);
      }
    };

    function insert() {
      var successCallback = function(data,responseHeaders){
          FlashFactory.setMessage({'type':'success','text': '' + entityName + ' criado com sucesso.'}, false);
          $location.path('/' + entityName);
      };
      var errorCallback = function(response) {
    	  console.error("Erro no insert: ", response);
    	  vm.handleErrorResponse(response);
      };
      factory.save(vm.entity, successCallback, errorCallback);
    };


    function update() {
      var successCallback = function(){
          FlashFactory.setMessage({'type':'success','text': '' + entityName +' atualizado com sucesso.'}, true);
          vm.activate();
      };
      var errorCallback = function(response) {
    	  console.error("Erro no update: ", response);
    	  vm.handleErrorResponse(response);
      };
      vm.entity.$update(successCallback, errorCallback);
    };
    
    function save() {
      if (vm.entity.id) {
    	  vm.update();
      } else {
    	  vm.insert();
      }
    };

    function remove(obj) {
      var successCallback = function() {
          FlashFactory.setMessage({'type': 'error', 'text': '' + entityName +' removido com sucesso.'}, true);
          vm.performSearch();
      };
      var errorCallback = function(response) {
    	  console.error("Erro no remove: ", response);
    	  vm.handleErrorResponse(response);
      }; 
      vm.entity = new factory(obj);
      vm.entity.$remove(successCallback, errorCallback);
    };

  }

})();