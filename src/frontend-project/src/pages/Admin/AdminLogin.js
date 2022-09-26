import React, { useState } from "react";
import { AdminNavBar } from "../../components/navbar/NavBar";
import {
  GlobalStyle,
  Input,
  SubmitButton,
} from "../../components/common/CustomerStyle";
import AdminPng from "../../Picture/admin.png";
import { Login } from "../../API/CommonApi";

export default function AdminLogin() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const admin = {
    role: "Admin",
    userId: email,
    password: password,
  };
  return (
    <div>
      <AdminNavBar />
      <center>
        <br />
        <img src={AdminPng} alt="admin" width="100" height="150" />
        <br />
        <div style={{width:"40%"}}>
        <Input
          type="email"
          placeholder="Emial Address"
          value={email}
          onChange={(e) => {
            setEmail(e.target.value);
          }}
        />
        <Input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
        <div style={{width:"50%"}}>
        <SubmitButton
          onClick={() => {
            Login(admin);
          }}
        >
          Submit
        </SubmitButton>
        </div>
        </div>
      </center>

      <GlobalStyle />
    </div>
  );
}
