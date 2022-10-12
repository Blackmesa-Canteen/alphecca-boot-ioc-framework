import axios from "axios";
import { useState, useEffect } from "react";
import { BASE_URL, checkMsg } from "./CommonApi";

const headers = {
  "Content-Type": "application/json",
  Authorization: localStorage.getItem("Admin"),
};
const ADD_PATH = "/admin/management/";

// get all customer ~
export async function getAllCustomer(info) {
  const endpoint =
    BASE_URL +
    ADD_PATH +
    `customer/?pageNo=${encodeURIComponent(
      info.pageNo
    )}&pageSize=${encodeURIComponent(info.pageSize)}`;
  return await fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}
export function ViewAllCustomer(info) {
  const [loading, setLoading] = useState(true);
  const [customers, setCustomers] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getAllCustomer(info)
      .then((res) => {
        setCustomers(res.data);

        setLoading(false);
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    customers,
    error,
  };
}
//register a new hotelier~
export async function registerNewHotelier(hotelier) {
  const endpoint = BASE_URL + ADD_PATH + `hotelier/new`;
  await axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify({
      userId: hotelier.userId,
      userName: hotelier.userName,
      password: hotelier.password,
    }),
  })
    .then((res) => {
      alert("successfully registered");
      window.location = "/admin/hoteliers";
      return;
    })
    .catch((e) => {
      console.log(e);
      alert("failed to register");
      return;
    });
}
//assign hotelier to a group
export async function groupHotelier(info) {
  const endpoint = BASE_URL + ADD_PATH + `hotelier/existing`;
  await axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify({
      hotelOwningHotelierUserId: info.hotelOwningHotelierUserId,
      hotelierToAddUserId: info.hotelierToAddUserId,
    }),
  })
    .then((res) => {
      checkMsg(res.data.msg);
      alert("Hotelier added");
      window.location = "/admin/hotels";
      return;
    })
    .catch((e) => {
      console.log(e);
      alert("failed to group the hotelier");
      return;
    });
}
//get all hotels ~
async function getAllHotel(info) {
  const endpoint =
    BASE_URL +
    ADD_PATH +
    `hotel/?pageNo=${encodeURIComponent(
      info.pageNo
    )}&pageSize=${encodeURIComponent(info.pageSize)}`;
  return await fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}

export function ViewAllHotels(info) {
  const [loading, setLoading] = useState(true);
  const [hotels, setHotels] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getAllHotel(info)
      .then((res) => {
        setHotels(res.data);
        setLoading(false);
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    hotels,
    error,
  };
}

//change hotel status ~
export async function changeHotelStatus(info) {
  const endpoint = BASE_URL + ADD_PATH + `hotel`;
  await axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify({
      hotelId: info,
    }),
  })
    .then((res) => {
      checkMsg(res.data.msg);
      window.location = "/admin/hotels";
      return;
    })
    .catch((e) => {
      console.log(e);
      alert("failed to group the hotelier");
      return;
    });
}

//find all hoteliers with same hotelId ~
export async function getHotelierGroup(info) {
  const endpoint =
    BASE_URL + ADD_PATH + `hotelier/?hotelId=${encodeURIComponent(info)}`;
  return fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}
export function ViewHotelierGroup(info) {
  const [loading, setLoading] = useState(true);
  const [hoteliers, setHoteliers] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getHotelierGroup(info)
      .then((res) => {

        setHoteliers(res.data);
        setLoading(false);
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    hoteliers,
    error,
  };
}
// remove hotelier from  hotel ~
export async function removeHotelier(info) {
  const endpoint = BASE_URL + ADD_PATH + `hotelier/remove`;
  await axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify({
      hotelierId: info,
    }),
  })
    .then((res) => {
      checkMsg(res.data.msg);
      alert("Hotelier removed");
      window.location = "/admin/hotels";
      return;
    })
    .catch((e) => {
      console.log(e);
      alert("failed to group the hotelier");
      return;
    });
}

// get all hoteliers~
export async function getAllHotelier(info) {
  const endpoint =
    BASE_URL +
    ADD_PATH +
    `hotelier/all/?pageNo=${encodeURIComponent(
      info.pageNo
    )}&pageSize=${encodeURIComponent(info.pageSize)}`;
  return fetch(endpoint, {
    method: "GET",
    headers: headers,
  }).then((res) => res.json());
}
export function ViewAllhotelier(info) {
  const [loading, setLoading] = useState(true);
  const [hoteliers, setHoteliers] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getAllHotelier(info)
      .then((res) => {
        setHoteliers(res.data.beans);
        setLoading(false);
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, []);
  return {
    loading,
    hoteliers,
    error,
  };
}
//update hotelier info
export async function updateHotelier(data) {
  const endpoint = BASE_URL + ADD_PATH + "hotelier/update";
  await axios({
    url: endpoint,
    method: "PUT",
    headers: headers,
    data: JSON.stringify(data)
  })
    .then((res) => {
      checkMsg(res.data.msg);
      alert("info updated");
      window.location = "/admin/hoteliers"
    })
    .catch((err) => {
      console.log(err);
      alert("something went wrong");
    });
}

//update customer info
export async function updateCustomer(data) {
  const endpoint = BASE_URL + ADD_PATH + "customer/update";
  await axios({
    url: endpoint,
    method: "PUT",
    headers: headers,
    data: JSON.stringify(data)
  })
    .then((res) => {
      checkMsg(res.data.msg);
      console.log(res.data)
      alert("info updated");
      window.location="/admin"
    })
    .catch((err) => {
      console.log(err);
      alert("something went wrong");
    });
}
