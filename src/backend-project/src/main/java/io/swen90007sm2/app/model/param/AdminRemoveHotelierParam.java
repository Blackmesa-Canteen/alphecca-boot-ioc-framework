package io.swen90007sm2.app.model.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminRemoveHotelierParam {
    @NotNull(message = "hotelier business id should be not null")
    @NotBlank(message = "hotelier business id should be not null")
    @Max(value = 255, message = "hotelier business id should not exceed {value}")
    private String hotelierId;

    public AdminRemoveHotelierParam() {
    }

    public AdminRemoveHotelierParam(String hotelierId) {
        this.hotelierId = hotelierId;
    }

    public String getHotelierId() {
        return hotelierId;
    }

    public void setHotelierId(String hotelierId) {
        this.hotelierId = hotelierId;
    }


}
