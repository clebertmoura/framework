(function () {
  'use strict';
  var inheritance = angular.module('app.shared');

  inheritance.controller('BaseController', BaseController);

  BaseController.$inject = ['vm', 'factory', '$routeParams', '$rootScope', 'FlashFactory', '$filter', '$location', 'Constants', 'entityName', 'entity'];


  /**
   * Controlador básico que fornece métodos de paginação e crud.
   */
  function BaseController(vm, factory, $routeParams, $rootScope, FlashFactory, $filter, $location, Constants, entityName, entity) {

    vm.pageSize = Constants.maxPageRegisters;
    vm.numberOfPages = 0;
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
    vm.get = get;
    vm.isClean = isClean;
    vm.handleErrorResponse = handleErrorResponse;

    vm.entity = entity || {};

    vm.activate = activate;

    console.debug('chamou o BaseController');

    function activate() {
      var path = $location.path();
      vm.editMode = path.indexOf('/edit/') > -1;
      vm.viewMode = path.indexOf('/view/') > -1;
      if (vm.editMode || vm.viewMode) {
        vm.get();
      } else {
    	vm.search();
      }
    };

    function search() {
      console.debug('chamou o search');
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
    
    function get() {
      var successCallback = function(data){
          vm.original = data;
          angular.forEach(vm.original, function(value, key) {
        	  if (value != null) {
        		  if (moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid()) {
        			  vm.original[key] = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZ").toDate();
        		  } else if (moment(value, "YYYY-MM-DD", true).isValid()) {
        			  vm.original[key] = moment(value, "YYYY-MM-DD").toDate();
        		  } else if (moment(value, "HH:mm:ss.SSSZ", true).isValid()) {
        			  vm.original[key] = moment(value, "HH:mm:ss.SSSZ").toDate();
        		  }
        	  }
          }, vm.original);
          vm.entity = new factory(vm.original);
      };
      var errorCallback = function() {
          FlashFactory.setMessage({'type': 'error', 'text': '' + entityName + ' não encontrado.'});
          $location.path("/" + entityName);
      };
      factory.get({EntityId:$routeParams.EntityId}, successCallback, errorCallback);
    };
        
    function isClean() {
      return angular.equals(vm.original, vm.entity);
    };

    function cancel() {
       $location.path("/" + entityName);
    };
    
    function edit(obj) {
      $location.path("/" + entityName + "/edit/" + obj.id);
    };
    
    function editInView() {
      vm.editMode = true;
      vm.viewMode = false;
    };

    function view(obj) {
      vm.editMode = false;
      vm.viewMode = true;
      $location.path("/" + entityName + "/view/" + obj.id);
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