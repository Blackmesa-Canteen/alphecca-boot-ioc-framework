import React from "react";
import { AdminNavBar } from "../components/NavBar";
import { GlobalStyle, Input, SubmitButton } from "../components/CustomerStyle";
import AdminPng from "../Picture/admin.png";

export default function AdminLogin() {
  return (
    <div>
      <AdminNavBar/>
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
