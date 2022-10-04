import React from "react";
import { useNavigate, useParams } from "react-router-dom";

import { MailButton, MailContainer } from "./MailElements";

const Mail = () => {
  const { id } = useParams();

  const navigate = useNavigate();
  const handleClick = () => {
    if (id === undefined) {
      navigate("/signup");
    } else {
      alert("You have sign in already!");
    }
  };
  return (
    <MailContainer>
      <h1 className="mailTitle">Make Hotel Booking Easy!</h1>
      <span className="mailDesc">Sign up and get the best price!</span>
      <div>
        <MailButton onClick={handleClick}>Sign Up</MailButton>
      </div>
    </MailContainer>
  );
};

export default Mail;
