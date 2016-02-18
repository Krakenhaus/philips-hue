var BridgeModel = Backbone.Model.extend({

  initializeConnection: function() {
    var permissions = {
      username: "newdeveloper",
      ip: "192.168.1.7"
    };

    $.ajax({
      type: 'POST',
      url: '/bridge/connect',
      data: JSON.stringify(permissions),
      success: function() {
        console.log('success');
      },
      contentType: 'application/json'
    });
  },

  pollConnection: function(callback) {
    $.get('/bridge/status', function (data) {
      if(typeof callback == 'function') {
        callback.call(this, data);
      }
    }, 'json');
  }
});
