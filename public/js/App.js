window.app = app = new Backbone.Marionette.Application();

app.Router = new Marionette.AppRouter({
	controller: new RouteController({app: app}),
	appRoutes: {
		''         : 'connectionHub',
		'connect'  : 'connectionHub',
	}
});

app.radioChannel = Backbone.Radio.channel('orchestration');

app.on('start', function() {
	Backbone.history.start();
});

app.addRegions({
	main: '#app'
});

$(function startApp() {
	$('body').append('<div id="app"></div>');
	app.start();
});
