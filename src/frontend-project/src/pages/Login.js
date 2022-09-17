import React, { useState } from "react";
import {
  FormContainer,
  SubmitButton,
  Input,
  GlobalStyle,
  MutedLink,
  IconContainer,
  RoleCard,
  RoleCardContainer,
} from "../components/CustomerStyle";
import { HomeNavBar } from "../components/NavBar";
import AlpheccaIcon from "../Picture/5Star.png";
import CustomerPng from "../Picture/Customer.png";
import HotelPng from "../Picture/Hotel.png";
import { BsArrowLeft } from "react-icons/bs";
import { Login } from "../API/CommonApi";
export default function Rolepage() {
  const [selectedRole, setRole] = useState("");

  //differenct loginform based on the role they selected
  function LoginForm(props) {
    const [userId, setUserId] = useState("");
    const [password, setPwd] = useState("");
    const onSubmit = () => {
      const user = {
        role: props.role,
        userId: userId,
        password: password,
      };
      Login(user);
    };

    return (
      <FormContainer>
        <BsArrowLeft
          onClick={() => {
            setRole("");
          }}
          style={{ position: "absolute", left: 10, top: 8, fontSize: 25 }}
        />
        <h2>Log In</h2>
        {props.role === "Customer" && (
          <img src={CustomerPng} alt="customer" width="100" height="100" />
        )}
        {props.role === "Hotelier" && (
          <img src={HotelPng} alt="customer" width="100" height="100" />
        )}
        <Input
          type="email"
          name="emailAddress"
          placeholder="Email Address"
          id="userId"
          value={userId}
          onChange={(u) => {
            setUserId(u.target.value);
          }}
        />
        <Input
          type="password"
          name="password"
          placeholder="Password"
          id="pwd"
          value={password}
          onChange={(p) => {
            setPwd(p.target.value);
          }}
        />
        <MutedLink href="/signup">Register a new account</MutedLink>
        <SubmitButton type="submit" onClick={onSubmit}>
          Log in
        </SubmitButton>
      </FormContainer>
    );
  }
  return (
    <div>
      <HomeNavBar />
      {selectedRole === "" && (
        <RoleCardContainer>
          <RoleCard
            key="hotelier"
            onClick={() => {
              setRole("Hotelier");
            }}
          >
            <img src={HotelPng} alt="hotel" width="100" height="100" />
            <h2>I'm a Hotelier</h2>
          </RoleCard>

          <RoleCard
            key="customer"
            onClick={() => {
              setRole("Customer");
            }}
          >
            <img src={CustomerPng} alt="customer" width="100" height="100" />
            <h2>I'm a Customer</h2>
          </RoleCard>
        </RoleCardContainer>
      )}
      <center>
        {selectedRole !== "" && <LoginForm role={selectedRole} />}
        <IconContainer>
          <img
            src={AlpheccaIcon}
            alt="Logo"
            width="160"
            height="145"
            margin-top="20"
          />
        </IconContainer>
      </center>
      <GlobalStyle />
    </div>
  );
}
