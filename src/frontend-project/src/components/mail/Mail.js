import React from "react";
import { MailButton, MailContainer, MailInput } from "./MailElements";

const Mail = () => {
  return (
    <MailContainer>
      <h1 className="mailTitle">Make Hotel Booking Easy!</h1>
      <span className="mailDesc">Sign up and get the best price!</span>
      <div>
        <MailInput type="text" placeholder="Your Email" />
        <MailButton>Subscribe</MailButton>
      </div>
    </MailContainer>
  );
};

export default Mail;
