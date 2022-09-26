import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";
import Form from "react-bootstrap/Form";
import { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";

import {
  ReserveBtn,
  ReserveCloseIcon,
  ReserveContainer,
  ReserveWrapper,
} from "./ReserveElements";

const Reserve = ({ setOpen, hotelId }) => {
  const [moveIn, setMoveIn] = useState("");
  const [moveout, setMoveOut] = useState("");
  const [people, setPeople] = useState("");

  const handleMoveIn = (e) => {
    setMoveIn((prev) => {
      return {
        ...prev,
        moveIn: e.target.value,
      };
    });
  };

  const handleMoveOut = (e) => {
    setMoveOut((prev) => {
      return {
        ...prev,
        moveout: e.target.value,
      };
    });
  };

  const handlePeople = (e) => {
    setPeople((prev) => {
      return {
        ...prev,
        people: e.target.value,
      };
    });
  };

  const handleBtn = () => {
    console.log({ moveIn, moveout, people });
  };

  return (
    <ReserveContainer>
      <ReserveWrapper>
        <ReserveCloseIcon onClick={() => setOpen(false)} icon={faCircleXmark} />
        <InputGroup className="mb-3">
          <InputGroup.Text>Move In / Out Date</InputGroup.Text>
          <Form.Control placeholder='"DD-MM-YYYY"' onChange={handleMoveIn} />
          <Form.Control placeholder='"DD-MM-YYYY"' onChange={handleMoveOut} />
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text>Number of People</InputGroup.Text>
          <Form.Control onChange={handleMoveOut} />
        </InputGroup>
        <ReserveBtn onClick={handleBtn}>Reserve Now!</ReserveBtn>
      </ReserveWrapper>
    </ReserveContainer>
  );
};

export default Reserve;
