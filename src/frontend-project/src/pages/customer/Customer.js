import React from "react";
import { CustomerNavBar } from "../../components/navbar/NavBar";
import { CustomerInfo } from "../../API/CustomerApi";
import ResultList from "../../components/resultList/ResultList";

const Customer = (id) => {
  const { loading, customer, error } = CustomerInfo();

  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  } else if (error) {
    return (
      <div>
        <h1>{error}</h1>
      </div>
    );
  }

  return (
    <div>
      <CustomerNavBar />
      <ResultList item={customer} />
    </div>
  );
};

export default Customer;
