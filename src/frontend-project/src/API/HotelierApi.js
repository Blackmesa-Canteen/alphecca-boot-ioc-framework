import axios from "axios";
import { useState, useEffect } from "react";
import { BASE_URL, checkMsg } from "./CommonApi";

const headers = {
  "Content-Type": "application/json",
  Authorization: localStorage.getItem("Hotelier"),
};
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
    headers: headers,
  }).then((res) => res.json());
}

export function HotelDetail() {
  const [loading1, setLoading] = useState(true);
  const [hotel, setHotel] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getOwnedHotel()
      .then((res) => {
        console.log(res)
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
    loading1,
    hotel,
    error,
  };
}
//get owned hotel info
async function getOwnedHotelLoked() {
  const endpoint = BASE_URL + `hotelier/owned_hotel/editing`;
  return await fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}

export function HotelDetailwithLock() {
  const [loading1, setLoading] = useState(true);
  const [hotel, setHotel] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getOwnedHotelLoked()
      .then((res) => {
        console.log(res)
        checkMsg(res.msg)
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
    loading1,
    hotel,
    error,
  };
}
//register a hotel (post)
export async function registerHotel(hotel) {
  const endpoint = BASE_URL + `hotelier/owned_hotel`;
  const { name, description, address, postCode, onSale, amenities } = hotel;
  axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify(
      {
        name: name,
        description: description,
        address: address,
        postCode: postCode,
        onSale: onSale,
        amenityIds: amenities,
      },
      { withCrednetials: true }
    ),
  })
    .then(() => {
      console.log("success");
      window.location = "/hotelier";
    })
    .catch((e) => {
      console.log(e);
    });
}

//edit hotel facility (put)
export async function editHotel(hotel) {
  const endpoint = BASE_URL + `hotelier/owned_hotel/v`;
  
  axios({
    url: endpoint,
    method: "PUT",
    headers: headers,
    data: JSON.stringify(
      hotel,
      { withCrednetials: true }
    ),
  })
    .then((res) => {
      checkMsg(res.data.msg);
      alert("success");
      window.location = "/hotelier";
    })
    .catch((e) => {
      console.log(e);
    });
}

//create a new hotel room (post)
export function createRoomType(room) {
  const endpoint = BASE_URL + `/hotelier/owned_hotel/room`;
  const {
    hotelId,
    roomId,
    name,
    description,
    pricePerNight,
    sleepsNum,
    vacantNum,
    onSale,
    currency,
    amenityIds,
  } = room;
  axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify(
      {
        hotelId: hotelId,
        roomId: roomId,
        name: name,
        description: description,
        pricePerNight: pricePerNight,
        sleepsNum: sleepsNum,
        vacantNum: vacantNum,
        onSale: onSale,
        currency: currency,
        amenityIds: amenityIds,
      },
      { withCrednetials: true }
    ),
  })
    .then((res) => {
      window.location = "/hotelier";
    })
    .catch((e) => {
      console.log(e);
    });
}
//edit room (put)
export async function editRoom(room) {
  const endpoint = BASE_URL + `hotelier/owned_hotel/room/editing`;
  const {
    hotelId,
    roomId,
    name,
    description,
    pricePerNight,
    sleepsNum,
    vacantNum,
    onSale,
    currency,
    amenityIds,
  } = room;
  axios({
    url: endpoint,
    method: "PUT",
    headers: headers,
    data: JSON.stringify(
      {
        hotelId: hotelId,
        roomId: roomId,
        name: name,
        description: description,
        pricePerNight: pricePerNight,
        sleepsNum: sleepsNum,
        vacantNum: vacantNum,
        onSale: onSale,
        currency: currency,
        amenityIds: amenityIds,
      },
      { withCrednetials: true }
    ),
  })
    .then(() => {
      console.log("success");
      window.location = "/hotelier";
    })
    .catch((e) => {
      console.log(e);
    });
}
//get owned hotel room
function getOwnedRoom(id) {
  const endpoint = BASE_URL + `hotelier/owned_hotel/room/`;
  return fetch(endpoint, {
    method: "GET",
    headers: headers,
    data: JSON.stringify(
      {
        hotelId: id,
      },
      { withCrednetials: true }
    ),
  }).then((res) => res.json());
}

export function RoomDetail(id) {
  const [loading, setLoading] = useState(true);
  const [rooms, setRoom] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getOwnedRoom(id)
      .then((res) => {
        if(res.msg!=="Ok"){
          alert(res.msg)
          throw new Error(res.msg)
        }
        setRoom(res.data);
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
    rooms,
    error,
  };
}

//show all transictions
export async function getAllTransaction(hotelier) {
  const endpoint =
    BASE_URL +
    `hotelier/transactions/all?hotelierId=${encodeURIComponent(
      hotelier.hotelierId
    )}&currencyName=${encodeURIComponent(hotelier.currencyName)}`;
  return fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}

export function UseAllTransaction(hotelier) {
  const [loading, setLoading] = useState(true);
  const [transaction, setTransaction] = useState([]);
  const [error1, setError] = useState(null);
  useEffect(() => {
    getAllTransaction(hotelier)
      .then((res) => {
        setLoading(false);
        setTransaction(res.data);
        
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    transaction,
    error1,
  };
}
// get room with lock (for edit use)
async function LockedEdit(id){
  const endpoint = BASE_URL+`hotelier/owned_hotel/room/editing/?roomId=${id}`;
  return await fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}
export function GetOneRoom(id){
  const [loading, setLoading] = useState(true);
  const [room, setRoom] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    LockedEdit(id)
      .then((res) => {
        checkMsg(res.msg)
        setLoading(false);
        setRoom(res.data);
      })
      .catch((e) => {
        setLoading(false);
        setError(e);
      });
  }, []);
  return {
    loading,
    room,
    error,
  };
}