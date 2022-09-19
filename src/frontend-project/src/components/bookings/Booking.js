import React from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";
import { GetHotel } from "../../API/CustomerApi";

const Booking = (item) => {
  const startDate = new Date(item.item.startDate);
  const endDate = new Date(item.item.endDate);
  const { hotel, loading, error } = GetHotel(item.item.hotelId);
  if (item.item.statusCode === 1) {
    return (
      <div>
        <Card style={{ width: "18rem" }}>
          <Card.Body>
            <Card.Title>Hotel Name: {hotel.name}</Card.Title>
            <ListGroup variant="flush">
              <ListGroup.Item>
                Move In Date: {startDate.toDateString()}
              </ListGroup.Item>
              <ListGroup.Item>
                Move Out Date: {endDate.toDateString()}
              </ListGroup.Item>
              <ListGroup.Item>Number of People: {hotel.rank}</ListGroup.Item>
            </ListGroup>
            <Button variant="primary">Edit</Button>
            <Button variant="danger">Cancel</Button>
          </Card.Body>
        </Card>
      </div>
    );
  } else {
    return (
      <div>
        <Card style={{ width: "18rem" }}>
          <Card.Body>
            <Card.Title>Hotel Name: {hotel.name}</Card.Title>
            <ListGroup variant="flush">
              <ListGroup.Item>
                Move In Date: {startDate.toDateString()}
              </ListGroup.Item>
              <ListGroup.Item>
                Move Out Date: {endDate.toDateString()}
              </ListGroup.Item>
              <ListGroup.Item>
                Number of People: Number of People: {hotel.rank}
              </ListGroup.Item>
            </ListGroup>
          </Card.Body>
        </Card>
      </div>
    );
  }
};

export default Booking;
