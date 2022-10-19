package io.swen90007sm2.app.model.param;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * @author 996Worker
 * @description
 * @create 2022-10-14 22:18
 */
public class CrateTransactionParamV2 {

    @NotNull(message = "hotelId should not be null")
    String hotelId;
    @NotNull(message = "startDate should not be null")
    Date startDate;
    @NotNull(message = "endDate should not be null")
    Date endDate;

    @NotNull(message = "roomIdNumberMap should not be null")
    // Map: [{roomId: orderedNumber}]
    Map<String, Integer> roomIdNumberMap;

    @NotNull(message = "hotelId should not be null")
    Integer version;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<String, Integer> getRoomIdNumberMap() {
        return roomIdNumberMap;
    }

    public void setRoomIdNumberMap(Map<String, Integer> roomIdNumberMap) {
        this.roomIdNumberMap = roomIdNumberMap;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}