$( document ).ready(function() {
    $( "#musictitle" ).click(function() {
        var titleM = $("#musictitle").text();
        alert(titleM);
        MIDIjs.play(titleM);
    });
    console.log( "ready!" );
});

