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
    GridSquare playerHits[];
    GridSquare playerMisses[];
    GridSquare computerHits[];
    GridSquare computerMisses[];

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

        this.playerHits = new GridSquare[16];
        this.playerMisses = new GridSquare[84];
        this.computerHits = new GridSquare[16];
        this.computerMisses = new GridSquare[84];
    }

    public static int get_num_hits_misses(GridSquare array[]) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    }
}
