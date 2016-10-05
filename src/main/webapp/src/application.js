
// js/views/app.js

var app = app || {};

var app.AppView=Backbone.View.extend({

  //biding to root element in index.html
  el: '#careerpath',

  initialize: function(){
    //need to bind key elemets in the view to app attributes
    //e.g. this.allCheckbox = this.$('#toggle-all')[0];

    //need to listen to events
    //e.g. this.listenTo(app.Todos, 'add', this.addOne);
  }

)};


//
