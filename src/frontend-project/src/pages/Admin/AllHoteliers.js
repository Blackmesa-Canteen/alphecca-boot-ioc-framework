import React, { useState } from "react";
import { Button } from "react-bootstrap";
import { ViewAllhotelier, registerNewHotelier } from "../../API/AdminApi";
import { AdminNavBar } from "../../components/NavBar";
import { CContainer, OneCustomer, Text } from "./AdminElemt";
import { EditWd } from "../Hotelier/EditHotel";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { Input } from "../../components/CustomerStyle";

export default function AllHoteliers() {
  const { loading, hoteliers, error } = ViewAllhotelier({
    pageNo: 0,
    pageSize: 14,
  });
  const [newHotelier, createNew] = useState({
    userId: "",
    userName: "",
    password: "",
  });
  const { userId, userName, password } = newHotelier;
  const registerNew = (e) => {
    createNew({ ...newHotelier, [e.target.name]: e.target.value });
  };
  const onClick = () => {
    registerNewHotelier(newHotelier);
  };
  const [newWd, setNewWd] = useState(false);
  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  } else if (error) {
    return (
      <div>
        <h1>somthing went wrong, please refresh the page</h1>
      </div>
    );
  }
  return (
    <div>
      <AdminNavBar />
      <div style={{ display: "flex", flexDirection: "row", marginTop: "2%" }}>
        <h1 style={{ marginLeft: "8%" }}>Hoteliers</h1>
        <Button
          style={{ marginLeft: "2%", height: "40px" }}
          onClick={() => {
            setNewWd(true);
          }}
        >
          + Add New
        </Button>
        {newWd && (
          <EditWd>
            <AiOutlineCloseCircle
              style={{ color: "white", width: 30, height: 30 }}
              onClick={() => {
                setNewWd(false);
              }}
            />
            <center>
              <h1 style={{ color: "white" }}>Edit</h1>
            </center>

            <Text style={{ color: "white", marginLeft:"2%" }}>Name:</Text>
            <center>
            <Input
              type="text"
              name="userName"
              value={userName}
              placeholder="userName"
              onChange={registerNew}
            />
            </center>
            <Text style={{ color: "white", marginLeft:"2%" }}>Email:</Text>
           <center>
           <Input
              type="text"
              name="userId"
              value={userId}
              placeholder="Email"
              onChange={registerNew}
            />
           </center>
           <Text style={{ color: "white", marginLeft:"2%" }}>Password:</Text>
           <center>
           <Input
              type="password"
              name="password"
              value={password}
              placeholder="password"
              onChange={registerNew}
            />
           </center>

            <center>
              <Button style={{ marginTop: "5%" }} onClick={onClick}>
                Submit
              </Button>
            </center>
          </EditWd>
        )}
      </div>
      <CContainer>
        {hoteliers.map((h) => {
          return (
            <OneCustomer key={h.userId}>
              <Text>
                <span style={{ fontWeight: "bold" }}>User Name: </span>
                {h.userName}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>User ID: </span> {h.userId}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>Description: </span>{" "}
                {h.description}
              </Text>
              <Text>
                <span style={{ fontWeight: "bold" }}>Create Time: </span>{" "}
                {new Date(h.createTime).toUTCString().slice(0, 17)}
              </Text>
            </OneCustomer>
          );
        })}
      </CContainer>
    </div>
  );
}
