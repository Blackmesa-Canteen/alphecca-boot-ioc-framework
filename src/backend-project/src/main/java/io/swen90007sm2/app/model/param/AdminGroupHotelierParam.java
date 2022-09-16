package io.swen90007sm2.app.model.param;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AdminGroupHotelierParam {

    @NotBlank(message = "User Id of the existing hotelier should not be null")
    @Size(max = 255, message = "User Id of the existing hotelier should not exceed {max}")
    @Email(message = "User Id of the existing hotelier should be in Email format")
    private String hotelOwningHotelierUserId;

    @NotBlank(message = "User Id of the hotelier to add should not be null")
    @Size(max = 255, message = "User Id of the hotelier to add should not exceed {max}")
    @Email(message = "User Id of the hotelier to add should be in Email format")
    private String hotelierToAddUserId;



    public AdminGroupHotelierParam() {
    }

    public AdminGroupHotelierParam(String HotelOwningHotelierUserId, String hotelierToAddUserId, String hotelierToAddUserName, String hotelierToAddPassword) {
        this.hotelOwningHotelierUserId = HotelOwningHotelierUserId;
        this.hotelierToAddUserId = hotelierToAddUserId;
    }

    public String getHotelOwningHotelierUserId() {
        return hotelOwningHotelierUserId;
    }

    public void setHotelOwningHotelierUserId(String hotelOwningHotelierUserId) {
        this.hotelOwningHotelierUserId = hotelOwningHotelierUserId;
    }

    public String getHotelierToAddUserId() {
        return hotelierToAddUserId;
    }

    public void setHotelierToAddUserId(String hotelierToAddUserId) {
        this.hotelierToAddUserId = hotelierToAddUserId;
    }


}
