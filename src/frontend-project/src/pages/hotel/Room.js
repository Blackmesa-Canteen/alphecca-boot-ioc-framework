import React, { useState } from "react";
import { bookHotel, HotelRooms } from "../../API/CustomerApi";
import InputGroup from "react-bootstrap/InputGroup";
import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";
import Form from "react-bootstrap/Form";
import {
  DetailText,
  DetailPriceBtn,
  HotelDescription,
  HotelTitle,
} from "./HotelElements";
import {
  ReserveBtn,
  ReserveCloseIcon,
  ReserveContainer,
  ReserveWrapper,
} from "../../components/reserve/ReserveElements";
export function Rooms(props) {
  const { loading, rooms, error } = HotelRooms(props.id);
  const [editWd, editOpen] = useState(null);
  if (loading) {
    return <div>Loading...</div>;
  } else if (error) {
    return <div>something went wrong</div>;
  }
  const openBookwd=(id)=>{
    const customer = localStorage.getItem("Customer");
    if(typeof customer==="undefined"||customer===null){
      alert("please log in");
      window.location="/login"
      return
    }
    editOpen(id);
    return
  }
  return (
    <div style={{ marginLeft: "5%" }}>
      {rooms.length !== 0 &&
        rooms.map((r) => {
          return (
            <DetailText key={r.roomId}>
              <HotelTitle>{r.name}</HotelTitle>
              <HotelDescription>Description: {r.description}</HotelDescription>
              <HotelDescription> Price: $ {r.pricePerNight}</HotelDescription>
              <DetailPriceBtn
                onClick={()=>{openBookwd(r)}}
              >
                Book Now!
              </DetailPriceBtn>
            </DetailText>
          );
        })}
      {editWd !== null && (
        <BookForm
          value={editWd}
          hotelId={props.id}
          onCancel={() => {
            editOpen(null);
          }}
        />
      )}
    </div>
  );
}

function BookForm(props) {
  const [movein, setMovein] = useState("");
  const [moveout, setMoveout] = useState("");
  const [numpeople, setNumpeople] = useState("");
  const submitBooking = () => {
    var roomMap = {};
    roomMap[props.value.roomId] = numpeople;
    const bookingDetail = {
      startDate: Date.parse(movein),
      endDate: Date.parse(moveout),
      hotelId: props.hotelId,
      roomIdNumberMap: roomMap,
      version:props.value.version
    };
    console.log(numpeople);
    console.log(bookingDetail);
    bookHotel(bookingDetail);
    props.onCancel()
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
