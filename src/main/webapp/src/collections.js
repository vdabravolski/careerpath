// src/collections.js

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

app.AccountColl = new accountsColl();
app.ProjectColl = new projectsColl();

// Create our global collection of **Todos**.
//app.accountsColl = new accountsColl();
//app.projectsColl = new projectsColl();
