import React, { useState } from "react";
import { AdminNavBar } from "../../components/navbar/NavBar";
import {
  ViewAllHotels,
  changeHotelStatus,
  removeHotelier,
} from "../../API/AdminApi";
import { TransacDetail, Text } from "../Hotelier/Transaction";
import { HotelierGroup, HotelsContainer } from "./AdminElemt";
import styled from "styled-components";
import { Button } from "react-bootstrap";
import { EditWd } from "../Hotelier/EditHotel";
import { AiOutlineCloseCircle } from "react-icons/ai";


export default function AllHotels() {
  const { loading, hotels, error } = ViewAllHotels({ pageNo: 1, pageSize: 12 });
  const [editWd, setEdit] = useState(false);
  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  } else if (error) {
    return (
      <div>
        <h1>Something went wrong</h1>
      </div>
    );
  }
  console.log(hotels);
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
                <Button
                  onClick={() => {
                    setEdit(true);
                  }}
                >
                  Edit
                </Button>
              </Text>
              {editWd && <EditHotel value={h.id} onCancel={()=>{setEdit(false)}}/>}
            </TransacDetail>
          );
        })}
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
    </EditWd>
  );
}
