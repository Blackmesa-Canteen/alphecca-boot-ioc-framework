import axios from "axios";
import { useState, useEffect } from "react";
import { BASE_URL } from "./CommonApi";
const headers = {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem("Admin"),
  };
export async function getAllCustomer(info){
    const endpoint = BASE_URL+`admin/management`;
    return fetch(endpoint, {
        method:"GET",
        headers:headers,
        data: JSON.stringify({
            pageNo: info.pageNo,
            pageSize: info.pageSize
        })
    })

}