(function($){

  var project = Backbone.Model.extend({
    url: '/api/projects'
  });

  var account = Backbone.Model.extend({
    url: '/api/accounts'
  });

  var accountsColl = Backbone.Collection.extend({
    model: account,
    url: '/api/accounts'
      });

  var projectsColl = Backbone.Collection.extend({
    model: project,
    url: '/api/projects'
  });

  var Accounts = new accountsColl;
  var Projects = new projectsColl;

  var AccountView=Backbone.View.extend({
    tagname: 'li',
    accountTpl: _.template( $('#item-template').html() ),
    events:{
      'keypress #viewDetails': 'viewDetails'
    },

    viewDetails: function(){
      //is coming.
    },

    // Called when the view is first created
    initialize: function() {
      this.$el = $('#dashboard');
    // Later we'll look at:
    // this.listenTo(someCollection, 'all', this.render);
    // but you can actually run this example right now by
    // calling todoView.render();
  },

    render: function() {
      this.$el.html(this.accountTpl( this.model.attributes ) )
      return this
    }



  });

  var accountView= new AccountView({model: account})

  // //todo: need to rewrite complete Dashboard view below.
  // // **DashboardView class**: Our main app view.
  // var DashboardView = Backbone.View.extend({
  //   el: $('body'), // attaches `this.el` to an existing element.
  //   events: {
  //     'click button#viewDetails':'viewDetails'
  //   },
  //
  //   // `initialize()`: Automatically called upon instantiation. Where you make all types of bindings, _excluding_ UI events, such as clicks, etc.
  //   initialize: function(){
  //     _.bindAll(this, 'render'); // fixes loss of context for 'this' within methods
  //     this.collection=new accountsColl();
  //
  //     this.collection.bind('getAccountData',this.viewDetails) //biding viewDetails of View to 'getAccountData' of the Collection.
  //     // todo: need to link Collection to API.
  //
  //      this.render(); // not all views are self-rendering. This one is.
  //   },
  //   // `render()`: Function in charge of rendering the entire view in `this.el`. Needs to be manually called by the user.
  //   render: function(){
  //     $(this.el).append("<h1>an empty backbone index page</h1>");
  //     $(this.el).append("<button id='viewDetails'>View details</button>");
  //     $(this.el).append("<ul></ul>");
  //   }
  //
  // });
  //
  // // **DashboardView instance**: Instantiate main app view.
  // var DashboardView = new DashboardView();
})(jQuery);
