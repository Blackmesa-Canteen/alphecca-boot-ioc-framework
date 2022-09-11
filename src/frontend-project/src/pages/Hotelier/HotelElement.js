import React from "react";
import styled from "styled-components";
import uploadPhoto from "../../Picture/uploadPhoto.png";
import Pool from "../../Picture/amenities/pool.png";
import airCondition from "../../Picture/amenities/aircondition.png";
import gym from "../../Picture/amenities/gym.png";
import restaurant from "../../Picture/amenities/restaurant.png";
import roomService from "../../Picture/amenities/roomservice.png";
import wifi from "../../Picture/amenities/freeWifi.png";
import bar from "../../Picture/amenities/bar.png";
import laundry from "../../Picture/amenities/laundry.png";
import outdoor from "../../Picture/amenities/outdoor.png";
import breakfast from "../../Picture/amenities/breakfast.png";
import parking from "../../Picture/amenities/parking.jpeg";
import { Button } from "react-bootstrap";
const HotelContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  height: 100%;
  align-items: center;
  margin-left: 5%;
`;
const HotelName = styled.h1`
  margin-top: 3%;
  margin-right: 60%;
  width: 40%;
`;
const DevideLine = styled.hr`
  font-weight: 200px;
  width: 100%;
`;
const Content = styled.div`
  width: 100%;
  height: 100%;
  background-color: white;
  height: 100px;
  display: flex;
  flex-direction: row;
  font-size: 20px;
`;
const Detail = styled.div`
  display: flex;
  flex-direction: column;
  width: 50%;
  align-items: flex-start;
`;
const amenities = {
  "Swimming Pool": Pool,
  "Air Conditioning": airCondition,
  Gym: gym,
  Restaurant: restaurant,
  "Room Service": roomService,
  "Free WiFi": wifi,
  Bar: bar,
  Laundry: laundry,
  "Outdoor Space": outdoor,
  Breakfast: breakfast,
  Parking: parking,
};

export function DisplayHotel(props) {
  //show hotel info
  if (props.value.length === 0) {
    return;
  }
  function Amenity(props) {
    console.log(amenities[props.amenityIds[0].description]);
    return props.amenityIds.map((id, i) => {
      var a = amenities[id.description];
      return (
        <Content>
          <div style={{ marginRight: 10 }}>
            <img src={a} width="20" height="20" />
          </div>
          {id.description}
        </Content>
      );
    });
  }
  //edit hotel info
  const hotelInfo = {
    name: "",
    description: "",
    address: "",
    postCode: "",
    onSale: true,
    amenityIds: [],
  };

  return (
    <HotelContainer>
      <HotelName>{props.value.name}</HotelName>
      <DevideLine />
      <Content>
        <img src={uploadPhoto} width="50%" height="500" />
        <Detail>
          <p>
            Address: {props.value.address}, {props.value.postCode}
          </p>
          <p>Description: {props.value.description}</p>
          <DevideLine />

          {props.value.amenities.length !== 0 && (
            <Amenity amenityIds={props.value.amenities} />
          )}

          <Content style={{ marginTop: "3%" }}>
            <Button >Edit</Button>
          </Content>
        </Detail>
      </Content>
    </HotelContainer>
  );
}
