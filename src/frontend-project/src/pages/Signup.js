import React from "react";
import {
  FormContainer,
  SubmitButton,
  Input,
  GlobalStyle,
  IconContainer,
  CheckboxContainer,
} from "../components/UserStyle";
import { Form } from 'react-bootstrap';
import NavBarcomp from "../components/NavBar";
import AlpheccaIcon from "../Picture/5Star.png";

export default function Signuppage(props) {
  function SignupForm() {
    return (
      <FormContainer>
        <h2>Register</h2>
        <Input type="text" name="firstName" placeholder="First Name" />
        <Input type="text" name="lastName" placeholder="Last Name" />
        <Input type="email" name="emailAddress" placeholder="Email Address" />
        <Input type="password" name="password" placeholder="Password" />
        <Input
          type="password"
          name="confirmPassword"
          placeholder="Confirm Password"
        />
        <CheckboxContainer>
        <fieldset disabled>
        <Form.Check type="checkbox" label="Hotelier"/>
        </fieldset>
        <Form.Check type="checkbox" label="Traveller"/>
        
        </CheckboxContainer>
        
        <Form.Check type="checkbox" id="disabledFieldsetCheck" label="Accept Condition&terms"/>
       
        <SubmitButton>Sign up</SubmitButton>
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
