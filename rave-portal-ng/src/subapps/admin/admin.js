define(function(require) {
  var angular = require('angular');

  var admin = angular.module('admin', ['ngResource']);

  var categoriesResource = require('./categories/resources/categories');
  admin.factory('categoriesResource', categoriesResource);

  var categoryResource = require('./categories/resources/category');
  admin.factory('categoryResource', categoryResource);

  var categoriesCtrl = require('./categories/controllers/categories');
  admin.controller('categoriesCtrl', categoriesCtrl);

  var categoryCtrl = require('./categories/controllers/category');
  admin.controller('categoryCtrl', categoryCtrl);

  return admin;
});
