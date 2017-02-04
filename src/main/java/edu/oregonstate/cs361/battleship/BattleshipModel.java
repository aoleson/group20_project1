package edu.oregonstate.cs361.battleship;

/**
 * Skeleton created by michaelhilton on 1/26/17.
 * Fleshed out by leian7 on 1/28/17.
 */

public class BattleshipModel {

    // Represents a specific square on the game board.
    public static class GridSquare {
        int Across;
        int Down;
    }

    public static class Ship {
        String name;
        int length;
        GridSquare start;
        GridSquare end;

        // Constructor: initializes name and length
        Ship(String name, int length) {
            this.name = name;
            this.length = length;
            this.start = new GridSquare();  // ship head
            this.end = new GridSquare();    // ship butt
        }
    }

    // User's ships:
    Ship aircraftCarrier;
    Ship battleship;
    Ship cruiser;
    Ship destroyer;
    Ship submarine;

    // AI's ships:
    Ship computer_aircraftCarrier;
    Ship computer_battleship;
    Ship computer_cruiser;
    Ship computer_destroyer;
    Ship computer_submarine;

    /* Game stats: Can be populated as 2D array like {{x, y}, ...}, or can
    simply hold a list of relevant GridSquare objects */
    int[] playerHits;
    int[] playerMisses;
    int[] computerHits;
    int[] computerMisses;

    // Constructor: Creates each ship and initializes their names and lengths
    public BattleshipModel() {
        this.aircraftCarrier = new Ship("AircraftCarrier", 5);
        this.battleship = new Ship("Battleship", 4);
        this.cruiser = new Ship("Cruiser", 3);
        this.destroyer = new Ship("Destroyer", 2);
        this.submarine = new Ship("Submarine", 2);

        this.computer_aircraftCarrier = new Ship("Computer_AircraftCarrier", 5);
        this.computer_battleship = new Ship("Computer_Battleship", 4);
        this.computer_cruiser = new Ship("Computer_Cruiser", 3);
        this.computer_destroyer = new Ship("Computer_Destroyer", 2);
        this.computer_submarine = new Ship("Computer_Submarine", 2);

        this.playerHits = new int[100];
        this.playerMisses = new int[100];
        this.computerHits = new int[100];
        this.computerMisses = new int[100];
    }
}
