$( document ).ready(function() {
    $( "#musicStart" ).click(function() {
        var titleM = $("#musicStart").attr("title");
        MIDIjs.play(titleM);
    });
    $ ("#musicStop").click(function () {
        MIDIjs.stop();
    })
    console.log( "ready!" );

});

