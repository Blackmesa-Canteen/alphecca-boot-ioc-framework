package io.swen90007sm2.app.model.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

public class AdminChangeHotelStatusParam {
    @NotBlank(message = "hotelId should not be null")
    @Max(value = 50, message = "hotelId should not exceed {value}")
    private String hotelId;

    public AdminChangeHotelStatusParam() {
    }

    public AdminChangeHotelStatusParam(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
