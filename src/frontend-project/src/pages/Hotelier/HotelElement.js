import React, { useState } from "react";
import styled from "styled-components";
import hotelPhoto from "../../Picture/defaultHotel.jpg"
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
import { EditDetail } from "./EditHotel";
import {  HotelDetail } from "../../API/HotelierApi";

export const WholeContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  height: 100%;

  margin-left: 5%;
  z-index: -110px;
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
  min-height: 450px;
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
  margin-left: 5%;
`;


export const amenities = {
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
export const Title = styled.span`
  font-weight: bold;
`;

export function DisplayHotel(props) {
  const { loading, hotel, error } = HotelDetail();
  const [editwd, setEditOpen] = useState(false);
  
  if(loading){
    return<h1>loading....</h1>
  }
  if(error){
    return<h1>{error}</h1>
  }
  //show hotel info
  if (hotel.length === 0) {
    return;
  }
  
  const onClick = () => {
    setEditOpen(true);
  };
  function Amenity(props) {
    return (
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div style={{ marginRight: "5%" }}>
          {props.amenityIds.slice(0, 6).map((id, i) => {
            var a = amenities[id.description];
            return (
              <div
                key={id.description}
                style={{ display: "flex", flexDirection: "row", width: 200 }}
              >
                <div style={{ marginRight: 10 }}>
                  <img src={a} width="20" height="20" alt="icon"/>
                </div>
                {id.description}
              </div>
            );
          })}
        </div>
        <div>
          {props.amenityIds.slice(6, 11).map((id, i) => {
            var a = amenities[id.description];
            return (
              <div
                key={id.description}
                style={{ display: "flex", flexDirection: "row" }}
              >
                <div style={{ marginRight: 10 }}>
                  <img src={a} width="20" height="20" alt="icon"/>
                </div>
                {id.description}
              </div>
            );
          })}
        </div>
      </div>
    );
  }
  return (
    <div>
      <WholeContainer>
        <HotelName>{hotel.name}</HotelName>
        <DevideLine />
        <Content>
          <img src={hotelPhoto} width="50%" height="400" alt="icon"/>
          <Detail>
            <p>
              <Title>Address: </Title> {hotel.address},{" "}
              {hotel.postCode}
            </p>
            <p>
              <Title> Description: </Title>
              {hotel.description}
            </p>
            <DevideLine />
            <Title>Amenities:</Title>
            {hotel.amenities.length !== 0 && (
              <Amenity amenityIds={hotel.amenities} />
            )}
            <div style={{ marginTop: "3%",display:"flex",flexDirection:"row" }}>
              <Button onClick={onClick} >Edit</Button>
              <Button style={{marginLeft:"5%", backgroundColor:"green"}}onClick={()=>{window.location="/hotelTransaction/"+props.value}}>Transactions</Button>
            </div>
          </Detail>
        </Content>
        <DevideLine />
      </WholeContainer>
      {editwd && (
        <EditDetail value={hotel} onCancel={() => setEditOpen(false)} />
       
      )}
      
    </div>
  );
}

