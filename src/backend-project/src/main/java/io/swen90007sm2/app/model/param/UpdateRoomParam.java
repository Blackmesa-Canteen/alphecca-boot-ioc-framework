package io.swen90007sm2.app.model.param;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-31 16:09
 */
public class UpdateRoomParam {

    @NotNull(message = "target room id should not be null")
    private String roomId;

    @NotBlank(message = "room name should be not null")
    @Size(max = 50, message = "room name should not exceed {max}")
    private String name;
    @NotBlank(message = "room description should be not null")
    @Size(max = 255, message = "room description should not exceed {max}")
    private String description;

    @NotNull(message = "room price should be not null")
    @DecimalMin(value = "0", message = "room price should >= 0")
    @DecimalMax(value = "65535", message = "sleep num should <= 65535")
    private BigDecimal pricePerNight;
    private String currency;
    @NotNull(message = "sleep num should be not null")
    @Min(value = 0, message = "sleep num should >= 0")
    @Max(value = 50, message = "sleep num should <= 50")
    private Integer sleepsNum;
    @NotNull(message = "vacant room num should be not null")
    @Min(value = 0, message = "vacant room num should >= 0")
    @Max(value = 512, message = "vacant room num should <= 512")
    private Integer vacantNum;

    @NotNull(message = "on sale status should be not null")
    private Boolean onSale;

    @NotNull(message = "room amenity list should be not null")
    private List<String> amenityIds;

    public UpdateRoomParam() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public List<String> getAmenityIds() {
        return amenityIds;
    }

    public void setAmenityIds(List<String> amenityIds) {
        this.amenityIds = amenityIds;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }


}