package tests;

import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;
import org.testng.annotations.Test;
import steps.BookingApiSteps;

public class BookingApiTests {

    @Test
    public void createNewBookingTest() {
        BookingDates bookingDates = new BookingDates("2018-10-10", "2019-10-10");
        Booking booking = new Booking("test_rest", "Testrest@123", 123, true, bookingDates, "hehe");

        BookingApiSteps bookingApiSteps = new BookingApiSteps();
        bookingApiSteps.postBooking(booking).getBody().prettyPrint();
    }

    @Test
    public void getBookingIdsTest() {
       BookingApiSteps bookingApiSteps = new BookingApiSteps();
        bookingApiSteps.getBookingIds().prettyPrint();
    }

    @Test
    public void getBookingTest() {
        BookingApiSteps bookingApiSteps = new BookingApiSteps();
        bookingApiSteps.getBooking(16).prettyPrint();
    }

    @Test
    public void patchBookingTest() {
        BookingApiSteps bookingApiSteps = new BookingApiSteps();
        int pathId = bookingApiSteps.getLastBooking();
        Response response = bookingApiSteps.updateBookingPatch(pathId, "new-update", "new--update");
        response.prettyPrint();
    }

    @Test
    public void putBookingTest() {
        BookingApiSteps bookingApiSteps = new BookingApiSteps();
        int pathId = bookingApiSteps.getLastBooking();

        bookingApiSteps.getBooking(pathId).prettyPrint();

        Booking booking = bookingApiSteps.getBooking(pathId).as(Booking.class);
        booking.setAdditionalneeds("lalala");
        booking.setFirstname("justnewname");

        Response response = bookingApiSteps.updateBookingPut(pathId, booking);
        response.prettyPrint();
    }

    @Test
    public void deleteBookingTest() {
        BookingApiSteps bookingApiSteps = new BookingApiSteps();
        bookingApiSteps.deleteBooking(1116);
        bookingApiSteps.getBooking(1116).getBody().prettyPrint();
    }
}