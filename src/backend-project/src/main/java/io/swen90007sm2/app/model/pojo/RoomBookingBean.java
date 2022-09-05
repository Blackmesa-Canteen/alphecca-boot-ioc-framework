package io.swen90007sm2.app.model.pojo;

import java.math.BigDecimal;

/**
 * @author 996Worker
 * @create 2022-09-05 15:53
 */
public class RoomBookingBean {

    // target room id
    private String roomId;
    // book how many this room?
    private String number;

    public RoomBookingBean() {
    }

    public RoomBookingBean(String roomId, String number) {
        this.roomId = roomId;
        this.number = number;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}