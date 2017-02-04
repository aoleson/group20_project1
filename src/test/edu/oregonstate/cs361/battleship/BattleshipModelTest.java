package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by annie on 1/28/17.
 */
class BattleshipModelTest {
    // instantiate a fresh game object for use in this test suite
    BattleshipModel game = new BattleshipModel();

    @Test
    public void test_constructor() {
        /* Tests that the game object's ships are already populated with the
        expected data */

        //Check the user ships
        assertEquals(game.aircraftCarrier.name, "AircraftCarrier");
        assertEquals(game.aircraftCarrier.length, 5);
        assertEquals(game.battleship.name, "Battleship");
        assertEquals(game.battleship.length, 4);
        assertEquals(game.cruiser.name, "Cruiser");
        assertEquals(game.cruiser.length, 3);
        assertEquals(game.destroyer.name, "Destroyer");
        assertEquals(game.destroyer.length, 2);
        assertEquals(game.submarine.name, "Submarine");
        assertEquals(game.submarine.length, 2);

        //Check the computer ships
        assertEquals(game.computer_aircraftCarrier.name, "Computer_AircraftCarrier");
        assertEquals(game.computer_aircraftCarrier.length, 5);
        assertEquals(game.computer_battleship.name, "Computer_Battleship");
        assertEquals(game.computer_battleship.length, 4);
        assertEquals(game.computer_cruiser.name, "Computer_Cruiser");
        assertEquals(game.computer_cruiser.length, 3);
        assertEquals(game.computer_destroyer.name, "Computer_Destroyer");
        assertEquals(game.computer_destroyer.length, 2);
        assertEquals(game.computer_submarine.name, "Computer_Submarine");
        assertEquals(game.computer_submarine.length, 2);

        //Check the hit and miss arrays
        assertEquals(game.playerHits.length, 16);
        for (int i = 0; i < 16; i++) {
            assertEquals(game.playerHits[i], null);
        }
        assertEquals(game.playerMisses.length, 84);
        for (int i = 0; i < 84; i++) {
            assertEquals(game.playerMisses[i], null);
        }
        assertEquals(game.computerHits.length, 16);
        for (int i = 0; i < 16; i++) {
            assertEquals(game.computerHits[i], null);
        }
        assertEquals(game.computerMisses.length, 84);
        for (int i = 0; i < 84; i++) {
            assertEquals(game.computerMisses[i], null);
        }
    }


    @Test
    public void test_ships() {

        BattleshipModel.GridSquare testing_grid = new BattleshipModel.GridSquare();
        testing_grid.Across = 55;
        testing_grid.Down = 55;

        // Test that ships are accessible and editable through the game object
        game.aircraftCarrier.name = "test_air";
        game.aircraftCarrier.length = 10;
        game.aircraftCarrier.start.Across = testing_grid.Across;
        game.aircraftCarrier.start.Down = testing_grid.Down;
        game.aircraftCarrier.end.Across = testing_grid.Across;
        game.aircraftCarrier.end.Down = testing_grid.Down;
        assertEquals(game.aircraftCarrier.name, "test_air");
        assertEquals(game.aircraftCarrier.length, 10);
        assertEquals(game.aircraftCarrier.start.Across, 55);
        assertEquals(game.aircraftCarrier.start.Down, 55);
        assertEquals(game.aircraftCarrier.end.Across, 55);
        assertEquals(game.aircraftCarrier.end.Down, 55);

        game.battleship.name = "test_bat";
        game.battleship.length = 10;
        game.battleship.start.Across = testing_grid.Across;
        game.battleship.start.Down = testing_grid.Down;
        game.battleship.end.Across = testing_grid.Across;
        game.battleship.end.Down = testing_grid.Down;
        assertEquals(game.battleship.name, "test_bat");
        assertEquals(game.battleship.length, 10);
        assertEquals(game.battleship.start.Across, 55);
        assertEquals(game.battleship.start.Down, 55);
        assertEquals(game.battleship.end.Across, 55);
        assertEquals(game.battleship.end.Down, 55);

        game.cruiser.name = "test_cru";
        game.cruiser.length = 10;
        game.cruiser.start.Across = testing_grid.Across;
        game.cruiser.start.Down = testing_grid.Down;
        game.cruiser.end.Across = testing_grid.Across;
        game.cruiser.end.Down = testing_grid.Down;
        assertEquals(game.cruiser.name, "test_cru");
        assertEquals(game.cruiser.length, 10);
        assertEquals(game.cruiser.start.Across, 55);
        assertEquals(game.cruiser.start.Down, 55);
        assertEquals(game.cruiser.end.Across, 55);
        assertEquals(game.cruiser.end.Down, 55);

        game.destroyer.name = "test_des";
        game.destroyer.length = 10;
        game.destroyer.start.Across = testing_grid.Across;
        game.destroyer.start.Down = testing_grid.Down;
        game.destroyer.end.Across = testing_grid.Across;
        game.destroyer.end.Down = testing_grid.Down;
        assertEquals(game.destroyer.name, "test_des");
        assertEquals(game.destroyer.length, 10);
        assertEquals(game.destroyer.start.Across, 55);
        assertEquals(game.destroyer.start.Down, 55);
        assertEquals(game.destroyer.end.Across, 55);
        assertEquals(game.destroyer.end.Down, 55);

        game.submarine.name = "test_sub";
        game.submarine.length = 10;
        game.submarine.start.Across = testing_grid.Across;
        game.submarine.start.Down = testing_grid.Down;
        game.submarine.end.Across = testing_grid.Across;
        game.submarine.end.Down = testing_grid.Down;
        assertEquals(game.submarine.name, "test_sub");
        assertEquals(game.submarine.length, 10);
        assertEquals(game.submarine.start.Across, 55);
        assertEquals(game.submarine.start.Down, 55);
        assertEquals(game.submarine.end.Across, 55);
        assertEquals(game.submarine.end.Down, 55);

        game.computer_aircraftCarrier.name = "test_air";
        game.computer_aircraftCarrier.length = 10;
        game.computer_aircraftCarrier.start.Across = testing_grid.Across;
        game.computer_aircraftCarrier.start.Down = testing_grid.Down;
        game.computer_aircraftCarrier.end.Across = testing_grid.Across;
        game.computer_aircraftCarrier.end.Down = testing_grid.Down;
        assertEquals(game.computer_aircraftCarrier.name, "test_air");
        assertEquals(game.computer_aircraftCarrier.length, 10);
        assertEquals(game.computer_aircraftCarrier.start.Across, 55);
        assertEquals(game.computer_aircraftCarrier.start.Down, 55);
        assertEquals(game.computer_aircraftCarrier.end.Across, 55);
        assertEquals(game.computer_aircraftCarrier.end.Down, 55);

        game.computer_battleship.name = "test_bat";
        game.computer_battleship.length = 10;
        game.computer_battleship.start.Across = testing_grid.Across;
        game.computer_battleship.start.Down = testing_grid.Down;
        game.computer_battleship.end.Across = testing_grid.Across;
        game.computer_battleship.end.Down = testing_grid.Down;
        assertEquals(game.computer_battleship.name, "test_bat");
        assertEquals(game.computer_battleship.length, 10);
        assertEquals(game.computer_battleship.start.Across, 55);
        assertEquals(game.computer_battleship.start.Down, 55);
        assertEquals(game.computer_battleship.end.Across, 55);
        assertEquals(game.computer_battleship.end.Down, 55);

        game.computer_cruiser.name = "test_cru";
        game.computer_cruiser.length = 10;
        game.computer_cruiser.start.Across = testing_grid.Across;
        game.computer_cruiser.start.Down = testing_grid.Down;
        game.computer_cruiser.end.Across = testing_grid.Across;
        game.computer_cruiser.end.Down = testing_grid.Down;
        assertEquals(game.computer_cruiser.name, "test_cru");
        assertEquals(game.computer_cruiser.length, 10);
        assertEquals(game.computer_cruiser.start.Across, 55);
        assertEquals(game.computer_cruiser.start.Down, 55);
        assertEquals(game.computer_cruiser.end.Across, 55);
        assertEquals(game.computer_cruiser.end.Down, 55);

        game.computer_destroyer.name = "test_des";
        game.computer_destroyer.length = 10;
        game.computer_destroyer.start.Across = testing_grid.Across;
        game.computer_destroyer.start.Down = testing_grid.Down;
        game.computer_destroyer.end.Across = testing_grid.Across;
        game.computer_destroyer.end.Down = testing_grid.Down;
        assertEquals(game.computer_destroyer.name, "test_des");
        assertEquals(game.computer_destroyer.length, 10);
        assertEquals(game.computer_destroyer.start.Across, 55);
        assertEquals(game.computer_destroyer.start.Down, 55);
        assertEquals(game.computer_destroyer.end.Across, 55);
        assertEquals(game.computer_destroyer.end.Down, 55);

        game.computer_submarine.name = "test_sub";
        game.computer_submarine.length = 10;
        game.computer_submarine.start.Across = testing_grid.Across;
        game.computer_submarine.start.Down = testing_grid.Down;
        game.computer_submarine.end.Across = testing_grid.Across;
        game.computer_submarine.end.Down = testing_grid.Down;
        assertEquals(game.computer_submarine.name, "test_sub");
        assertEquals(game.computer_submarine.length, 10);
        assertEquals(game.computer_submarine.start.Across, 55);
        assertEquals(game.computer_submarine.start.Down, 55);
        assertEquals(game.computer_submarine.end.Across, 55);
        assertEquals(game.computer_submarine.end.Down, 55);

    }


    @Test
    public void test_squares() {

        // Test that squares are accessible and editable through the game object.
        BattleshipModel.GridSquare grid2 = new BattleshipModel.GridSquare();
        grid2.Across = 17;
        grid2.Down = 17;

        game.aircraftCarrier.start = grid2;
        game.aircraftCarrier.end = grid2;
        assertEquals(game.aircraftCarrier.start.Across, 17);
        assertEquals(game.aircraftCarrier.start.Down, 17);
        assertEquals(game.aircraftCarrier.end.Across, 17);
        assertEquals(game.aircraftCarrier.end.Down, 17);

        game.battleship.start = grid2;
        game.battleship.end = grid2;
        assertEquals(game.battleship.start.Across, 17);
        assertEquals(game.battleship.start.Down, 17);
        assertEquals(game.battleship.end.Across, 17);
        assertEquals(game.battleship.end.Down, 17);

        game.cruiser.start = grid2;
        game.cruiser.end = grid2;
        assertEquals(game.cruiser.start.Across, 17);
        assertEquals(game.cruiser.start.Down, 17);
        assertEquals(game.cruiser.end.Across, 17);
        assertEquals(game.cruiser.end.Down, 17);

        game.destroyer.start = grid2;
        game.destroyer.end = grid2;
        assertEquals(game.destroyer.start.Across, 17);
        assertEquals(game.destroyer.start.Down, 17);
        assertEquals(game.destroyer.end.Across, 17);
        assertEquals(game.destroyer.end.Down, 17);

        game.submarine.start = grid2;
        game.submarine.end = grid2;
        assertEquals(game.submarine.start.Across, 17);
        assertEquals(game.submarine.start.Down, 17);
        assertEquals(game.submarine.end.Across, 17);
        assertEquals(game.submarine.end.Down, 17);

        game.computer_aircraftCarrier.start = grid2;
        game.computer_aircraftCarrier.end = grid2;
        assertEquals(game.computer_aircraftCarrier.start.Across, 17);
        assertEquals(game.computer_aircraftCarrier.start.Down, 17);
        assertEquals(game.computer_aircraftCarrier.end.Across, 17);
        assertEquals(game.computer_aircraftCarrier.end.Down, 17);

        game.computer_battleship.start = grid2;
        game.computer_battleship.end = grid2;
        assertEquals(game.computer_battleship.start.Across, 17);
        assertEquals(game.computer_battleship.start.Down, 17);
        assertEquals(game.computer_battleship.end.Across, 17);
        assertEquals(game.computer_battleship.end.Down, 17);

        game.computer_cruiser.start = grid2;
        game.computer_cruiser.end = grid2;
        assertEquals(game.computer_cruiser.start.Across, 17);
        assertEquals(game.computer_cruiser.start.Down, 17);
        assertEquals(game.computer_cruiser.end.Across, 17);
        assertEquals(game.computer_cruiser.end.Down, 17);

        game.computer_destroyer.start = grid2;
        game.computer_destroyer.end = grid2;
        assertEquals(game.computer_destroyer.start.Across, 17);
        assertEquals(game.computer_destroyer.start.Down, 17);
        assertEquals(game.computer_destroyer.end.Across, 17);
        assertEquals(game.computer_destroyer.end.Down, 17);

        game.computer_submarine.start = grid2;
        game.computer_submarine.end = grid2;
        assertEquals(game.computer_submarine.start.Across, 17);
        assertEquals(game.computer_submarine.start.Down, 17);
        assertEquals(game.computer_submarine.end.Across, 17);
        assertEquals(game.computer_submarine.end.Down, 17);

    }

}

