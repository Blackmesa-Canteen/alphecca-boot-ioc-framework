package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotBlank;

public class AdminChangeHotelStatusParam {
    @NotBlank(message = "Test should not be null")
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
