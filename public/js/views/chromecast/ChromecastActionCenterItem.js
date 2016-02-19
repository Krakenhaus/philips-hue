var ChromecastActionCenterItem = Backbone.Marionette.ItemView.extend({
  currentImageIndex: 0,

  ui: {
    'imageUrl': '#image-url',
    'lightsAndImageButton': '#go',
    'connectionStatusText': '#chromecast-connection-status',
    'fave': '#chromecast-fave',
    'imageNameText': '#image-name'
  },

  events: {
    'click @ui.lightsAndImageButton': 'triggerLightsAndImageMessage',
    'click @ui.fave': 'toggleFave'
  },

  initialize: function() {
    app.radioChannel.on('orchestration:imageWithLights', this.setChromecastImage.bind(this));
  },

  render: function() {
    var _this = this;
    $.get('/templates/chromecast_action_center.html', function (data) {
      template = _.template(data, {});
      _this.$el.html(template);
    }, 'html');
  },

  setChromecastImage: function(imageUrl){
    this.model.setImage(imageUrl);
  },

  triggerLightsAndImageMessage: function() {
    var _this=  this;
    this.currentImageIndex = Math.floor(Math.random() * CHROMECAST_IMAGES.numImages);
    var imageUrl = CHROMECAST_IMAGES.imageList[this.currentImageIndex].url;

    $(this.ui.imageNameText).text(imageUrl);

    app.radioChannel.trigger('orchestration:imageWithLights', imageUrl);
    setTimeout(function() {
     _this.triggerLightsAndImageMessage();
    }, 40000);
  },

  toggleFave: function() {
    if(CHROMECAST_IMAGES.imageList[this.currentImageIndex].fave === 0) {
      CHROMECAST_IMAGES.imageList[this.currentImageIndex].fave = 1;
    } else {
      CHROMECAST_IMAGES.imageList[this.currentImageIndex].fave = 0;
    }
    this._updateFaveIcon(CHROMECAST_IMAGES.imageList[this.currentImageIndex].fave);
  },

  _updateFaveIcon: function(faveStatus) {
    if(CHROMECAST_IMAGES.imageList[this.currentImageIndex].fave === 0) {
      $(this.ui.fave + '> span').removeClass('glyphicon-star');
      $(this.ui.fave + '> span').addClass('glyphicon-star-empty');

    } else {
      $(this.ui.fave + '> span').addClass('glyphicon-star');
      $(this.ui.fave + '> span').removeClass('glyphicon-star-empty');
    }

  }
});
