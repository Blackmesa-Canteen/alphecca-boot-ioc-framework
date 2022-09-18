import React, { useContext } from "react";
import { CustomerNavBar } from "../../components/navbar/NavBar";
import { CustomerInfo } from "../../API/CustomerApi";
import ResultList from "../../components/resultList/ResultList";

const Customer = () => {
  const { customer, loading, error } = CustomerInfo();
  if (loading || customer === []) {
    return (
      <div>
        <h1>Loading</h1>
      </div>
    );
  }
  if (error) {
    return (
      <div>
        <h1>error</h1>
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
