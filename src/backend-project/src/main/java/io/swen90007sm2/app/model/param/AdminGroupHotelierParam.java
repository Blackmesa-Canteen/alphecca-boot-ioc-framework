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

    @NotBlank(message = "New hotelier's nickname should be not null")
    @Size(max = 50, message = "User nick name should not exceed {max}")
    private String hotelierToAddUserName;

    @NotBlank(message = "New hotelier's User password should be not null")
    @Size(max = 100, message = "User password should not exceed {max}")
    private String hotelierToAddPassword;

    public AdminGroupHotelierParam() {
    }

    public AdminGroupHotelierParam(String HotelOwningHotelierUserId, String hotelierToAddUserId, String hotelierToAddUserName, String hotelierToAddPassword) {
        this.hotelOwningHotelierUserId = HotelOwningHotelierUserId;
        this.hotelierToAddUserId = hotelierToAddUserId;
        this.hotelierToAddUserName = hotelierToAddUserName;
        this.hotelierToAddPassword = hotelierToAddPassword;
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

    public String getHotelierToAddUserName() {
        return hotelierToAddUserName;
    }

    public void setHotelierToAddUserName(String hotelierToAddUserName) {
        this.hotelierToAddUserName = hotelierToAddUserName;
    }

    public String getHotelierToAddPassword() {
        return hotelierToAddPassword;
    }

    public void setHotelierToAddPassword(String hotelierToAddPassword) {
        this.hotelierToAddPassword = hotelierToAddPassword;
    }
}
