var ChromecastHub = Backbone.Marionette.View.extend({
  session: null,
  ui: {
    'chromecastButton': '#chromecast-launcher',
    'chromecastIcon': '#chromecast-launcher > img',
    'imageUrl': '#image-url',
    'lightsAndImageButton': '#go'
  },

  events: {
    'click @ui.chromecastButton': 'launchChromecast',
    'click @ui.lightsAndImageButton': 'triggerLightsAndImageMessage'
  },

  initialize: function() {
    var _this = this;
    app.radioChannel.on('orchestration:imageWithLights', this.setChromecastImage.bind(this));
    $(window).bind('beforeunload',function(){
      _this.stopChromecast();
    });
  },

  render: function() {
    var _this = this;
    $.get('/templates/chromecast_hub.html', function (data) {
      template = _.template(data, {  });
      _this.$el.html(template);  
    }, 'html');

    window['__onGCastApiAvailable'] = function(loaded, error) {
      if (loaded) {
        _this.initializeCastApi();     
      } else {
        _this.handleChromecastError(error)
      }
    }
  },

  handleChromecastError: function(error) {
    console.log('Chromecast Error:', error);
  },

  triggerLightsAndImageMessage: function(e) {
    e.preventDefault();
  
    var imageUrl = $(this.ui.imageUrl).val();
    app.radioChannel.trigger('orchestration:imageWithLights', imageUrl);
  },

  setChromecastImage: function(imageUrl){
    if (!this.session) {
      this.handleChromecastError("no session");
      return;
    }
 
    var mediaInfo = new chrome.cast.media.MediaInfo(imageUrl);
    mediaInfo.contentType = 'image/jpg';
  
    var mediaRequest = new chrome.cast.media.LoadRequest(mediaInfo);
    mediaRequest.autoplay = true;

    this.session.loadMedia(mediaRequest);
  },

  launchChromecast: function(e) {
    chrome.cast.requestSession(this.onRequestSessionSuccess.bind(this), this.handleChromecastError('launch error'));
  },
  
  onRequestSessionSuccess: function(e) {
    this.session = e;
    $(this.ui.chromecastIcon).attr('src', '../images/chromecast/ic_cast_connected_24dp.svg');
    this.setChromecastImage('http://www.creativewalls101.com/imgs/sendImages.jpg');
  },

  initializeCastApi: function() {
    var sessionRequest = new chrome.cast.SessionRequest(chrome.cast.media.DEFAULT_MEDIA_RECEIVER_APP_ID);
    var apiConfig = new chrome.cast.ApiConfig(sessionRequest, this.sessionListener, this.receiverListener);
    chrome.cast.initialize(apiConfig, this.onInitSuccess, this.handleChromecastError('could not initialize cast api'));
  },

  onInitSuccess: function(e) {
    // Do something, like show the cast button
  },

  receiverListener: function(e) {
    if( e === chrome.cast.ReceiverAvailability.AVAILABLE) {
      // Should I be doing something here???
    }
  },

  sessionListener: function(e) {
    this.session = e;
  },

  stopChromecast: function() {
    if(this.session) {
      this.session.stop();
    }
  }
});
