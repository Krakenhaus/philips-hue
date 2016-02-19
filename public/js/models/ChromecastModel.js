var ChromecastModel = Backbone.Model.extend({
  session: null,

  initializeCastApi: function() {
    var appId = '76676808'
    var sessionRequest = new chrome.cast.SessionRequest(appId);
    var apiConfig = new chrome.cast.ApiConfig(sessionRequest, this._sessionListener, this.receiverListener);
    chrome.cast.initialize(apiConfig, this._onInitSuccess, this.handleChromecastError.bind(this));
  },

  requestSession: function() {
    chrome.cast.requestSession(this._onRequestSessionSuccess.bind(this), this.handleChromecastError.bind(this));
  },

  receiverListener: function(e) {
    if( e === chrome.cast.ReceiverAvailability.AVAILABLE) {
      // Should I be doing something here???
    }
  },

  handleChromecastError: function(error) {
    console.log('Chromecast Error:', error);
  },

  setImage: function(imageUrl) {
    var message = JSON.stringify({
      url: imageUrl,
      preloadWait: app.preloadWait
    });

    this.session.sendMessage('urn:x-cast:com.home.dashboard', message, this._messageSentSuccess, this.handleChromecastError);
  },

  endSession: function() {
    if(this.session) {
      this.session.stop();
    }
  },

  _sessionListener: function(e) {
    this.session = e;
  },

  _onRequestSessionSuccess: function(e) {
    this.session = e;
  },

  _onInitSuccess: function(e) {
    // Do something, like show the cast button
  },

  _messageSentSuccess: function() {
    console.log('update sent');
  }
});


window['__onGCastApiAvailable'] = function(loaded, error) {
  if (loaded) {
    ChromecastModel.prototype.initializeCastApi();
  } else {
    ChromecastModel.prototype.handleChromecastError(error)
  }
}
