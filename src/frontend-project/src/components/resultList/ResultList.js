import React from "react";
import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { GetBookings } from "../../API/CustomerApi";
import Booking from "../../components/bookings/Booking";

const ResultList = (customer) => {
  const { booking, loading, error } = GetBookings(customer.item);

  if (booking.msg === "User Not Exist.") {
    return <div>loading</div>;
  } else {
    return (
      <div>
        {console.log(booking)}
        <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
          <Tab eventKey="profile" title="Profile">
            My profile
          </Tab>
          <Tab eventKey="booking" title="My Booking">
            {/* <div>
              {booking.data.map((item) => (
                <Booking item={item} key={item.id} />
              ))}
            </div> */}
          </Tab>
        </Tabs>
      </div>
    );
  }
};

export default ResultList;
