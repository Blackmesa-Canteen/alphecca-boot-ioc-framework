import React from "react";
import Booking from "../../bookings/Booking";

const PriorBooking = (priorBooking) => {
  return (
    <div>
      {priorBooking.item.map((booking) => (
        <Booking item={booking} key={booking.id} />
      ))}
    </div>
  );
};

export default PriorBooking;
