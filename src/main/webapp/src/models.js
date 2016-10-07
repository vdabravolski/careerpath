// src/models.js

var app = app || {};

var Project = Backbone.Model.extend({
   urlRoot: '/api/projects'
 });

var Account = Backbone.Model.extend({
   urlRoot: '/api/accounts'
 });
