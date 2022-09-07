import axios from "axios";
import {useState, useEffect}from "react";
const BASE_URL = "http://localhost:8088/";

//customer signup
export async function signup(user){
    const endpoint = BASE_URL+`api/customer/`
    const {userId, userName, password1, password2} = user;
    if(!userId){
        alert("Please provide your email address");
        return
    }
    if(!password1){
        alert("Please enter password");
        return
    }
    if(!userName){
        alert("Please enter your name");
        return;
    }
    if(password1!==password2){
        alert("Passwords do not match, please check");
        return;
    }

    axios({
        url:endpoint,
        headers:{
            "Content-Type": "application/json",
        },
        method: "POST",
        data:{
            userId: userId,
            userName:userName,
            password:password1,
        },
    })
    .then(()=>{
        console.log("successfully create a account");
        
    })
    .catch((e)=>{
        console.log(e);
        alert("An error occured")
    })
}
