// js/models/todo.js

 var app = app || {};

 // Project and Account Model

 var project = Backbone.Model.extend({
   url: '/api/projects'
 });

 var account = Backbone.Model.extend({
   url: '/api/accounts'
 });
