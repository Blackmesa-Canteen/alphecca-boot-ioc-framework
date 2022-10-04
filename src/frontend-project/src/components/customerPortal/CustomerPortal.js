import React from "react";
import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { Spinner } from "react-bootstrap";
import { GetAllTransaction } from "../../API/CustomerApi";
import Profile from "./profile/Profile";
import FutureBooking from "./future/FutureBooking";
import PriorBooking from "./prior/PriorBooking";

const CustomerPortal = (props) => {
  const { loading, transaction, error } = GetAllTransaction(props.item);

  if (transaction.length === 0) {
    return <Spinner animation="border" />;
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
            <Profile />
          </Tab>
          <Tab eventKey="prior" title="Prior Booking">
            <PriorBooking item={priorBooking} />
          </Tab>
          <Tab eventKey="future" title="Future Booking">
            <FutureBooking item={futureBooking} />
          </Tab>
        </Tabs>
      </div>
    );
  }
};

export default CustomerPortal;
