package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;


/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {

        int lowest_res = 5;
        int highest_res = 5;

        //Since the ships are placed randomly, we can't test if they're in a certain spot. So, we test that the randomization works.
        for(int i = 0; i < 500; i++) {      //Request a new model five hundred times.

            TestResponse res = request("GET", "/model");
            assertEquals(200, res.status);      //Check that the status is "success" each time

            //Convert the result into an object you can work with
            String model = res.body;
            Gson gson = new Gson();
            BattleshipModel testModel = gson.fromJson(model, BattleshipModel.class);

            //Figure out the lowest and highest values the down coordinate of aircraftCarrier takes over the 500 times it runs
            if (testModel.computer_aircraftCarrier.start.Down > highest_res)
                highest_res = testModel.computer_aircraftCarrier.start.Down;
            if (testModel.computer_aircraftCarrier.start.Down < lowest_res)
                lowest_res = testModel.computer_aircraftCarrier.start.Down;

        }

        //The actual check - ensure that 1 and 10 are the lowest and highest values possible
        //This tests the "randomness" and ensures we wrote the RNG correctly, which means the ships do get placed randomly
        assertEquals(1, lowest_res);
        assertEquals(10, highest_res);

    }

    @Test
    public void testGetModelFromReq(){
        TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal");
        assertEquals(200, res.status);
    }

    @Test
    public void testPlaceShip() {
        TestResponse res = request("POST", "/placeShip/destroyer/1/1/horizontal");
        assertEquals(200, res.status);
        //assertEquals("SHIP",res.body);
      //  TestResponse res2 = request("POST", "/placeShip/aircraftCarrier/1/1/vertical");
      //  assertEquals(200, res.status);
    }

    @Test
    //This test applies to user story 1 of our project code.
    public void testFireAt() {
        TestResponse res = request("POST", "/fire/1/1");
        assertEquals(200, res.status);
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}