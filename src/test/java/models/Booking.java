package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Booking {
        private String firstname;
        private String lastname;
        private int totalprice;
        private Boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;
    }

