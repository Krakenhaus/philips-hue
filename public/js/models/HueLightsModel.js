var HueLightsModel = Backbone.Model.extend({

  setToImage: function(imageUrl) {
    $.ajax({
      type: 'POST',
      url: '/lights/image',
      data: JSON.stringify({'url': imageUrl}),
      success: function() {
      },
      contentType: 'application/json'
    });
  }
});
