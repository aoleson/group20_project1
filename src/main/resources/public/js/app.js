var gameModel;

//This function will be called once the page is loaded.  It will get a new game model from the back end, and display it.
$( document ).ready(function() {

  $.getJSON("model", function( json ) {
    displayGameState(json);
    gameModel = json;
   });
});

function placeShip() {
   // This ajax call will asnychonously call the back end, and tell it where to place the ship, then get back a game model with the ship placed, and display the new model.
   var request = $.ajax({
     url: "/placeShip/"+$( "#shipSelec" ).val()+"/"+$( "#rowSelec" ).val()+"/"+$( "#colSelec" ).val()+"/"+$( "#orientationSelec" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   //This will be called when the call is returned from the server.
   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   // if there is a problem, and the back end does not respond, then an alert will be shown.
   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });
}

//Similar to placeShip, but instead it will fire at a location the user selects.
function fire(){
   var request = $.ajax({
     url: "/fire/"+$( "#colFire" ).val()+"/"+$( "#rowFire" ).val(),
     method: "post",
     data: JSON.stringify(gameModel),
     contentType: "application/json; charset=utf-8",
     dataType: "json"
   });

   request.done(function( currModel ) {
     displayGameState(currModel);
     gameModel = currModel;

   });

   request.fail(function( jqXHR, textStatus ) {
     alert( "Request failed: " + textStatus );
   });

}

//This function will display the game model.  It displays the ships on the users board, and then shows where there have been hits and misses on both boards.
function displayGameState(gameModel){
$( '#MyBoard td'  ).css("background-color", "blue");
$( '#TheirBoard td'  ).css("background-color", "blue");

displayShip(gameModel.aircraftCarrier);
displayShip(gameModel.battleship);
displayShip(gameModel.cruiser);
displayShip(gameModel.destroyer);
displayShip(gameModel.submarine);

for (var i = 0; i < gameModel.computerMisses.length; i++) {
   $( '#TheirBoard #' + gameModel.computerMisses[i].Across + '_' + gameModel.computerMisses[i].Down ).css("background-color", "green");
}
for (var i = 0; i < gameModel.computerHits.length; i++) {
   $( '#TheirBoard #' + gameModel.computerHits[i].Across + '_' + gameModel.computerHits[i].Down ).css("background-color", "red");
}

for (var i = 0; i < gameModel.playerMisses.length; i++) {
   $( '#MyBoard #' + gameModel.playerMisses[i].Across + '_' + gameModel.playerMisses[i].Down ).css("background-color", "green");
}
for (var i = 0; i < gameModel.playerHits.length; i++) {
   $( '#MyBoard #' + gameModel.playerHits[i].Across + '_' + gameModel.playerHits[i].Down ).css("background-color", "red");
}



}


//This function will display a ship given a ship object in JSON
function displayShip(ship){
 startCoordAcross = ship.start.Across;
 startCoordDown = ship.start.Down;
 endCoordAcross = ship.end.Across;
 endCoordDown = ship.end.Down;
 if(startCoordAcross > 0){
    if(startCoordAcross == endCoordAcross){
        for (i = startCoordDown; i <= endCoordDown; i++) {
            $( '#MyBoard #'+startCoordAcross+'_'+i  ).css("background-color", "yellow");
        }
    } else {
        for (i = startCoordAcross; i <= endCoordAcross; i++) {
            $( '#MyBoard #'+i+'_'+startCoordDown  ).css("background-color", "yellow");
        }
    }
 }
}
