import axios from "axios";
import { useState, useEffect } from "react";
import { BASE_URL } from "./CommonApi";

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
