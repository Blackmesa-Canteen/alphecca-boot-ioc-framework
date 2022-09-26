import React, { useState } from "react";
import { Button } from "react-bootstrap";
import { RoomDetail } from "../../../API/HotelierApi";
import { Title, WholeContainer } from "../HotelElement";
import styled from "styled-components";
import roomPhoto from "../../../Picture/defaultRoom.jpg"
import { EditRoomDetail } from "./EditRoom";
import { AddRoom } from "./AddRoom";
const RoomContainer = styled.div`
  box-shadow: 0 3px 3px rgba(0, 0, 0, 0.5);
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 0.05);
  backdrop-filter: blur(50px);
  padding: 2rem 1rem;
  min-width: 600px;
  height: 250px;
  margin-right: 10px;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
`;

const RoomsDiv = styled.div`
  display: flex;
  margin-left: 5%;
  flex-wrap: wrap;
  width: 100%;
  margin-bottom: 5%;
`;

export function AllRooms(props) {
  const { loading, rooms, error } = RoomDetail(props.value);
  const [edit, setEdit] = useState(false);
  const [addwd, setAddwd] = useState(false);
  if (loading) {
    return <h1>loading...</h1>;
  }
  if (error) {
    return <h1>{error}</h1>;
  } else {
    return (
      <WholeContainer>
        <div>
          <Button
            onClick={() => {
              setAddwd(true);
            }}
          >
            + Add Room Type
          </Button>
        </div>
        <RoomsDiv>
          {rooms.map((r) => {
            return (
              <div>
                <Oneroom
                  value={r}
                  key={r.id}
                  onClick={() => {
                    setEdit(true);
                  }}
                />
                {edit && (
                  <EditRoomDetail
                    value={r}
                    hotel={props.value}
                    onCancel={() => {
                      setEdit(false);
                    }}
                  />
                )}
              </div>
            );
          })}
          {addwd && (
            <AddRoom
              hotelId={props.value}
              onCancel={() => {
                setAddwd(false);
              }}
            />
          )}
        </RoomsDiv>
      </WholeContainer>
    );
  }
}

function Oneroom(props) {
  return (
    <RoomContainer>
      <img
        src={roomPhoto}
        width="50%"
        height="100%"
        style={{ marginRight: 10 }}
        alt="icon"
      />
      <div>
        <div>
          <Title>Room Name: </Title>
          {props.value.name}
        </div>
        <div>
          <Title>Description: </Title>
          {props.value.description}
        </div>
        <div>
          <Title>Price: </Title>
          <span>$ {props.value.pricePerNight}</span>
        </div>
        <div>
          <Title>Bed Number: </Title> {props.value.sleepsNum}
        </div>
        <div>
          <Title>Rooms Available: </Title> {props.value.vacantNum}
        </div>

        <Button onClick={props.onClick}>Edit</Button>
      </div>
    </RoomContainer>
  );
}
