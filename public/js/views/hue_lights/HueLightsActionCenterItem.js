var HueLightsActionCenterItem = Backbone.Marionette.ItemView.extend({
  render: function() {
    var _this = this;
    $.get('/templates/hue_lights_action_center.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
    }, 'html');
  }
});
