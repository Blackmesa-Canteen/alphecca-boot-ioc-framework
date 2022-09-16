package io.swen90007sm2.app.model.pojo;

/**
 * @author 996Worker
 * @create 2022-09-05 15:53
 */
public class RoomBookingBean {

    // target room id
    private String roomId;
    // book how many this room?
    private Integer number;

    public RoomBookingBean() {
    }

    public RoomBookingBean(String roomId, Integer number) {
        this.roomId = roomId;
        this.number = number;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}