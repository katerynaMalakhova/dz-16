package steps;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Booking;
import org.json.JSONObject;

public class BookingApiSteps {
    public static String TOKEN_VALUE;
    public static final String TOKEN = "token";

    public void generateToken() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        JSONObject body = new JSONObject();  //JSONObject is used for an example, better use Java class instead
        body.put("password", "password123");
        body.put("username", "admin");

        Response response = RestAssured.given()
                .body(body.toString())
                .post("/auth");
        response.prettyPrint();
        TOKEN_VALUE = response.then().extract().jsonPath().get(TOKEN);
    }

    public Response postBooking(Booking booking) {
        generateToken();

        JSONObject subRequestParams = new JSONObject();
        subRequestParams.put("checkin", booking.getBookingdates().getCheckin());
        subRequestParams.put("checkout", booking.getBookingdates().getCheckout());

        JSONObject requestParams = new JSONObject();
        requestParams.put("firstname", booking.getFirstname());
        requestParams.put("lastname", booking.getLastname());
        requestParams.put("totalprice", booking.getTotalprice());
        requestParams.put("depositpaid", booking.getDepositpaid());
        requestParams.put("bookingdates", subRequestParams);
        requestParams.put("additionalneeds", booking.getAdditionalneeds());

        return RestAssured.given().log().all()
                .header("Accept", ContentType.JSON)
                .cookie(TOKEN, TOKEN_VALUE)
                .when()
                .body(requestParams.toString())
                .post("/booking");
    }

    public Response getBookingIds() {
        return RestAssured.given()
                .get("https://restful-booker.herokuapp.com/booking");
    }

    public Response getBooking(Integer id) {

        return RestAssured.given()
                .get("https://restful-booker.herokuapp.com/booking/{id}", id);
    }

    public Integer getLastBooking() {
        return RestAssured.given().log().all().get("https://restful-booker.herokuapp.com/booking")
                .then().extract().jsonPath().get("bookingid[0]");
    }

    public Response updateBookingPatch(int id, String firstName, String lastName) {
        generateToken();

        JSONObject body = new JSONObject();
        // body.put("totalprice", price);
        body.put("firstname", firstName);
        body.put("lastname", lastName);

        return RestAssured.given().log().all()
                .header("Accept", ContentType.JSON)
                .cookie(TOKEN, TOKEN_VALUE)
                .body(body.toString())
                .patch("/booking/{id}", id);
    }


    public Response updateBookingPut(int id, Booking booking) {
        generateToken();

        JSONObject subRequestParams = new JSONObject();
        subRequestParams.put("checkin", booking.getBookingdates().getCheckin());
        subRequestParams.put("checkout", booking.getBookingdates().getCheckout());

        JSONObject requestParams = new JSONObject();
        requestParams.put("firstname", booking.getFirstname());
        requestParams.put("lastname", booking.getLastname());
        requestParams.put("totalprice", booking.getTotalprice());
        requestParams.put("depositpaid", booking.getDepositpaid());
        requestParams.put("bookingdates", subRequestParams);
        requestParams.put("additionalneeds", booking.getAdditionalneeds());

        return RestAssured.given().log().all()
                .header("Accept", ContentType.JSON)
                .cookie(TOKEN, TOKEN_VALUE)
                .body(requestParams.toString())
                .put("/booking/{id}", id);
    }

    public void deleteBooking(int id) {
        generateToken();
        RestAssured.given()
                .delete("/booking/{id}", id);
    }
}