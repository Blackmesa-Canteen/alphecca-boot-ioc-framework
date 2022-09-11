import React from "react";
import { Button } from "react-bootstrap";
import { Logout } from "../../API/CommonApi";
import { HotelierNavBar } from "../../components/NavBar";
import {
  createRoomType,
  editHotel,
  HotelDetail,
  HotelierInfo,
  registerHotel,
} from "../../API/HotelierApi";
import { DisplayHotel } from "./HotelElement";
export default function HotelierHome() {
  const { loading, hotel, error } = HotelDetail();
  const { loading1, hotelier, error1 } = HotelierInfo();
  if (loading && loading1) {
    return (
      <div><h1>Loading</h1></div>
    );
  }
  const hotel1 = {
    name: "dem",
    description: "description",
    address: "4 Lonsdale Street",
    postCode: "3000",
    onSale: true,
    amenityIds: ["1", "2"],
  };
  const room = {
    hotelId: "1568295139146559488",
    name: "single",
    description: "one bed one night have a good time",
    pricePerNight: 100,
    sleepsNum: 1,
    vacantNum: 10,
    onSale: true,
    currency: "AUD",

    amenityIds: [1, 2],
  };
  const onClick = (e) => {
    //Logout(e.target.value);
    //registerHotel(hotel1);
    editHotel(hotel)
    //createRoomType(room);
  };
  return (
    <div>
      <HotelierNavBar />
      <DisplayHotel value={hotel}/>
      
      <Button value="Hotelier" onClick={onClick} />
    </div>
  );
}
