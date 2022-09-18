import React from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";

const Booking = (item) => {
  return (
    <div>
      <Card style={{ width: "18rem" }}>
        <Card.Body>
          <Card.Title>Card Title</Card.Title>
          <ListGroup variant="flush">
            <ListGroup.Item>Move In Date:</ListGroup.Item>
            <ListGroup.Item>Move Out Date:</ListGroup.Item>
            <ListGroup.Item>Number of People:</ListGroup.Item>
          </ListGroup>
          <Button variant="primary">Edit</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

export default Booking;
