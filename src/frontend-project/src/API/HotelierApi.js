import axios from "axios";
import { useState, useEffect } from "react";
import { BASE_URL } from "./CommonApi";

// get hotelier user information
function getInfo() {
  const endpoint = BASE_URL + `hotelier`;
  return fetch(endpoint, {
    method: "GET",
    headers: {
      Authorization: localStorage.getItem("Hotelier"),
    },
  }).then((res) => res.json());
}
export function HotelierInfo() {
  const [loading, setLoading] = useState(true);
  const [hotelier, setHotelier] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getInfo()
      .then((res) => {
        setLoading(false);
        setHotelier(res.data);
      })
      .catch((e) => {
        setLoading(false);
        setError(e);
      });
  }, []);
  return {
    loading,
    hotelier,
    error,
  };
}

//get owned hotel info
function getOwnedHotel() {
  const endpoint = BASE_URL + `hotelier/owned_hotel/`;
  return fetch(endpoint, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("Hotelier"),
    },
  }).then((res) => res.json());
}

export function HotelDetail() {
  const [loading, setLoading] = useState(true);
  const [hotel, setHotel] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getOwnedHotel()
      .then((res) => {
        setHotel(res.data);
        setLoading(false);
      })
      .catch((e) => {
        console.log(e);
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    hotel,
    error,
  };
}
//register a hotel (post)
export async function registerHotel(hotel) {
  const endpoint = BASE_URL + `hotelier/owned_hotel`;
  const { name, description, address, postCode, onSale, amenityIds } = hotel;
  axios({
    url: endpoint,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("Hotelier"),
    },
    data: JSON.stringify(
      {
        name: name,
        description: description,
        address: address,
        postCode: postCode,
        onSale: onSale,
        amenityIds: amenityIds,
      },
      { withCrednetials: true }
    ),
  })
    .then(() => {
      console.log("success");
    })
    .catch((e) => {
      console.log(e);
    });
}

//edit hotel facility (put)
export async function editHotel(hotel) {
  const endpoint = BASE_URL + `hotelier/owned_hotel`;
  const { name, description, address, postCode, onSale, amenityIds } = hotel;
  axios({
    url: endpoint,
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("Hotelier"),
    },
    data: JSON.stringify(
      {
        name: "Your Star",
        description: "loong description for test only, will be changed later",
        address: address,
        postCode: postCode,
        onSale: onSale,
        amenityIds: ["1", "2", "3", "4", "5","6","7","8","9","10","11"],
      },
      { withCrednetials: true }
    ),
  })
    .then(() => {
      console.log("success");
    })
    .catch((e) => {
      console.log(e);
    });
}

//create a new hotel room (post)
export function createRoomType(room) {
  const endpoint = BASE_URL + `/hotelier/owned_hotel/room`;

  axios({
    url: endpoint,
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("Hotelier"),
    },
    data: JSON.stringify(
      {
        hotelId: room.hotelId,
        name: room.name,
        description: room.description,
        pricePerNight: room.pricePerNight,
        sleepsNum: room.sleepsNum,
        vacantNum: room.vacantNum,
        onSale: room.onSale,
        currency: room.currency,
        amenityIds: room.amenityIds,
      },
      { withCrednetials: true }
    ),
  })
    .then((res) => {
      console.log(res);
    })
    .catch((e) => {
      console.log(e);
    });
}
//edit room (put)

//get owned hotel room
