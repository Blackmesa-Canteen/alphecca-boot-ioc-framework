import React from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { UseAllTransaction } from "../../API/HotelierApi";
import { HotelierNavBar } from "../../components/navbar/NavBar";
import { RiDeleteBin5Fill } from "react-icons/ri";

const TContainer = styled.div`
  width: 90%;
  margin: 5%;
  margin-top: 2%;
`;
export const TransacDetail = styled.div`
  width: 100%;
  padding: 1rem 1rem;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  border-bottom: 2px solid rgba(200, 200, 200, 1);
`;
export const Text = styled.div`
  width: 20%;
  overflow: hidden;
  text-align: center;
  align-items: center;
  text-overflow: ellipsis;
`;
export default function HotelTransaction() {
  const { id } = useParams();
  const { loading, transaction, error } = UseAllTransaction({
    hotelierId: id,
    currencyName: "AUD",
  });
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
      <HotelierNavBar />
      <TContainer>
        <h1>Transactions</h1>
        <TransacDetail>
          <Text>Transaction ID</Text>
          <Text>Customer ID</Text>
          <Text>Start Date</Text>
          <Text>End Date</Text>
          <Text>Ordered Rooms</Text>
          
          <Text>Total Price</Text>
          <Text style={{ marginRight: 40 }}>Status</Text>
        </TransacDetail>
        {transaction.length !== 0 &&
          transaction.map((trans) => {
            return <OneTransc value={trans} key={trans.id} />;
          })}
      </TContainer>
    </div>
  );
}
function OneTransc(props) {
  const transc = props.value;
  console.log(transc.statusCode);
  const startDate = new Date(transc.startDate).toUTCString().slice(0, 17);
  const endDate = new Date(transc.endDate).toUTCString().slice(0, 17);
  return (
    <TransacDetail>
      <Text>{transc.transactionId}</Text>
      <Text>{transc.customerId}</Text>
      <Text>{startDate}</Text>
      <Text>{endDate}</Text>
      <Text>{transc.roomOrders[0].orderedCount}</Text>
      <Text>
        ${transc.roomOrders[0].orderedCount * transc.roomOrders[0].pricePerRoom}
      </Text>
      {transc.statusCode === 1 ? (
        <Text style={{ color: "green" }}>Process</Text>
      ) : (
        <Text style={{ color: "red" }}>Cancel</Text>
      )}
      <RiDeleteBin5Fill style={{ marginRight: 10 }} size="30" />
    </TransacDetail>
  );
}
