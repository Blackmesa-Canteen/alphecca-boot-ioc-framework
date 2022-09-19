import React from "react";
import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { GetBookings } from "../../API/CustomerApi";
import Booking from "../../components/bookings/Booking";
import { GetAllTransaction } from "../../API/CustomerApi";

const ResultList = (props) => {
  const { loading, transaction, error } = GetAllTransaction(props.item);

  if (transaction.length === 0) {
    return <div>loading</div>;
  } else {
    const priorBooking = [];
    const futureBooking = [];
    for (const item of transaction.data) {
      if (item.statusCode === 1) {
        futureBooking.push(item);
      } else {
        priorBooking.push(item);
      }
    }
    return (
      <div>
        <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
        
          <Tab eventKey="profile" title="Profile">
            My profile
          </Tab>
          <Tab eventKey="prior" title="Prior Booking">
            <div>
              {priorBooking.map((item) => (
                <Booking item={item} key={item.id} />
              ))}
            </div>
          </Tab>
          <Tab eventKey="future" title="Future Booking">
            <div>
              {futureBooking.map((item) => (
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
