angular.module('app.shared').value('locationParser', function(responseHeaders){
    // Get the Location header and parse it.
    console.debug(responseHeaders);
    var locationHeader = responseHeaders('Location');

    var fragments = locationHeader.split('/');
    var id = fragments[fragments.length -1];
    return id;
});