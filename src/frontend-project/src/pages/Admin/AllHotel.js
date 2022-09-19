import React, { useState } from "react";
import { AdminNavBar } from "../../components/navbar/NavBar";
import {
  ViewAllHotels,
  changeHotelStatus,
} from "../../API/AdminApi";
import { TransacDetail, Text } from "../Hotelier/Transaction";
import { HotelierGroup, HotelsContainer } from "./AdminElemt";
import styled from "styled-components";
import { Button } from "react-bootstrap";
import { EditWd } from "../Hotelier/EditHotel";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { CircularProgress} from "@mui/material";

const StatusButton = styled.div`
  height: 30px;
  width: 40%;
  border-radius: 5px;
  margin-left: 30%;
  overflow: hidden;
  text-align: center;
  align-items: center;
  text-overflow: ellipsis;
`;
const HGContainer = styled.div`
  color: white;
  margin-left: 5%;
  margin-top: 5%;
  font-size: 20px;
`;

export default function AllHotels() {
  const { loading, hotels, error } = ViewAllHotels({ pageNo: 1, pageSize: 12 });
  const [editWd, setEdit] = useState("");
  if (loading) {
    return (
      <div>
        <AdminNavBar />
        <center style={{ marginTop: "20%" }}>
          <CircularProgress />
          <h1>Loading...</h1>
        </center>
      </div>
    );
  } else if (error) {
    return (
      <div>
        <h1>Something went wrong</h1>
      </div>
    );
  }
  return (
    <div>
      <AdminNavBar />

      <HotelsContainer>
        <h1>Hotels</h1>
        <TransacDetail>
          <Text>Hotel Name</Text>
          <Text>Address</Text>
          <Text>Post Code</Text>
          <Text>Description</Text>
          <Text>Status</Text>
          <Text />
        </TransacDetail>
        {hotels.map((h) => {
          return (
            <TransacDetail key={h.id}>
              <Text>{h.name}</Text>
              <Text>{h.address}</Text>
              <Text>{h.postCode}</Text>
              <Text>{h.description}</Text>
              <Text>
                {h.onSale && (
                  <StatusButton
                    style={{
                      backgroundColor: "rgb(213,253,208)",
                      color: "green",
                    }}
                    onClick={() => {
                      changeHotelStatus(h.hotelId);
                    }}
                  >
                    Open
                  </StatusButton>
                )}
                {!h.onSale && (
                  <StatusButton
                    style={{
                      backgroundColor: "rgb(253,208,208)",
                      color: "red",
                    }}
                    onClick={() => {
                      changeHotelStatus(h.hotelId);
                    }}
                  >
                    Closed
                  </StatusButton>
                )}
              </Text>
              <Text>
                <Button
                  onClick={() => {
                    setEdit(h.hotelId);
                  }}
                >
                  Edit
                </Button>
              </Text>
            </TransacDetail>
          );
        })}
        {editWd !== "" && (
          <EditHotel
            value={editWd}
            onCancel={() => {
              setEdit("");
            }}
          />
        )}
      </HotelsContainer>
    </div>
  );
}

function EditHotel(props) {
  
  return (
    <EditWd>
      <AiOutlineCloseCircle
        style={{ color: "white", width: 30, height: 30 }}
        onClick={props.onCancel}
      />
      <center>
        <h1 style={{ color: "white" }}>Edit Hotel</h1>
      </center>
      <HGContainer>
        Hotelier Group:
        <div style={{marginLeft:"5%"}}>
        <HotelierGroup value={props.value} />
        
        </div>
      </HGContainer>
    </EditWd>
  );
}
