(function($){
  var project = Backbone.Model.extend({
    defaults: {
      type: 'project'
    }
  }),

  account = Backbone.Model.extend({
    defaults: {
      type: 'account'
    }
  });

  var career = Backbone.Collection.extend({
    model: function(model, options) {
    switch(model.type) {
        case 'project':
            return new project(model, options);
            break;
        case 'account':
            return new folder(model, options);
            break;
          }
        }
      });


  // **ListView class**: Our main app view.
  var ListView = Backbone.View.extend({
    el: $('body'), // attaches `this.el` to an existing element.

    events: {
      'click button#viewDetails':'viewDetails'
    },

    // `initialize()`: Automatically called upon instantiation. Where you make all types of bindings, _excluding_ UI events, such as clicks, etc.
    initialize: function(){
      _.bindAll(this, 'render'); // fixes loss of context for 'this' within methods
      this.collection=new Career();

      this.collection.bind('getAccountData',this.viewDetails) //biding viewDetails of View to 'getAccountData' of the Collection.
      // todo: need to link Collection to API.

       this.render(); // not all views are self-rendering. This one is.
    },
    // `render()`: Function in charge of rendering the entire view in `this.el`. Needs to be manually called by the user.
    render: function(){
      $(this.el).append("<h1>an empty backbone index page</h1>");
      $(this.el).append("<button id='viewDetails'>View details</button>");
      $(this.el).append("<ul></ul>");
    },

    viewDetails: function(){
      $('ul',this.el).append("<li>details are coming</li>");
    }
  });

  // **listView instance**: Instantiate main app view.
  var listView = new ListView();
})(jQuery);
