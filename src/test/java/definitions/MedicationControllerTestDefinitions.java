package definitions;

import com.example.pharmacyapi.PharmacyApiApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@CucumberContextConfiguration
// This configures the web environment to use a random port in order to avoid port number conflicts in the test environment and indicates that our main method is in the PharmacyApiApplication class
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PharmacyApiApplication.class)
public class MedicationControllerTestDefinitions {

    private static final String BASE_URL = "http://localhost:";
    private static final Logger log = Logger.getLogger(MedicationControllerTestDefinitions.class.getName());

    // This injects the random port used by the test into the class at runtime
    @LocalServerPort
    String port;

    private static Response response;


    /**
     * This test checks if the HTTP status code from the GET all method is OK, otherwise, it catches the exception
     */
    @Given("A list of medications are available")
    public void aListOfMedicationsAreAvailable() {
        log.info("Calling aListOfMedicationsAreAvailable");

        try {
            // This uses the exchange method to get data by executing the request of the HTTP GET method and returning a ResponseEntity instance, which we can get the response status, body and headers from. To query data for the given properties, we can pass them as URI variables.
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/medications/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> medications = JsonPath.from(String.valueOf(response.getBody())).get("data");

            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            // Assert.assertTrue(medications.size() > 0);

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }


    /**
     * These tests check that when you call the POST method, the HTTP status code is 201 (created)
     */
    @When("I add a medication to my list")
    public void iAddAMedicationToMyList() throws JSONException {
        log.info("Calling iAddAMedicationToMyList");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Name");
        requestBody.put("description", "Description");
        requestBody.put("dosage", "Dosage");
        requestBody.put("isCurrent", "IsCurrent");
        request.header("Content-Type", "application/json", "Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJlc2gyQGdhLmNvbSIsImlhdCI6MTY5NTA1MjE5NSwiZXhwIjoxNjk1MTM4NTk1fQ.w9-8v-1uEd9IOhU-kX98vqKiqClghSIFPU1T0X7KQgo");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/medications/");
    }

    @Then("The medication is added")
    public void theMedicationIsAdded() {
        log.info("Calling theMedicationIsAdded");
        Assert.assertEquals(201, response.getStatusCode());
    }


    /**
     * These tests check that when you call the GET method for a specific medication, the HTTP status code is 200 (ok)
     */
    @When("I get a specific medication")
    public void iGetASpecificMedication() {
        log.info("Calling iGetASpecificMedication");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get(BASE_URL + port + "/api/medications/1/");
    }

    @Then("The specific medication is available")
    public void theSpecificMedicationIsAvailable() {
        log.info("Calling theSpecificMedicationIsAvailable");

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(200, response.getStatusCode());
    }


    /**
     * These tests check that when you call the PUT method for a specific medication, the HTTP status code is 200 (ok)
     */
    @When("I edit a medication from my list")
    public void iEditAMedicationFromMyList() throws JSONException {
        log.info("Calling iEditAMedicationFromMyList");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Name");
        requestBody.put("description", "Description");
        requestBody.put("dosage", "Dosage");
        requestBody.put("isCurrent", "IsCurrent");
        request.header("Content-Type", "application/json", "Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJlc2gyQGdhLmNvbSIsImlhdCI6MTY5NTA1MjE5NSwiZXhwIjoxNjk1MTM4NTk1fQ.w9-8v-1uEd9IOhU-kX98vqKiqClghSIFPU1T0X7KQgo");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/medications/1/");
    }

    @Then("The medication content is edited")
    public void theMedicationContentIsEdited() {
        log.info("Calling theMedicationContentIsEdited");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("medication with id 1 has been successfully updated", message);
    }


    /**
     * These tests check that when you call the DELETE method for a specific medication, the HTTP status code is 200 (ok)
     */
    @When("I remove medication from my list")
    public void iRemoveMedicationFromMyList() {
        log.info("Calling iRemoveMedicationFromMyList");

        RequestSpecification request = RestAssured.given();
        // Pass Authorization and token into the header here if doing authorization for project
        request.header("Content-Type", "application/json", "Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJlc2gyQGdhLmNvbSIsImlhdCI6MTY5NTA1MjE5NSwiZXhwIjoxNjk1MTM4NTk1fQ.w9-8v-1uEd9IOhU-kX98vqKiqClghSIFPU1T0X7KQgo");
        response = request.delete(BASE_URL + port + "/api/medications/1/");
    }

    @Then("The medication is removed")
    public void theMedicationIsRemoved() {
        log.info("Calling theMedicationIsRemoved");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("medication with id 1 has been successfully deleted", message);
    }

}