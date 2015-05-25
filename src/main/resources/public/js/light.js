var templateData;
var data;

$(document).ready(function() {
    fetchTemplate("light");
    //setColor(222,122,0);
});


var setColor = function(r, g, b, brightness, on, id) {
    $.ajax({
      type: "POST",
      url: "lights/" + id,
      headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
        },
      data: JSON.stringify({"r":r, "g":g, "b":b, "is-on":on, "brightness":brightness}),
      dataType: "json",
      success: function(data) {
        updateGeneralInfo(data.identifier, r, g, b);

        // Update a light form that tracks the light color
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR: " + errorThrown);
      }

    });
}


var fetchTemplate = function(templateName) {

    $.ajax({
        url:"templates/" + templateName + ".htm",
        success: function(template){ 
            templateData = template
            fetchData()
        },
        error: function(error) {
            console.log("error");
        }
    });
}

var fetchData = function() {
    $.ajax({
        url:"http://localhost:8080/lights",
        success: function(data){ 
            fillTemplate(data);
        },
        error: function(error) {
            console.log("error");
        }
    });
}

var onlineLight = function(id) {
    var online = $("#color-picker-"+id).data("visible");
    if(online) {
        //el.css("display","block");
    }else {
        //el.css("display","none");
    }

}

var fillTemplate = function(data) {
    console.log(data);
    for (var light in data) {
        var obj = data[light];
        // pass the template html and the data from the ajax call into Mustache's "to_html" function
        var html = Mustache.to_html(templateData, obj);
        $('#light-panels').append(html);

        onlineLight(obj.identifier);
        
    }
    $("[name='my-checkbox']").bootstrapSwitch();


    $('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
       id = $(this).parent().parent().parent().data("target");
        setColor(1, 1, 1, 1, state, id);
    });


     $(".circleBaseMain").click(function() {
        fetchData();
        //onlineLight($(this).attr("id"));
     });

    $(".circleBase").click(function() {
        var id = $(this).parent().data("target");
        var rgb = $(this).css("background-color");

        colorsOnly = rgb.substring(rgb.indexOf('(') + 1, rgb.lastIndexOf(')')).split(/,\s*/),
        r = colorsOnly[0],
        g = colorsOnly[1],
        b = colorsOnly[2],

        setColor(r, g, b, 250, true, id);
    });

}

var updateGeneralInfo = function(id, r, g, b) {
    console.log(r);
    $("#" + id).css("background-color", "rgba(" + r + ", " + g + ", " + b + ", 1)");
}