var HueLightsConnectionStatusItem = Backbone.Marionette.ItemView.extend({
  ui: {
    'discoverButton': '#discover-devices',
    'discoverButtonText': '#discover-devices-text',
    'discoverButtonSpinner': '#discover-devices .spinner',
    'connectionStatusText': '#hue-lights-connection-status'
  },

  events: {
    'click @ui.discoverButton': 'initializeConnection'
  },

  className: 'status-unconnected',

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
         $(_this.ui.discoverButtonText).text('Reconnect');
         $(_this.ui.discoverButtonSpinner).removeClass('active');
         _this.$el.removeClass('status-unconnected');
         _this.$el.addClass('status-connected');
         $(_this.ui.connectionStatusText).text('Connected');
      }
    });
  }
});
