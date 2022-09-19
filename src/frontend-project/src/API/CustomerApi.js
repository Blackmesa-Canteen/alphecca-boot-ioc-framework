import {useEffect, useState} from "react";
import axios from "axios";
import {BASE_URL} from "./CommonApi";

const headers = {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem("Customer"),
};

export function CustomerInfo() {
    const [loading, setLoading] = useState(true);
    const [customer, setCustomer] = useState([]);
    const [error, setError] = useState(null);
    const endpoint = BASE_URL + `customer?userId=edisonTest1@gmail.com`;
    useEffect(() => {
        const getData = async () => {
            setLoading(true);
            try {
                const res = await axios.get(endpoint);
                setCustomer(res.data);
            } catch (err) {
                setError(err);
            }
            setLoading(false);
        };

        getData();
    }, [endpoint]);

    return {loading, customer, error};
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

    return {data, loading, error, reFetch};
};


//get owned hotel room
function getOwnedTransaction(customer) {
    const endpoint = BASE_URL + `customer/transaction/all?customerId=${customer.userId}&currencyName=AUD`;
    return fetch(endpoint, {
        method: "GET",
        headers: {
            Authorization: localStorage.getItem("Customer"),
        },
    }).then((res) => res.json());
}

export function GetAllTransaction(customer) {
    const [loading, setLoading] = useState(true);
    const [transaction, setTransaction] = useState([]);
    const [error, setError] = useState(null);
    const endpoint = `http://localhost:8088/api/customer/transaction/all?customerId=${customer.data.userId}&currencyName=AUD`;
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

export function GetHotel(id) {
    const [loading, setLoading] = useState(true);
    const [hotel, setHotel] = useState("");
    const [error, setError] = useState(null);
    const endpoint = `http://localhost:8088/api/shared/hotel/query?hotelId=${id}&currency=AUD`;
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
