var ConnectionHub = Backbone.Marionette.LayoutView.extend({
  regions: {
    hueLightsRegion : '#hue-lights-region',
    chromecastRegion: '#chromecast-region'
  },

  initialize: function() {
    
    
  },

  showModules: function() {
    var hueLightsHub = new HueLightsHub();
    this.hueLightsRegion.show(hueLightsHub);

    var chromecastHub = new ChromecastHub();
    this.chromecastRegion.show(chromecastHub);
  },

  render: function() {
    var _this = this;
    $.get('/templates/connection_hub.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
      _this.showModules(); 
    }, 'html');
  }
});
