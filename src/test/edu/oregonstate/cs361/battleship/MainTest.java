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
import java.io.OutputStream;

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

            TestResponse res = request("GET", "/model", null);
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
    public void testPlaceShip() {
        BattleshipModel tmodel = new BattleshipModel();
        Gson gson = new Gson();
        String json = gson.toJson(tmodel);
        TestResponse res = request("POST", "/placeShip/submarine/1/1/horizontal", json);
        assertEquals(res.status, 200);
        TestResponse res2 = request("POST", "/placeShip/destroyer/3/3/horizontal", json);
        assertEquals(res2.status, 200);
        TestResponse res3 = request("POST", "/placeShip/aircraftCarrier/2/2/horizontal", json);
        assertEquals(res3.status, 200);
        TestResponse res4 = request("POST", "/placeShip/battleship/2/4/horizontal", json);
        assertEquals(res4.status, 200);
        TestResponse res5 = request("POST", "/placeShip/cruiser/2/1/horizontal", json);
        assertEquals(res5.status, 200);

        TestResponse res6 = request("POST", "/placeShip/submarine/1/1/vertical", json);
        assertEquals(res6.status, 200);
        TestResponse res7 = request("POST", "/placeShip/destroyer/3/3/vertical", json);
        assertEquals(res7.status, 200);
        TestResponse res8 = request("POST", "/placeShip/aircraftCarrier/2/2/vertical", json);
        assertEquals(res8.status, 200);
        TestResponse res9 = request("POST", "/placeShip/battleship/2/4/vertical", json);
        assertEquals(res9.status, 200);
        TestResponse res10 = request("POST", "/placeShip/cruiser/2/1/vertical", json);
        assertEquals(res10.status, 200);
    }

    @Test
    //This test applies to user story 1 of our project code.
    public void testFireAt() {
        TestResponse res = request("POST", "/fire/1/1");
        assertEquals(200, res.status);
    }

    private TestResponse request(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            connection.connect();
            body = IOUtils.toString(connection.getInputStream());
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