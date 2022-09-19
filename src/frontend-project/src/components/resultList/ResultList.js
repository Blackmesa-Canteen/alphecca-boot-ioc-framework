import React from "react";
import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { GetBookings } from "../../API/CustomerApi";
import Booking from "../../components/bookings/Booking";
import { GetAllTransaction } from "../../API/CustomerApi";

const ResultList = (customer) => {
  const { loading, transaction, error } = GetAllTransaction(customer.item);

  if (transaction.length === 0) {
    return <div>loading</div>;
  } else {
    return (
      <div>
        {console.log(transaction)}
        <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
          <Tab eventKey="profile" title="Profile">
            My profile
          </Tab>
          <Tab eventKey="booking" title="My Booking">
            <div>
              {transaction.data.map((item) => (
                <Booking item={item} key={item.id} />
              ))}
            </div>
          </Tab>
        </Tabs>
      </div>
    );
  }
};

export default ResultList;
