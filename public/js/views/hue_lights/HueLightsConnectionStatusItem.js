var HueLightsConnectionStatusItem = Backbone.Marionette.ItemView.extend({
  ui: {
    'discoverButton': '#discover-devices',
    'discoverButtonText': '#discover-devices-text',
    'discoverButtonSpinner': '#discover-devices .spinner'
  },

  events: {
    'click @ui.discoverButton': 'initializeConnection'
  },

  render: function() {
    var _this = this;
    $.get('/templates/hue_lights_connection_status.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
    }, 'html');
  },

  initializeConnection: function(e) {
    e.preventDefault();
    this.model.initializeConnection();

    $(this.ui.discoverButtonText).text('Connecting...');
    $(this.ui.discoverButtonSpinner).addClass('active');
    this.pollConnection();
  },

  pollConnection: function() {
    var _this = this;
    this.model.pollConnection(function(status) {
      if(status !== 'CONNECTED') {
        setTimeout(function() {_this.pollConnection();}, 200);
      }else{
         $(_this.ui.discoverButtonText).text('Connected!');
         $(_this.ui.discoverButtonSpinner).removeClass('active');
      }
    });
  }
});
