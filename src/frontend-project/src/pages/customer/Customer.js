import React from "react";
import { CustomerNavBar } from "../../components/navbar/NavBar";
import { CustomerInfo } from "../../API/CustomerApi";
import ResultList from "../../components/resultList/ResultList";

const Customer = () => {
  const { loading, customer, error } = CustomerInfo();

  if (customer.length === 0) {
    return <div>loading</div>;
  } else {
    return (
      <div>
        <CustomerNavBar />
        <ResultList item={customer.userId}/>
      </div>
    );
  }
};

export default Customer;
