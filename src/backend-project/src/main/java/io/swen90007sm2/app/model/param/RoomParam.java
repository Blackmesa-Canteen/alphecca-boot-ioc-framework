package io.swen90007sm2.app.model.param;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 996Worker
 */
public class RoomParam {

    @NotBlank(message = "room name should be not null")
    @Size(max = 50, message = "room name should not exceed {max}")
    private String name;
    @NotBlank(message = "room description should be not null")
    @Size(max = 255, message = "room description should not exceed {max}")
    private String description;

    @NotBlank(message = "room price should be not null")
    @Min(value = 0, message = "room price should >= 0")
    @Max(value = Integer.MAX_VALUE, message = "sleep num should <= 65535")
    private BigDecimal pricePerNight;
    private String currency;
    @NotBlank(message = "sleep num should be not null")
    @Min(value = 0, message = "sleep num should >= 0")
    @Max(value = 50, message = "sleep num should <= 50")
    private Integer sleepsNum;
    @NotBlank(message = "vacant room num should be not null")
    @Min(value = 0, message = "vacant room num should >= 0")
    @Max(value = 512, message = "vacant room num should <= 512")
    private Integer vacantNum;

    @NotNull(message = "on sale status should be not null")
    private Boolean onSale;

    public RoomParam() {
    }

    public RoomParam(String name, String description, BigDecimal pricePerNight, String currency, Integer sleepsNum, Integer vacantNum, Boolean onSale) {
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.currency = currency;
        this.sleepsNum = sleepsNum;
        this.vacantNum = vacantNum;
        this.onSale = onSale;
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

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getSleepsNum() {
        return sleepsNum;
    }

    public void setSleepsNum(Integer sleepsNum) {
        this.sleepsNum = sleepsNum;
    }

    public Integer getVacantNum() {
        return vacantNum;
    }

    public void setVacantNum(Integer vacantNum) {
        this.vacantNum = vacantNum;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }
}