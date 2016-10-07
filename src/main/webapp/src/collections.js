// src/collections.js

var app = app || {};
// Accounts and Projects collections.
var accountsColl = Backbone.Collection.extend({
  model: Account,
  url: '/api/accounts'
    });

var projectsColl = Backbone.Collection.extend({
  model: Project,
  url: '/api/projects'
});

app.AccountColl = new accountsColl();
app.ProjectColl = new projectsColl();
