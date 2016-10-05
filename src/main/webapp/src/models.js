// src/models.js

var app = app || {};

app.project = Backbone.Model.extend({
   url: '/api/projects',
//   defaults: {
//	   id: '',
//	   status: '',
//	   clients: '',
//	   team: ''
//   }
 });

app.account = Backbone.Model.extend({
   url: '/api/accounts',
//   defaults: {
//	   id: '',
//	   status: ''
//   }
 });
