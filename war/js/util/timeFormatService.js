wisdomApp.service('timeFormatService', ['log', 'Constants', function(log, Constants){

    return {
        getFormattedTime : function(time){

            var date = new Date(time);

            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var h = date.getHours();
            var min = date.getMinutes();
            // var w = time.getDay();

            if (m < 10) {
                m = '0' + m;
            }

            if (d < 10) {
                d = '0' + d;
            }

            var result = y + '/' + m + '/' + d + ' ' + h + ':' + min;

            log.d("format time: " + result);

            return result;
        }

    };
}]);