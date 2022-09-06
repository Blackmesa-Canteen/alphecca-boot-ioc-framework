import React from "react";
import { Navbar, NavbarBrand } from "react-bootstrap";
import { AlpheccaLogo, GlobalStyle, Input, SubmitButton } from "../components/UserStyle";
import AdminPng from "../Picture/admin.png";

export default function AdminLogin() {
  return (
    <div>
      <Navbar bg="dark" variant="dark">
        <NavbarBrand href="/">
          <AlpheccaLogo />
        </NavbarBrand>
      </Navbar>
      <center>
        <br/>
        <img src={AdminPng} alt="admin" width="100" height="150" />
        <br/>
        <Input type="email" placeholder="Emial Address"/>
        <Input type="password" placeholder="Password"/>
        <SubmitButton>Submit</SubmitButton>
      </center>

      <GlobalStyle />
    </div>
  );
}
