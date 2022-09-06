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
} from "../components/UserStyle";
import NavBarcomp from "../components/NavBar";
import AlpheccaIcon from "../Picture/5Star.png";
import CustomerPng from "../Picture/Customer.png";
import HotelPng from "../Picture/Hotel.png";
import {BsArrowLeft} from "react-icons/bs"

export default function Loginpage() {
  
  const [selectedRole, setRole] = useState("");

  //differenct loginform based on the role they selected
  function LoginForm(props) {
    return (
      <FormContainer>
        <BsArrowLeft onClick={()=>{setRole("")}} />
        <h2>Log In</h2>
        {props.role === "Traveller" && (
          <img src={CustomerPng} alt="customer" width="100" height="100" />
        )}
        {props.role === "Hotelier" && (
          <img src={HotelPng} alt="customer" width="100" height="100" />
        )}
        <Input type="email" name="emailAddress" placeholder="Email Address" />
        <Input type="password" name="password" placeholder="Password" />
        <MutedLink href="/signup">Register a new account</MutedLink>
        <SubmitButton>Log in</SubmitButton>
      </FormContainer>
    );
  }
  return (
    <div>
      <NavBarcomp />
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
            key="traveller"
            onClick={() => {
              setRole("Traveller");
            }}
          >
            <img src={CustomerPng} alt="customer" width="100" height="100" />
            <h2>I'm a Traveller</h2>
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
