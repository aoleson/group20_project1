# cs361 Project 1
THIS IS the code you will use as a starting point for Project 1.  The code you have been given has the front end for a [battleship](https://en.wikipedia.org/wiki/Battleship_(game)) game.
As is often the case when developing real-world software, you and your team have been given a front end, and are responsible for developing a back-end that will make the game work correctly.

# Concepts
Two commonly used methods for a request-response between a client and server are: `GET` and `POST`.

`GET` - Requests data from a specified resource

`POST` - Submits data to be processed to a specified resource

# Instructions

The game should operate as follows:

1) Load the HTML page (provided for you in `sampleBattleShip/src/main/resources/public/index.html`)

2) When the HTML page is loaded, it will send a `GET` request to the back-end. 

3) The back-end will create a new game model, and convert it to JSON before sending it to the front-end. If you want the game state to display without having to modify the front-end, the game state should look something like this:

```json
{
    "aircraftCarrier": {
        "name": "AircraftCarrier",
        "length": 5,  
        "start": { "Across": 0,"Down": 0 },
        "end": {"Across": 0, "Down": 0}
    },
    "battleship": {
        "name": "Battleship",
        "length": 4,
        "start": { "Across": 0,"Down": 0 },
        "end": {"Across": 0, "Down": 0}
    },
    "cruiser": {
        "name": "Cruiser",
        "length": 3,
        "start": { "Across": 0,"Down": 0 },
        "end": {"Across": 0, "Down": 0}
    },
    "destroyer": {
        "name": "Destroyer",
        "length": 2,
        "start": { "Across": 0,"Down": 0 },
        "end": {"Across": 0, "Down": 0}
    },
    "submarine": {
        "name": "Submarine",
        "length": 2,
       "start": { "Across": 0,"Down": 0 },
        "end": {"Across": 0, "Down": 0}
    },
    "computer_aircraftCarrier": {
        "name": "Computer_AircraftCarrier",
        "length": 5,
        "start": { "Across": 2,"Down": 2 },
        "end": {"Across": 2, "Down": 7}
    },
    "computer_battleship": {
        "name": "Computer_Battleship",
        "length": 4,
        "start": { "Across": 2,"Down": 8 },
        "end": {"Across": 6, "Down": 8}
    },
    "computer_cruiser": {
        "name": "Computer_Cruiser",
        "length": 3,
        "start": { "Across": 4, "Down": 1 },
        "end": {"Across": 4, "Down": 4}
    },
    "computer_destroyer": {
        "name": "Computer_Destroyer",
        "length": 2,
        "start": { "Across": 7, "Down": 3 },
        "end": {"Across": 7, "Down": 5}
    },
    "computer_submarine": {
        "name": "Computer_Submarine",
        "length": 2,
        "start": { "Across": 9, "Down": 6 },
        "end": {"Across": 9, "Down": 8}
    },
    "playerHits": [],
    "playerMisses": [],
    "computerHits": [],
    "computerMisses": []
}
```

4) The user will then place a ship on the board using the user interface. This will result in a `POST` being sent to the back-end. This post will be sent to a REST URL:
```
http://localhost:4567/placeShip/{shipname}/{across}/{down}/{horizontal | vertical}
```
an example request is:
```
http://localhost:4567/placeShip/aircraftCarrier/1/1/horizontal
```
This is a `POST` request, and it will also send the current game state via JSON (which was previously received from the back-end).
The back end should correctly place the ship where the user has requested it, and then return a new game state.
For this example request, the model response would include:
```
...
{"name":"AircraftCarrier","length":5,"start":{"Across":1,"Down":1},"end":{"Across":1,"Down":6}},
...
```

5) The user will then fire at the computer ship. This will result in a `POST` being sent to the back-end. This post will be sent to a REST URL:
```
http://http://localhost:4567/fire/{across}/{down}
```
an example request is:
```
http://http://localhost:4567/fire/4/3
```
This is a `POST` request, and it will also send the current game state via JSON (which was previously received from the back-end).
The back end should put tell the user if it was a hit or miss, and then it should fire at the user and tell them if they were hit or missed also. It should also then return a new game state.
For this example request, the model response would include:
```
...
"playerHits":[],
"playerMisses":[{"Across":4,"Down":3}],
"computerHits":[{"Across":2,"Down":2}],
"computerMisses":[]}
...
```


6) The `POST` requests will continue for each user input until the game state reaches the end of that particular game of Battleship.


# Tips and tricks:

To implement this code, you will be using [Java Spark](http://sparkjava.com).

You will make your life MUCH easier if you use [GSON](https://github.com/google/gson) to seralize/deserialize the JSON objects to/from java objects.

You might also find the jquery documentation useful.
