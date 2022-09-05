import React from "react"
import { FormContainer, SubmitButton, Input, GlobalStyle, IconContainer } from "../components/Common";
import NavBarcomp from "../components/NavBar";
import AlpheccaIcon from '../Picture/5Star.png';

export default function Signuppage(props) {
  return (
    <div>
      <NavBarcomp/>
        <center>
   <FormContainer>
    <h2>Register</h2>
    <Input type = "text" name ="firstName" placeholder="First Name"/>
    <Input type = "text" name ="lastName" placeholder="Last Name"/>
    <Input type="email" name="emailAddress" placeholder="Email Address"/> 
    <Input type="password" name="password" placeholder="Password"/> 
    <SubmitButton>Sign up</SubmitButton>
   </FormContainer>
   <IconContainer>
   <img src={AlpheccaIcon} alt="Logo"  width="160" height="145" />
   </IconContainer>
   </center>
   <GlobalStyle/>
   </div>
  );
}