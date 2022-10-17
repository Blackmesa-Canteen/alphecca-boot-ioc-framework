import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { Input } from "../../components/common/CustomerStyle";
import { editHotel, HotelDetailwithLock } from "../../API/HotelierApi";
import { amenities } from "./HotelElement";
import styled from "styled-components";
import { AiOutlineCloseCircle } from "react-icons/ai";

export const EditWd = styled.div`
  box-shadow: 0 5px 9px rgba(0, 0, 0, 0.5);
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 4);
  backdrop-filter: blur(5px);
  padding: 2rem 1rem;
  width: 35rem;
  z-index: 10;
  position: absolute;
  top: 15%;
  left: calc(50% - 16rem);
`;
export const EditInput = styled(Input)`
  margin-left: 5%;
`;
export const Text = styled.div`
  color: white;
  margin-left: 5%;
`;

export function EditDetail(props) {
  //edit hotel info
 
  const hotelInfo = props.value;
  const [name, setName] = useState(hotelInfo.name);
  const [description, setDescription] = useState(hotelInfo.description);
  const [address, setAdress] = useState(hotelInfo.address);
  const [postCode, setPostCode] = useState(hotelInfo.postCode);
  const [onSale, setOnsale] = useState(hotelInfo.onSale);
  const [onsaleT, setOnsaleT] = useState(hotelInfo.onSale);
  const [onsaleF, setOnsaleF] = useState(!hotelInfo.onSale);
  var amenityArray = [];
  const amentyKeys = Object.keys(amenities);
  function closeHandler() {
    props.onCancel();
  }
 
  const isOnsale = (e) => {
    if (onsaleT || !onsaleF) {
      setOnsaleT(false);
      setOnsaleF(true);
      setOnsale(false);
    }
    if (!onsaleT || onsaleF) {
      setOnsaleT(true);
      setOnsaleF(false);
      setOnsale(true);
    }
  };
  const onClick = () => {
    const newInfo = {
      hotelId: hotelInfo.hotelId,
      name: name,
      description: description,
      address: address,
      postCode: postCode,
      onSale: onSale,
      amenityIds: amenityArray.sort(),
      version:hotelInfo.version
    };
    
    editHotel(newInfo);
  };

  const addAmenity = (e) => {
    var id = e.target.id.toString();
    console.log(amenityArray);
    !amenityArray.includes(id)
      ? amenityArray.push(id)
      : amenityArray.splice(amenityArray.indexOf(id), 1);
  };
  return (
    <EditWd>
      <AiOutlineCloseCircle
        style={{ color: "white", width: 30, height: 30 }}
        onClick={closeHandler}
      />
     
      {<div>
      <center>
        <h1 style={{ color: "white" }}>Edit</h1>
      </center>
      <Text>Name:</Text>
      <EditInput
        type="text"
        value={name}
        onChange={(e) => {
          setName(e.target.value);
        }}
      />
      <Text>Address:</Text>
      <EditInput
        type="text"
        value={address}
        onChange={(e) => {
          setAdress(e.target.value);
        }}
      />
      <Text>Post Code:</Text>
      <EditInput
        type="text"
        value={postCode}
        onChange={(e) => {
          setPostCode(e.target.value);
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

      <div style={{ display: "flex", flexDirection: "row" }}>
        <Text>Onsale:</Text>
        <Form.Check
          style={{ color: "white", marginLeft: "5%", marginRight: "5%" }}
          type="checkbox"
          label="Yes"
          id="Onsale"
          checked={onsaleT}
          onChange={isOnsale}
        />
        <Form.Check
          style={{ color: "white" }}
          type="checkbox"
          label="No"
          id="Onsale"
          checked={onsaleF}
          onChange={isOnsale}
        />
      </div>
      <Text style={{ marginTop: "5%" }}>Amenities:</Text>
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            marginRight: "5%",
            marginLeft: "5%",
          }}
        >
          {amentyKeys.slice(0, 6).map((k, i) => {
            return (
              <Form.Check
                style={{ color: "white" }}
                type="checkbox"
                label={k}
                key={k}
                value={k}
                id={i + 1}
                onChange={addAmenity}
              />
            );
          })}
        </div>
        <div style={{ display: "flex", flexDirection: "column" }}>
          {amentyKeys.slice(6, 11).map((k, i) => {
            return (
              <Form.Check
                style={{ color: "white", marginLeft: "5%", width: 300 }}
                type="checkbox"
                label={k}
                key={k}
                value={k}
                id={i + 7}
                onChange={addAmenity}
              />
            );
          })}
        </div>
      </div>
      <center>
        <Button style={{ marginTop: "5%" }} onClick={onClick}>
          Submit
        </Button>
      </center>
      </div>}
    </EditWd>
  );
}
