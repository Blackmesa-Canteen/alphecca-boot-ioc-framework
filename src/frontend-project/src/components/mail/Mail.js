import React from "react";
import { MailButton, MailContainer, MailInput } from "./MailElements";

const Mail = () => {
  return (
    <MailContainer>
      <h1 className="mailTitle">Save time, save money!</h1>
      <span className="mailDesc">
        Sign up and we'll send the best deals to you
      </span>
      <div>
        <MailInput type="text" placeholder="Your Email" />
        <MailButton>Subscribe</MailButton>
      </div>
    </MailContainer>
  );
};

export default Mail;
