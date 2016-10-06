// src/views.js

var app = app || {};

// this is a main view of the application
app.AppView=Backbone.View.extend({

  //biding to root element in index.html
  tagName: 'body',

  // NB. These are the DOM events (not Backbone Event API).
  events: {
	  'click #add-new':'addNew',
	  'click #view-all':'viewAll',
    'keydown':'keyAction'
  },

  initialize: function(){
    //need to bind key elements in the view to app attributes
    //e.g. this.allCheckbox = this.$('#toggle-all')[0];

    //Views is listening to the 'add' events in the accountColl

    console.log(this.events)
    // this.listenTo(app.account, 'addNew', this.addNew);
    this.listenTo(app.accountsColl, 'fetch', this.viewAll);
    this.viewAll()
  },

  addNew: function(account){
    console.log(this.$('#add-new'))
    // var view=new app.AccountView({model:account});
	  // this.$('#projects').append(view.render().el)

  },

  viewAll: function(accountColl){
	  console.log("viewAll was pressed")
  },

  keyAction: function(e) {
  if (e.which === ENTER_KEY) {
    console.log('blabla');
  }
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
