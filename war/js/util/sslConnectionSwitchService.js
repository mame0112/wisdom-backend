wisdomApp.service('sslConnectionSwitchService', ['$location', 'log', '$window', function($location, log, $window){

    return {
        forceSslConnection : function()
        {
            //If we use this in local development environment, we shouldn't use https.
            if($location.absUrl().indexOf("appspot") != -1){
                if ($location.protocol() !== 'https') {
                    $window.location.href = $location.absUrl().replace('http', 'https');
                }
            }
        }
    };
}]);