var ChromecastConnectionStatusItem = Backbone.Marionette.View.extend({
  ui: {
    'chromecastButton': '#chromecast-launcher',
    'chromecastIcon': '#chromecast-launcher > img',
    'connectionStatusText': '#chromecast-connection-status'
  },

  events: {
    'click @ui.chromecastButton': 'launchChromecast',
  },

  className: 'status-unconnected',

  initialize: function() {
    var _this = this;
    $(window).bind('beforeunload',function(){
      _this.model.endSession();
    });
  },

  render: function() {
    var _this = this;
    $.get('/templates/chromecast_connection_status.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
    }, 'html');
  },

  launchChromecast: function(e) {
    e.preventDefault();
    this.model.requestSession();
    this.$el.removeClass('status-unconnected');
    this.$el.addClass('status-connected');
    $(this.ui.connectionStatusText).text('Connected');
  }
});
