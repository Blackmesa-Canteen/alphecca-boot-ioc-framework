import React, { useState } from "react";
import {
  FormContainer,
  SubmitButton,
  Input,
  GlobalStyle,
  IconContainer,
  CheckboxContainer,
} from "../components/CustomerStyle";

import { Form } from "react-bootstrap";
import { CustomerNavBar } from "../components/NavBar";
import AlpheccaIcon from "../Picture/5Star.png";
import { signup } from "../API/CommonApi";

export default function Signuppage() {
  function SignupForm() {
    const [user, setUser] = useState({
      userName: "",
      userId: "",
      password1: "",
      password2: "",
    });
    const [customerBox, customerChecked] = useState(false);
    const [hotelierBox, hotelierChecked] = useState(false);
    const [role, setRole] = useState("");
    const { userName, userId, password1, password2 } = user;
    //set all values to the json form
    const onChange = (info) => {
      setUser({ ...user, [info.target.id]: info.target.value });
    };
    //role tick
    const handleHotelier = () => {
      setRole("Hotelier");
      hotelierChecked((current) => !current);
      if (customerBox === true) {
        customerChecked(false);
      }
    };
    const handleCustomer = () => {
      setRole("Customer");
      customerChecked((current) => !current);
      if (hotelierBox === true) {
        hotelierChecked(false);
      }
    };

    //submit form
    function onSubmit() {
      const newCustomer = {
        role: role,
        userName: userName,
        userId: userId,
        password1: password1,
        password2: password2,
      };
      if(newCustomer.role===""){
        alert("Please select your role")
      }else{
        signup(newCustomer);
      }
    }
    return (
      <FormContainer>
        <h2>Register</h2>
        <Input
          type="text"
          value={userName}
          id="userName"
          placeholder="User Name"
          onChange={onChange}
        />
        <Input
          type="email"
          value={userId}
          onChange={onChange}
          id="userId"
          placeholder="Email Address"
          autoFocus
        />
        <Input
          type="password"
          value={password1}
          onChange={onChange}
          id="password1"
          placeholder="Password"
        />
        <Input
          type="password"
          value={password2}
          onChange={onChange}
          id="password2"
          placeholder="Confirm Password"
        />
        <div
          style={{
            textDecorationLine: "underline",
            marginRight: 170,
            marginTop: 10,
          }}
        >
          Register As:{" "}
        </div>
        <CheckboxContainer>
          <Form.Check
            type="checkbox"
            label="Hotelier"
            id="role"
            value="Hotelier"
            checked={hotelierBox}
            onChange={handleHotelier}
            style={{ marginRight: 20 }}
          />
          <Form.Check
            type="checkbox"
            label="Customer"
            id="role"
            value="Customer"
            onChange={handleCustomer}
            checked={customerBox}
          />
        </CheckboxContainer>

        <Form.Check
          type="checkbox"
          id="disabledFieldsetCheck"
          label="Accept Condition&terms"
          style={{ marginTop: 10, marginRight: 50, marginBottom: 10 }}
        />

        <SubmitButton onClick={onSubmit}>Sign up</SubmitButton>
      </FormContainer>
    );
  }

  return (
    <div>
      <CustomerNavBar />
      <center>
        <SignupForm />
        <IconContainer>
          <img src={AlpheccaIcon} alt="Logo" width="160" height="145" />
        </IconContainer>
      </center>
      <GlobalStyle />
    </div>
  );
}
