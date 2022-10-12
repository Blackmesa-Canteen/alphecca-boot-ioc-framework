import React, { useState } from "react";
import styled from "styled-components";
import { HiUserAdd } from "react-icons/hi";
import { Button } from "react-bootstrap";
import {
  removeHotelier,
  ViewHotelierGroup,
  groupHotelier,
  updateCustomer,
  updateHotelier,
} from "../../API/AdminApi";
import { CircularProgress } from "@mui/material";
import { Input } from "../../components/common/CustomerStyle";
import { IoMdRemoveCircle } from "react-icons/io";
import { AiFillCheckCircle } from "react-icons/ai";
import { EditWd } from "../Hotelier/EditHotel";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { HGContainer } from "./AllHotel";

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
const AddInput = styled(Input)`
  width: 300px;
  margin-right: 2%;
`;
export function HotelierGroup(props) {
  const { loading, hoteliers, error } = ViewHotelierGroup(props.value);
  const [addField, openAdd] = useState(false);
  const [newHotelier, setNew] = useState("");

  if (loading) {
    return <CircularProgress style={{ size: "small" }} />;
  } else if (error) {
    return <h1>Something went wrong</h1>;
  }
  const info = {
    hotelOwningHotelierUserId: hoteliers[0].userId,
    hotelierToAddUserId: newHotelier,
  };
  console.log(hoteliers);
  return (
    <GroupContainer>
      {hoteliers != null &&
        hoteliers.map((h) => {
          return (
            <div style={{ color: "white", fontSize: "20px" }}>
              {h.userId}
              <IoMdRemoveCircle
                style={{ color: "red", marginLeft: "10px" }}
                onClick={() => {
                  removeHotelier(h.userId);
                }}
              />
            </div>
          );
        })}
      {addField && (
        <div>
          <AddInput
            type="text"
            placeholder="Hotelier Email"
            value={newHotelier}
            onChange={(e) => {
              setNew(e.target.value);
            }}
          />
          <AiFillCheckCircle
            style={{ color: "green", fontSize: "30px" }}
            onClick={() => {
              groupHotelier(info);
            }}
          />
        </div>
      )}
      <Button
        style={{
          fontSize: "20px",
          display: "flex",
          flexDirection: "row",
          marginTop: "3%",
        }}
        onClick={() => {
          openAdd(true);
        }}
      >
        <HiUserAdd style={{ color: "white", margin: "5%", fontSize: "30px" }} />
        <div style={{ marginLeft: "2%", marginTop: "5%" }}>Add</div>
      </Button>
    </GroupContainer>
  );
}
export function EditInfoForm(props) {
  const [name, setName] = useState(props.value.name);
  const [description, setDescription] = useState(props.value.description);
  const submitEdit = () => {
    const newInfo = {
      userName: name,
      description: description,
      avatarUrl: "qqq",
      userId: props.value.email,
    };
    if (props.value.role === "Hotelier") {
      updateHotelier(newInfo);
      
      return;
    }
    updateCustomer(newInfo);
    
  };
  return (
    <EditWd>
      <AiOutlineCloseCircle
        style={{ color: "white", width: 30, height: 30 }}
        onClick={props.onCancel}
      />
      <center>
        <h1 style={{ color: "white" }}>Edit {props.role}</h1>
      </center>
      <HGContainer>
        Name:
        <div style={{ marginLeft: "5%" }}>
          <AddInput
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
        </div>
      </HGContainer>
      <HGContainer>
        Description:
        <div style={{ marginLeft: "5%" }}>
          <AddInput
            type="text"
            placeholder="Description"
            value={description}
            onChange={(e) => {
              setDescription(e.target.value);
            }}
          />
        </div>
      </HGContainer>
      <center>
        <Button
          style={{
            fontSize: "20px",
            display: "flex",
            flexDirection: "row",
            marginTop: "3%",
          }}
          onClick={() => {
            submitEdit();
          }}
        >
          Submit
        </Button>
      </center>
    </EditWd>
  );
}
