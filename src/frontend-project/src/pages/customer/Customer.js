import React from "react";
import { Placeholder } from "react-bootstrap";
import { CustomerNavBar } from "../../components/navbar/NavBar";
import { CustomerInfo } from "../../API/CustomerApi";
import CustomerPortal from "../../components/customerPortal/CustomerPortal";

const Customer = () => {
  const { loading, customer, error } = CustomerInfo();

  if (customer.length === 0) {
    return (
      <div>
        <Placeholder as="p" animation="glow">
          <Placeholder xs={12} style={{ width: "50%" }} />
          <br />
          <Placeholder xs={12} style={{ width: "20%" }} />
          <br />
          <Placeholder xs={12} style={{ width: "5%" }} />
        </Placeholder>
      </div>
    );
  } else {
    return (
      <div>
        <CustomerNavBar />
        <CustomerPortal item={customer.userId} />
      </div>
    );
  }
};

export default Customer;
