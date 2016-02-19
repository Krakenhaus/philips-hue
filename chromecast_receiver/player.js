function startTime() {
  var today = new Date();
  var h = today.getHours();
  var m = today.getMinutes();
  var s = today.getSeconds();
  m = checkTime(m);
  s = checkTime(s);
  $('#clock').text((h % 12) + ":" + m);
  var t = setTimeout(startTime, 500);
}

function checkTime(i) {
  // Add zero in front of numbers < 10
  if (i < 10) {i = "0" + i};
  return i;
}

function loadImage(data) {
  // Preload the next image
  $('#next-image').attr('src', data.url);
  setTimeout(function(){
    $('#next-image').css({'opacity':1.0});
  }, data.preloadWait);

  // Get ready for next preloaded image
  $('#next-image').bind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd", function(){
    // Backdrops with class 'visible' do not have a fade animation
    $('#current-image').attr('src', data.url);
    $('#next-image').addClass('visible');
    $('#next-image').css({'opacity':0});
    $('#next-image').removeClass('visible');
  });
}

function setupChromcast() {
  var castReceiverManager = cast.receiver.CastReceiverManager.getInstance();
  var customMessageBus = castReceiverManager.getCastMessageBus('urn:x-cast:com.home.dashboard', cast.receiver.CastMessageBus.MessageType.JSON);

  customMessageBus.onMessage = function(event) {
    loadImage(event.data);

    // send something back
    customMessageBus.send(event.senderId, {
      requestId: event.data.requestId,
      data: 'ack'
    });
  };

  castReceiverManager.start();
}

setupChromcast();
startTime();
