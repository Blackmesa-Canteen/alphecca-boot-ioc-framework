import { useEffect, useState } from "react";
import axios from "axios";
import { BASE_URL } from "./CommonApi";

const headers = {
  "Content-Type": "application/json",
  Authorization: localStorage.getItem("Customer"),
};

// get customer information
function getInfo() {
  const endpoint = BASE_URL + `customer`;
  return fetch(endpoint, {
    method: "GET",
    headers: {
      Authorization: localStorage.getItem("Customer"),
    },
  }).then((res) => res.json());
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
        setLoading(false);
        setError(e);
      });
  }, []);

  return {
    loading,
    customer,
    error,
  };
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
function getOwnedTransaction(customer) {
  const endpoint = `http://localhost:8088/api/customer/transaction/all?customerId=${customer.userId}&currencyName=AUD`;
  return fetch(endpoint, {
    method: "GET",
    headers: {
      Authorization: localStorage.getItem("Customer"),
    },
  }).then((res) => res.json());
}

export function GetBookings(customer) {
  const [loading, setLoading] = useState(true);
  const [booking, setBooking] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    getOwnedTransaction(customer)
      .then((res) => {
        setBooking(res);
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
    booking,
    error,
  };
}

export default useFetch;
