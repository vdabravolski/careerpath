// src/collections.js

// initialize app if one doesn't exist.
var app = app || {};


// Accounts and Projects collections.
var accountsColl = Backbone.Collection.extend({
  model: app.account,
  url: '/api/accounts'
    });

var projectsColl = Backbone.Collection.extend({
  model: app.project,
  url: '/api/projects'
});


// Create our global collection of **Todos**.
app.accountsColl = new accountsColl();
app.projectsColl = new projectsColl();
