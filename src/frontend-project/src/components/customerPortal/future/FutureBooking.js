import React from "react";
import Booking from "../../bookings/Booking";

const FutureBooking = (futureBooking) => {
  return (
    <div>
      {futureBooking.item.map((item) => (
        <Booking item={item} key={item.id} />
      ))}
    </div>
  );
};

export default FutureBooking;
