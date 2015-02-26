wisdomApp.factory('apiService', ['$resource',
  function($resource) {
    return $resource('api/wisdom/:id', {}, {
      // Get certain or all category
      get_category: {method: 'GET', isArray: false},
      // Add as POST
      save: {method: 'POST'}
    });
  }
]);