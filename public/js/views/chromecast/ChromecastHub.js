var ChromecastHub = Backbone.Marionette.LayoutView.extend({
  regions: {
    connectionStatusRegion: '#chromecast-connection-status-region',
    actionsCenterRegion   : '#chromecast-actions-center-region'
  },

  className: 'row',

  model: new ChromecastModel(),

  showModules: function() {
    var chromecastConnectionStatusItem = new ChromecastConnectionStatusItem({model: this.model});
    this.connectionStatusRegion.show(chromecastConnectionStatusItem);

    var chromecastActionCenterItem = new ChromecastActionCenterItem({model: this.model});
    this.actionsCenterRegion.show(chromecastActionCenterItem);
  },

  render: function() {
    var _this = this;
    $.get('/templates/chromecast_hub.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
      _this.showModules();
    }, 'html');
  }
});
