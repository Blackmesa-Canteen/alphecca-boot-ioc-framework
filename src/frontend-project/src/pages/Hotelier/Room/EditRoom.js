import React, { useState } from "react";
import { Button } from "react-bootstrap";

import { editRoom, GetOneRoom } from "../../../API/HotelierApi";
import { EditInput, Text } from "../EditHotel";
import styled from "styled-components";
import { AiOutlineCloseCircle } from "react-icons/ai";

const EditWd = styled.div`
  box-shadow: 0 5px 9px rgba(0, 0, 0, 0.5);
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 4);
  backdrop-filter: blur(5px);
  padding: 2rem 1rem;
  width: 35rem;

  position: absolute;
  top: 60%;
  left: calc(50% - 16rem);
`;

export function EditRoomDetail(props) {
  //edit hotel info
  const roomInfo = props.value;
  const {loading, room, error} = GetOneRoom(roomInfo.roomId);
  const [name, setName] = useState(roomInfo.name);
  const [description, setDescription] = useState(roomInfo.description);
  const [price, setPrice] = useState(roomInfo.pricePerNight);
  const [bednumber, setbedNumber] = useState(roomInfo.sleepsNum);
  const [vacantRoom, setRooms] = useState(roomInfo.vacantNum);

  function closeHandler() {
    props.onCancel();
  }
  
  const onClick = () => {
    const newInfo = {
      hotelId: roomInfo.hotelId,
      roomId: roomInfo.roomId,
      name: name,
      description: description,
      pricePerNight: price,
      sleepsNum: bednumber,
      vacantNum: vacantRoom,
      onSale: roomInfo.onSale,
      currency: roomInfo.currency,
      amenityIds: [1, 2],
    };
    editRoom(newInfo);
  };

  return (
    <EditWd>
      <AiOutlineCloseCircle
        style={{ color: "white", width: 30, height: 30 }}
        onClick={closeHandler}
      />
      <center>
        <h1 style={{ color: "white" }}>Edit</h1>
      </center>
      {loading&&<h1 style={{ color: "white" }}>Loading...</h1>}
      {error&&<h1 style={{ color: "white" }}>{error.message}</h1>}
      {!loading&&!error&&<div><Text>Name:</Text>
      <EditInput
        type="text"
        value={name}
        onChange={(e) => {
          setName(e.target.value);
        }}
      />
      <Text>Price: </Text>
      <EditInput
        type="text"
        value={price}
        onChange={(e) => {
          setPrice(e.target.value);
        }}
      />
      <Text>Number of Bed: </Text>
      <EditInput
        type="text"
        value={bednumber}
        onChange={(e) => {
          setbedNumber(e.target.value);
        }}
      />
      <Text>Rooms Available </Text>
      <EditInput
        type="text"
        value={vacantRoom}
        onChange={(e) => {
          setRooms(e.target.value);
        }}
      />
      <Text>Description:</Text>
      <div>
        <textarea
          style={{
            width: "90%",
            borderTop: "2px solid rgba(200, 200, 200, 1)",
            textAlignVertical: "top",
            marginLeft: "5%",
          }}
          type="text"
          value={description}
          onChange={(e) => {
            setDescription(e.target.value);
          }}
        />
      </div>
      <center>
        <Button style={{ marginTop: "5%" }} onClick={onClick}>
          Submit
        </Button>
      </center></div>}
    </EditWd>
  );
}
