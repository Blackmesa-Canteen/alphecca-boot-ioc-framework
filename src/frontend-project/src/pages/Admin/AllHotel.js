import React from "react";
import { AdminNavBar } from "../../components/NavBar";
import {
  ViewAllHotels,
  ViewHotelierGroup,
  changeHotelStatus,
  removeHotelier,
} from "../../API/AdminApi";
import { TransacDetail, Text } from "../Hotelier/Transaction";
import { HotelierGroup, HotelsContainer } from "./AdminElemt";
export default function AllHotels() {
  const { loading, hotels, error } = ViewAllHotels({ pageNo: 1, pageSize: 12 });
  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  } else if (error) {
    return (
      <div>
        <h1>Something went wrong</h1>
      </div>
    );
  }
  console.log(hotels);
  return (
    <div>
      <AdminNavBar />
      <h1 style={{ marginTop: "2%", marginLeft: "8%" }}>Hotels</h1>
      <TransacDetail >
              <Text>Hotel Name</Text>
              <Text>Address</Text>
              <Text>Post Code</Text>
              <Text>Description</Text>
              <Text>Description</Text>
            </TransacDetail>
      <HotelsContainer>
        {hotels.map((h) => {
          return (
            <TransacDetail key={h.id}>
              <Text>{h.name}</Text>
              <Text>{h.address}</Text>
              <Text>{h.postCode}</Text>
              <Text>{h.description}</Text>
              <HotelierGroup value={h.hotelId}/>
            </TransacDetail>
          );
        })}
      </HotelsContainer>
    </div>
  );
}
