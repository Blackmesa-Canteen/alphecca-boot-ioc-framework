import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import InputGroup from "react-bootstrap/InputGroup";
import ListGroup from "react-bootstrap/ListGroup";
import { GetHotel, cancelTransac, updateOrder } from "../../API/CustomerApi";
import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";
import Form from "react-bootstrap/Form";
import {
  DetailText,
  DetailPriceBtn,
  HotelDescription,
  HotelTitle,
} from "../../pages/hotel/HotelElements";
import {
  ReserveBtn,
  ReserveCloseIcon,
  ReserveContainer,
  ReserveWrapper,
} from "../reserve/ReserveElements";
const Booking = (item) => {
  const startDate = new Date(item.item.startDate);
  const endDate = new Date(item.item.endDate);
  const { hotel, loading, error } = GetHotel(item.item.hotelId);
  const [editWd, setEditwd] = useState(false);
  //console.log(item.item)
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
              <ListGroup.Item>
                Number of Rooms: {item.item.roomOrders[0].orderedCount}
              </ListGroup.Item>
            </ListGroup>
            <Button
              variant="primary"
              onClick={() => {
                setEditwd(true);
              }}
            >
              Edit
            </Button>
            <Button
              style={{ marginLeft: "3%" }}
              variant="danger"
              onClick={() => {
                cancelTransac(item.item.transactionId);
              }}
            >
              Cancel
            </Button>
          </Card.Body>
        </Card>
        {editWd && (
          <EditForm
            value={item.item}
            onCancel={() => {
              setEditwd(false);
            }}
          />
        )}
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
                Number of People: Number of People:{" "}
                {item.item.roomOrders[0].orderedCount}
              </ListGroup.Item>
            </ListGroup>
          </Card.Body>
        </Card>
      </div>
    );
  }
};
function EditForm(props) {
  const order = props.value;
  const startDate = new Date(order.startDate);
  const endDate = new Date(order.endDate);
  const [movein, setMovein] = useState(timeString(startDate));
  const [moveout, setMoveout] = useState(timeString(endDate));
  const [numpeople, setNumpeople] = useState(order.roomOrders[0].orderedCount);

  const submitBooking = () => {
    const bookingDetail = {
      roomOrderId: order.roomOrders[0].roomOrderId,
      transactionId: order.transactionId,
      newQuantity: numpeople,
    };
    console.log(movein);
    console.log(bookingDetail);
    updateOrder(bookingDetail);

    props.onCancel();
  };
  return (
    <ReserveContainer>
      <ReserveWrapper>
        <ReserveCloseIcon onClick={props.onCancel} icon={faCircleXmark} />
        <HotelTitle>Enter Booking Detail</HotelTitle>
        <InputGroup className="mb-3">
          <InputGroup.Text>Move In / Out Date</InputGroup.Text>
          <Form.Control
            type="date"
            value={movein}
            onChange={(e) => {
              setMovein(e.target.value);
            }}
          />
          <Form.Control
            type="date"
            value={moveout}
            onChange={(e) => {
              setMoveout(e.target.value);
            }}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text>Number of Rooms</InputGroup.Text>
          <Form.Control
            type="text"
            value={numpeople}
            onChange={(e) => {
              setNumpeople(e.target.value);
            }}
          />
        </InputGroup>
        <ReserveBtn onClick={submitBooking}>Reserve Now!</ReserveBtn>
      </ReserveWrapper>
    </ReserveContainer>
  );
}
function timeString(date){
  const year = date.getFullYear();
  const month = date.getMonth()+1;
  const day = date.getDate();
  var s = year.toString()+"-"
  if(month<10){
    s=s+"0"+month.toString()+"-";
  }else{
    s=s+month.toString()+"-";
  }
  if(day<10){
    s=s+"0"+day.toString();
  }else{
    s=s+day.toString();
  }
  return s;
}
export default Booking;
