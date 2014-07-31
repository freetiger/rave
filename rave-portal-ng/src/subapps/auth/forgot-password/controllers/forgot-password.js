define(function(require) {
  var auth = require('../../auth');
  require('../providers/forgot-password-api');

  auth.controller('forgotPasswordController', [
    '$scope', 'forgotPasswordApi',
    function($scope, forgotPasswordApi) {

      // We call this method when they submit the form.
      $scope.submit = function(email) {

        // Do nothing if they haven't included an email.
        if (!email) {
          return;
        }

        forgotPasswordApi.forgotPassword(email)

          .then(function(res) {
            $scope.invalid = false;
            $scope.success = true;
            $scope.email = email;
          })

          .catch(function(res) {
            $scope.invalid = true;
          });
      };
    }
  ]);
});
