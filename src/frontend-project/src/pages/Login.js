import React from "react"
import { FormContainer, SubmitButton, Input, GlobalStyle, MutedLink, IconContainer } from "../components/Common";
import NavBarcomp from "../components/NavBar";
import AlpheccaIcon from '../Picture/5Star.png';

export default function Loginpage() {
  return (
    <div>
      <NavBarcomp/>
        <center>
            <FormContainer>
                <h2>Log In</h2>
                <Input type="email" name="emailAddress" placeholder="Email Address"/> 
                <Input type="password" name="password" placeholder="Password"/> 
                <MutedLink href="/signup">Register a new account</MutedLink>
                <SubmitButton>Log in</SubmitButton>
            </FormContainer>
            <IconContainer>
            <img src={AlpheccaIcon} alt="Logo"  width="160" height="145" margin-top="20"/>
            </IconContainer>
        </center>
        <GlobalStyle/>
   </div>
  );
}