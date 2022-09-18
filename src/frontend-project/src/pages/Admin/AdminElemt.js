import React from "react";
import styled from "styled-components";

import { ViewHotelierGroup } from "../../API/AdminApi";
import { CircularProgress } from '@mui/material'
export const OneCustomer = styled.div`
  min-width: 300px;
  height: 15opx;
  display: flex;
  flex-direction: column;
  padding: 1rem 1rem;
  background-color: aliceblue;
  backdrop-filter: blur(5px);
  justify-content: center;
  margin: 1%;
`;

export const CContainer = styled.div`
  width: 100%;
  margin-top: 2%;
  display: flex;
  align-items: flex-start;
  margin-left: 7%;
  flex-wrap: wrap;
`;
export const Text = styled.div`
  width: 100%;
  margin-top: 1%;
  padding-left: 1rem;
  align-items: center;
  font-size: 15px;
`;
export const HotelsContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  margin-top: 2%;
  margin-left: 5%;
`;
const GroupContainer = styled.div``;

export function HotelierGroup(props) {
  const { loading, hoteliers, error } = ViewHotelierGroup(props.value);
  if (loading) {
    return <CircularProgress style={{size:"small"}}/>;
  } else if (error) {
    return <h1>Something went wrong</h1>;
  }
  return (
    <GroupContainer>
      {hoteliers!=null &&hoteliers.map((h) => {
        return <div>{h.userId}</div>;
      })}
    </GroupContainer>
  );
}
