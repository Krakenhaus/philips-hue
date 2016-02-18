var RouteController = Marionette.Controller.extend({
  initialize: function(options) {
    this.app = options.app;
  },

  connectionHub: function() {
    this.app.getRegion('main').show(new ConnectionHub());
  }
});