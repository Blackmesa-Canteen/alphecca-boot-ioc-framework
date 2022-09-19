import React, { useState } from "react";

import { HotelierNavBar } from "../../components/navbar/NavBar";
import { HotelierInfo } from "../../API/HotelierApi";
import { DisplayHotel } from "./HotelElement";
import { AllRooms } from "./Room/Rooms";
import { Button } from "react-bootstrap";
import { CreateNewHotel } from "./AddHotel";
export default function HotelierHome() {
  const { loading1, hotelier, error1 } = HotelierInfo();
  const [createHotel, startCreate] = useState(false);
  if (loading1) {
    return (
      <div>
        <h1>Loading</h1>
      </div>
    );
  }
  if (error1) {
    return (
      <div>
        <h1>error</h1>
      </div>
    );
  }
  
  console.log(localStorage.getItem("Hotelier"));
  return (
    <div>
      <HotelierNavBar />
      {"hotelId" in hotelier && (
        <div>
          <DisplayHotel value={hotelier.userId}/>
          <AllRooms value={hotelier.hotelId} />
        </div>
      )}
      {!("hotelId" in hotelier) && (
        <div>
          <div>
            welcome {hotelier.userName}, please register your own Hotel
            <br />
            <Button
              onClick={() => {
                startCreate(true);
              }}
            >
              Register Hotel
            </Button>
          </div>
          {createHotel && (
            <CreateNewHotel
              onCancel={() => {
                startCreate(false);
              }}
            />
          )}
        </div>
      )}
    </div>
  );
}
