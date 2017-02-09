package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import spark.Spark.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.util.Random;                    //For random placement of AI ships

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function should return a new model

    /* Modified by aoleson on 2/1/17.
     * aoleson (1/30/17): This function is called when the page loads. It creates a new model of the game,
     * randomly places the AI's ships on the board, and  stringifies the model with GSON and sends it back.
     */
    static String newModel() {
         //Instantiate model, GSON and random objects plus needed vars
        BattleshipModel modelObj = new BattleshipModel();
        Gson gson = new Gson();
        Random rand = new Random();
        int grid_min = 1;
        int grid_max = 10;

        //Get random starting coordinates for each ship, making sure they're not the same
        int air_across = rand.nextInt((6 - grid_min) + 1) + grid_min;       //Get random int in [1,6] (inclusive)
        int air_down = rand.nextInt((10 - grid_min) + 1) + grid_min;

        int bat_across = rand.nextInt((7 - grid_min) + 1) + grid_min;
        int bat_down = rand.nextInt((10 - grid_min) + 1) + grid_min;
        while (bat_down == air_down)                                                //If it's the same as one of the others, reroll random int
            bat_down = rand.nextInt((10 - grid_min) + 1) + grid_min;

        int cru_across = rand.nextInt((8 - grid_min) + 1) + grid_min;
        int cru_down = rand.nextInt((10 - grid_min) + 1) + grid_min;
        while (cru_down == air_down || cru_down == bat_down)
            cru_down = rand.nextInt((10 - grid_min) + 1) + grid_min;

        int des_across = rand.nextInt((9 - grid_min) + 1) + grid_min;
        int des_down = rand.nextInt((10 - grid_min) + 1) + grid_min;
        while (des_down == air_down || des_down == bat_down || des_down == cru_down)
            des_down = rand.nextInt((10 - grid_min) + 1) + grid_min;

        int sub_across = rand.nextInt((9 - grid_min) + 1) + grid_min;
        int sub_down = rand.nextInt((10 - grid_min) + 1) + grid_min;
        while (sub_down == air_down || sub_down == bat_down || sub_down == cru_down || sub_down == des_down)
            sub_down = rand.nextInt((10 - grid_min) + 1) + grid_min;

        //Place aircraft carrier on the board. (length 5)
        modelObj.computer_aircraftCarrier.start.Across = air_across;
        modelObj.computer_aircraftCarrier.start.Down= air_down;
        modelObj.computer_aircraftCarrier.end.Across = (air_across + 4);
        modelObj.computer_aircraftCarrier.end.Down = air_down;

        //Place battleship on the board. (length 4)
        modelObj.computer_battleship.start.Across = bat_across;
        modelObj.computer_battleship.start.Down= bat_down;
        modelObj.computer_battleship.end.Across = (bat_across + 3);
        modelObj.computer_battleship.end.Down = bat_down;

        //Place cruiser on the board. (length 3)
        modelObj.computer_cruiser.start.Across = cru_across;
        modelObj.computer_cruiser.start.Down= cru_down;
        modelObj.computer_cruiser.end.Across = (cru_across + 2);
        modelObj.computer_cruiser.end.Down = cru_down;

        //Place destroyer on the board. (length 2)
        modelObj.computer_destroyer.start.Across = des_across;
        modelObj.computer_destroyer.start.Down= des_down;
        modelObj.computer_destroyer.end.Across = (des_across + 1);
        modelObj.computer_destroyer.end.Down = des_down;

        //Place aircraft carrier on the board. (length 2)
        modelObj.computer_submarine.start.Across = sub_across;
        modelObj.computer_submarine.start.Down= sub_down;
        modelObj.computer_submarine.end.Across = (sub_across + 1);
        modelObj.computer_submarine.end.Down = sub_down;

        //Convert to JSON object and to string
        String model = gson.toJson(modelObj);
        //Return the model in string format (GSON needed)
        return model;

    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req) {
        Gson gson = new Gson();
        String json = req.body();
        BattleshipModel gameState = gson.fromJson(json, BattleshipModel.class);

        String ret = gson.toJson(gameState);
        System.out.println(ret);

        return gameState;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {

        //Gets model and converts
        BattleshipModel game = getModelFromReq(req);
        Gson gson = new Gson();

        //Creates array of users_ships to check whats on the board
        BattleshipModel.Ship[] user_ships = {game.aircraftCarrier, game.battleship, game.cruiser, game.destroyer, game.submarine};

        //Takes user's request and makes variables from it
        String shipname = req.params("id");
        String arow = req.params("row");
        int across = Integer.parseInt(arow);
        String dcol = req.params("col");
        int down = Integer.parseInt(dcol);
        String orientation = req.params("orientation");
        int length = 0;
        Boolean isHoz = false;

        if (orientation.equals("horizontal")){
            isHoz = true;
        }

        //Compares the shipname to all existing ships on the board to check if already placed
        switch(shipname) {
            case "aircraftCarrier":
                length = 5;
                break;
            case "battleship":
                length = 4;
                break;
            case "cruiser":
                length = 3;
                break;
            case "destroyer":
                length = 2;
                break;
            case "submarine":
                length = 2;
                break;
        }

        //create blank array that simulates the board
        int[][] boardarr = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int a = 0; a < 10; a++)
                boardarr[i][a] = 0;
        }

        //look at the info for the current game state, and place boards according to where they are located
        //spaces that are 0 are empty, spaces that have a 1 are full
        for (BattleshipModel.Ship ship : user_ships){
            if (ship != null){
                if(ship.start.Across == ship.end.Across){
                    //horizontal
                    for (int i = 0; i < ship.length; i++){
                        boardarr[ship.start.Down][ship.start.Across+i] = 1;
                    }
                } else {
                    //vertical
                    for (int i = 0; i < ship.length; i++){
                        boardarr[ship.start.Down+i][ship.start.Across] = 1;
                    }
                }
            }
        }

        //compare where the user wants to go to the board
        if(isHoz){
            //player wants to put it hoz
            for(int i = 0; i < length; i++){
                if(boardarr[down][across + 1] == 1){

                }
            }
        } else {
            //they want it vertical
            for(int i = 0; i < length; i++){
                if(boardarr[down + 1][across] == 1){

                }
            }
        }
        //if the ship can not fit, return null
        //if the ship can fit,
        //place it into the game state, convert it back to JSON, and return it
        switch(shipname) {
            case "aircraftCarrier":
                game.aircraftCarrier.start.Across = across;
                game.aircraftCarrier.start.Down = down;
                if(isHoz) {
                    game.aircraftCarrier.end.Across = across + length;
                    game.aircraftCarrier.end.Down = down;
                }
                else {
                    game.aircraftCarrier.end.Across = across;
                    game.aircraftCarrier.end.Down = down + length;
                }
                break;
            case "battleship":
                game.battleship.start.Across = across;
                game.battleship.start.Down = down;
                if(isHoz){
                    game.battleship.end.Across = across + length;
                    game.battleship.end.Down = down;
                } else {
                    game.battleship.end.Across = across;
                    game.battleship.end.Down = down + length;
                }
                break;
            case "cruiser":
                game.cruiser.start.Across = across;
                game.cruiser.start.Down = down;
                if(isHoz){
                    game.cruiser.end.Across = across + length;
                    game.cruiser.end.Down = down;
                } else {
                    game.cruiser.end.Across = across;
                    game.cruiser.end.Down = down + length;
                }
                break;
            case "destroyer":
                game.destroyer.start.Across = across;
                game.destroyer.start.Down = down;
                if(isHoz){
                    game.destroyer.end.Across = across + length;
                    game.destroyer.end.Down = down;
                } else {
                    game.destroyer.end.Across = across;
                    game.destroyer.end.Down = down + length;
                }
            case "submarine":
                game.submarine.start.Across = across;
                game.submarine.start.Down = down;
                if(isHoz){
                    game.submarine.end.Across = across + length;
                    game.submarine.end.Down = down;
                } else {
                    game.submarine.end.Across = across;
                    game.submarine.end.Down = down + length;
                }
                break;
        }

        //ship is now: in the board, serialize and return state
        String ret = gson.toJson(game);
        System.out.println(ret);
        return ret;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req)
    {
        Random num = new Random();

        BattleshipModel game = getModelFromReq(req);

        BattleshipModel.GridSquare square = new BattleshipModel.GridSquare();
        square.Across = Integer.parseInt(req.params("col"));
        square.Down = Integer.parseInt(req.params("row"));

        System.out.println(square.Across);
        System.out.println(square.Down);

        BattleshipModel.Ship[] user_ships = {game.aircraftCarrier, game.battleship, game.cruiser, game.destroyer,
                                             game.submarine};
        BattleshipModel.Ship[] AI_ships = {game.computer_aircraftCarrier, game.computer_battleship,
                                           game.computer_cruiser, game.computer_destroyer, game.computer_submarine};

        for (int i = 0; i < 5; i++) {
            if (AI_ships[i].start.Across == square.Across && AI_ships[i].end.Across == square.Across) {     // "vertical"
                if (AI_ships[i].start.Down <= square.Down && square.Down <= AI_ships[i].end.Down) {
                    game.playerHits[game.get_num_hits_misses(game.playerHits)] = square;
                    break;
                } else {
                    game.playerMisses[game.get_num_hits_misses(game.playerMisses)] = square;
                    break;
                }
            } else if (AI_ships[i].start.Down == square.Down && AI_ships[i].end.Down == square.Down) {      // "horizontal"
                if (AI_ships[i].start.Across <= square.Across && square.Across <= AI_ships[i].end.Across) {
                    game.playerHits[game.get_num_hits_misses(game.playerHits)] = square;
                    break;
                } else {
                    game.playerMisses[game.get_num_hits_misses(game.playerMisses)] = square;
                    break;
                }
            }
        }
        square.Down = (num.nextInt(10)) + 1;
        square.Across = (num.nextInt(10)) + 1;

        for (int i = 0; i < 5; i++) {
             if (user_ships[i].start.Across == square.Across && user_ships[i].end.Across == square.Across) {     // "vertical"
                if (user_ships[i].start.Down <= square.Down && square.Down <= user_ships[i].end.Down) {
                    game.computerHits[game.get_num_hits_misses(game.computerHits)] = square;
                    break;
                } else {
                    game.computerMisses[game.get_num_hits_misses(game.computerHits)] = square;
                    break;
                }
            } else if (user_ships[i].start.Down == square.Down && user_ships[i].end.Down == square.Down) {      // "horizontal"
                if (user_ships[i].start.Across <= square.Across && square.Across <= user_ships[i].end.Across) {
                    game.computerHits[game.get_num_hits_misses(game.computerHits)] = square;
                    break;
                } else {
                    game.computerMisses[game.get_num_hits_misses(game.computerMisses)] = square;
                    break;
                }
            }
        }

        Gson gson = new Gson();
        String ret = gson.toJson(game);
        return ret;
    }
}
