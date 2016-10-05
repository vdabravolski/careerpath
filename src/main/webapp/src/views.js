// src/views.js

var app = app || {};

// this is a main view of the application
app.AppView=Backbone.View.extend({

  //biding to root element in index.html
  tagName: 'body',
  
  // NB. These are the DOM events (not Backbone Event API).
  events: {
	  'keypress #add-new':'addNew',
	  'keypress #view-all':'viewAll'  
  },

  initialize: function(){
    //need to bind key elements in the view to app attributes
    //e.g. this.allCheckbox = this.$('#toggle-all')[0];

    //Views is listening to the 'add' events in the accountColl
    this.listenTo(app.accountsColl, 'add', this.addOne);
  },
  
  addNew: function(account){
	  var view=new app.AccountView({model:account});
	  this.$('#projects').append(view.render().el)
  },
  
  viewAll: function(){
	  //wip
  }

});


// a few to handle an individual account items
app.AccountView=Backbone.View.extend({
	el: '#account-list',
	statsTemplate: _.template( $('#account-template').html() ),
	
})

// a few to handle an individual project items
app.ProjectView=Backbone.View.extend({
	
})