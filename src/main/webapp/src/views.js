// src/views.js

var app = app || {};

// this is a main view of the application
var AppView=Backbone.View.extend({

  //biding to root element in index.html
  el: $('body'),

  // NB. These are the DOM events (not Backbone Event API).
  events: {
	  'click #add-new':'addNew',
	  'click #view-all':'viewAll',
    'click #test':'test'
  },

  initialize: function(){
    //need to bind key elements in the view to app attributes
    this.listenTo(app.AccountsColl, 'fetch', this.viewAll);
    this.listenTo(app.AccountsColl, 'change', this.test);

  },

  addNew: function(){
    console.log("addNew fired")
    //bugs ahead var view=new app.AccountView({model:account});
	  // this.$('#projects').append(view.render().el)

  },

  viewAll: function(){
    app.AccountColl.fetch()
	  console.log("viewAll was pressed")
  },

  test: function(){
    alert('test')
  }


});


// // a few to handle an individual account items
// app.AccountView=Backbone.View.extend({
// 	el: '#account-list',
// 	statsTemplate: _.template( $('#account-template').html() ),
//
// })
//
// // a few to handle an individual project items
// app.ProjectView=Backbone.View.extend({
//
// })
