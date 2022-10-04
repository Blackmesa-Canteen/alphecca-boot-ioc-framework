import { useEffect, useState } from "react";
import axios from "axios";
import { BASE_URL } from "./CommonApi";

const headers = {
  "Content-Type": "application/json",
  Authorization: localStorage.getItem("Customer"),
};
// CustomerInfo
async function getInfo() {
  const endpoint = BASE_URL + `customer`;
  return fetch(endpoint, { method: "GET", headers: headers }).then((res) =>
    res.json()
  );
}
export function CustomerInfo() {
  const [loading, setLoading] = useState(true);
  const [customer, setCustomer] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getInfo()
      .then((res) => {
        setLoading(false);
        setCustomer(res.data);
      })
      .catch((e) => {
        setLoading(true);
        setError(e);
      });
  }, []);

  return { loading, customer, error };
}

const useFetch = (url) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const res = await axios.get(url);
        setData(res.data.data);
      } catch (err) {
        setError(err);
      }
      setLoading(false);
    };
    fetchData();
  }, [url]);

  const reFetch = async () => {
    setLoading(true);
    try {
      const res = await axios.get(url);
      setData(res.data.data);
    } catch (err) {
      setError(err);
    }
    setLoading(false);
  };

  return { data, loading, error, reFetch };
};

//get owned hotel room
export function GetAllTransaction(userId) {
  const [loading, setLoading] = useState(true);
  const [transaction, setTransaction] = useState([]);
  const [error, setError] = useState(null);
  const endpoint =
    BASE_URL +
    `customer/transaction/all?customerId=${encodeURIComponent(
      userId
    )}&currencyName=AUD`;
  useEffect(() => {
    const getData = async () => {
      setLoading(true);
      try {
        const res = await axios.get(endpoint, {
          headers: {
            Authorization: localStorage.getItem("Customer"),
          },
        });
        setTransaction(res.data);
      } catch (err) {
        setError(err);
      }
      setLoading(false);
    };

    getData();
  }, [endpoint]);
  return {
    loading,
    transaction,
    error,
  };
}
//get rooms by hotelid
async function getRooms(hotelId) {
  const endpoint =
    BASE_URL + `shared/room/query/?hotelId=${encodeURIComponent(hotelId)}`;
  return fetch(endpoint).then((res) => res.json());
}
export function HotelRooms(hotelId) {
  const [loading, setLoading] = useState(false);
  const [rooms, setRooms] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getRooms(hotelId)
      .then((res) => {
        setLoading(false);
        setRooms(res.data);
      })
      .catch((e) => {
        setError(e);
        setLoading(false);
      });
  }, [hotelId]);
  return {
    loading,
    rooms,
    error,
  };
}
//bookHotel
export async function bookHotel(id) {
  const endpoint = BASE_URL + `customer/transaction`;
  axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify(id),
  })
    .then((res) => {
      alert("successfully reserved");
    })
    .catch((e) => {
      alert("failed to book, please try later");
      console.log(e);
    });
}

//cancel Transaction
export async function cancelTransac(id) {
  const endpoint =
    BASE_URL +
    `customer/transaction/cancel/?transactionId=${encodeURIComponent(id)}`;
  return fetch(endpoint, { method: "GET", headers: headers }).then((res) => {
    alert("Booking canceled");
    window.location = "/customer";
  });
}

//update roomOrder
export async function updateOrder(info) {
  const endpoint = BASE_URL + `customer/transaction/update`;
  axios({
    url: endpoint,
    method: "POST",
    headers: headers,
    data: JSON.stringify(info),
  })
    .then((res) => {
      console.log(res);
      window.location = "/customer";
    })
    .catch((e) => {
      console.log(e);
    });
}

//get Hotel
export function GetHotel(id) {
  const [loading, setLoading] = useState(true);
  const [hotel, setHotel] = useState("");
  const [error, setError] = useState(null);
  const endpoint = BASE_URL + `shared/hotel/query?hotelId=${id}&currency=AUD`;
  useEffect(() => {
    const getData = async () => {
      setLoading(true);
      try {
        const res = await axios.get(endpoint);
        setHotel(res.data.data);
      } catch (err) {
        setError(err);
      }
      setLoading(false);
    };

    getData();
  }, [endpoint]);
  return {
    hotel,
    loading,
    error,
  };
}

export default useFetch;
