package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateHotelParam {

    @NotBlank(message = "hotel name should be not null")
    @Size(max = 100, message = "hotel name should not exceed {max}")
    private String name;

    @NotBlank(message = "hotel description should be not null")
    @Size(max = 255, message = "hotel description should not exceed {max}")
    private String description;

    @NotBlank(message = "address text should be not null")
    @Size(max = 255, message = "address text should not exceed {max}")
    private String address;

    @NotBlank(message = "hotel postcode  should be not null")
    @Size(max = 50, message = "hotel name should not exceed {max}")
    private String postCode;

    @NotNull(message = "on sale status should be not null")
    private Boolean onSale;

    private List<String> amenityIds;

    public CreateHotelParam() {
    }

    public CreateHotelParam(String name, String description, String address, String postCode, Boolean onSale, List<String> amenityIds) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.postCode = postCode;
        this.onSale = onSale;
        this.amenityIds = amenityIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public List<String> getAmenityIds() {
        return amenityIds;
    }

    public void setAmenityIds(List<String> amenityIds) {
        this.amenityIds = amenityIds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
