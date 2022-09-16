import React from "react";
import {
  ViewAllCustomer,
  registerNewHotelier,
  groupHotelier,
  ViewAllHotels,
  ViewHotelierGroup,
} from "../../API/AdminApi";

export default function AdminHome() {
  const { loading, hoteliers, error } = ViewHotelierGroup(
    "1568295139146559488"
  );
  if (!loading) {
    console.log(hoteliers);
  }
  console.log(localStorage.getItem("Admin"));
  return (
    <div>
      admin
      <button
        onClick={() => {
          groupHotelier({
            hotelOwningHotelierUserId: "firstlight@qq.com",
            hotelierToAddUserId: "infinity@qq.com",
          });
        }}
      >
        resister
      </button>
    </div>
  );
}
