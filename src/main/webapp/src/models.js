// src/models.js

var app = app || {};

app.project = Backbone.Model.extend({
   urlRoot: '/api/projects',
//   defaults: {
//	   id: '',
//	   status: '',
//	   clients: '',
//	   team: ''
//   }
 });

app.account = Backbone.Model.extend({
   urlRoot: '/api/accounts',
//   defaults: {
//	   id: '',
//	   status: ''
//   }
 });
