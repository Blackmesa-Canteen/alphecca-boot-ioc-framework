import React, { useState } from "react";
import {
  FormContainer,
  SubmitButton,
  Input,
  GlobalStyle,
  IconContainer,
  CheckboxContainer,
} from "../components/UserStyle";

import { Form } from "react-bootstrap";
import NavBarcomp from "../components/NavBar";
import AlpheccaIcon from "../Picture/5Star.png";
import { signup } from "../API/CustomerApi";

export default function Signuppage(props) {
  const [userName, setUserName] = useState("");
  const [userId, setUserId] = useState("");
  const [password1, setPassword1] = useState("");
  const [password2, setPassword2] = useState("");
  function onSubmit(){
    const newCustomer={
      userName:userName,
      userId:userId,
      password1:password1,
      password2:password2,
    }
    console.log(newCustomer);
    signup(newCustomer);
  }
  
  function SignupForm() {
    return (
      <FormContainer>
        <h2>Register</h2>
        <Input
          type="text"
          value={userName}
          id="userName"
          placeholder="User Name"
          onChange={(name)=>{
            setUserName(name.target.value)
          }}
        />
        <Input
          type="email"
          value={userId}
          onChange={(id)=>{
            setUserId(id.target.value)
          }}
          id="userId"
          placeholder="Email Address"
        />
        <Input
          type="password"
          value={password1}
          onChange={(p)=>{
            setPassword1(p.target.value)
          }}
          id="password1"
          placeholder="Password"
        />
        <Input
          type="password"
          value={password2}
          onChange={(p)=>{
            setPassword2(p.target.value)
          }}
          id="password2"
          placeholder="Confirm Password"
        />
        <CheckboxContainer>
          <fieldset disabled>
            <Form.Check type="checkbox" label="Hotelier" />
          </fieldset>
          <Form.Check type="checkbox" label="Customer" />
        </CheckboxContainer>

        <Form.Check
          type="checkbox"
          id="disabledFieldsetCheck"
          label="Accept Condition&terms"
        />

        <SubmitButton  onClick ={onSubmit}>Sign up</SubmitButton>
      </FormContainer>
    );
  }

  return (
    <div>
      <NavBarcomp />
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
