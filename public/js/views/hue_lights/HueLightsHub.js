var HueLightsHub = Backbone.Marionette.LayoutView.extend({
  regions: {
    connectionStatusRegion: '#hue-lights-connection-status-region',
    actionsCenterRegion   : '#hue-lights-actions-center-region'
  },

  model: HueLightsModel,

  initialize: function() {
    this.model = new HueLightsModel();
    app.radioChannel.on('orchestration:imageWithLights', this.setLightsToImage.bind(this));
  },

  showModules: function() {
    var bridgeModel = new BridgeModel();
    var hueLightsConnectionStatusItem = new HueLightsConnectionStatusItem({model: bridgeModel});
    this.connectionStatusRegion.show(hueLightsConnectionStatusItem);

    var hueLightsActionCenterItem = new HueLightsActionCenterItem({model: this.model});
    this.actionsCenterRegion.show(hueLightsActionCenterItem);
  },

  render: function() {
    var _this = this;
    $.get('/templates/hue_lights_hub.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
      _this.showModules();
    }, 'html');
  },

  /***********************************
  / Events that require orchestration
  /***********************************/
  setLightsToImage: function(imageUrl) {
    this.model.setToImage(imageUrl);
  }
});
