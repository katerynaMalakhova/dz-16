package steps;

import io.restassured.response.Response;
import models.BodyArgs;
import models.Booking;
import models.BookingDates;
import models.Result;
import org.testng.annotations.Test;

public class BookingSteps extends BaseApiSteps {

    public String createBooking(String firstname, String lastname, int price, boolean isPaid, String checkIn, String checkOut, String info) {
        Booking args = Booking.builder()
                .firstName(firstname)
                .lastName(lastname)
                .totalprice(price)
                .depositpaid(isPaid)
                .bookingdates(BookingDates.builder().checkIn(checkIn).checkOut(checkOut).build())
                .additionalneeds(info)
                .build();

        BodyArgs bodyArgs = BodyArgs.builder().
                params(args)
                .method("/booking")
                .build();

        Response response = postRequest(bodyArgs);
        response.then().statusCode(200);
        Result result = response.as(Result.class);
        return result.getResult().toString();
    }

}
