import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";

import { registerHotel } from "../../API/HotelierApi";
import { amenities } from "./HotelElement";

import { AiOutlineCloseCircle } from "react-icons/ai";
import { EditWd,EditInput, Text } from "./EditHotel";
export function CreateNewHotel(props) {

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [address, setAdress] = useState("");
  const [postCode, setPostCode] = useState("");
  const [onSale, setOnsale] = useState(true);
  const [onsaleT, setOnsaleT] = useState(true);
  const [onsaleF, setOnsaleF] = useState(false);
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
      name: name,
      description: description,
      address: address,
      postCode: postCode,
      onSale: onSale,
      amenities: amenityArray.sort(),
    };
    registerHotel(newInfo);
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
    </EditWd>
  );
}
