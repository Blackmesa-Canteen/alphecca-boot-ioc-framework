import React from 'react';
import { Button } from 'react-bootstrap';
import {CustomerNavBar} from '../components/NavBar';
import { GlobalStyle } from '../components/CustomerStyle';
import { Logout } from '../API/CommonApi';
export default function HomePage(){
    
    console.log(localStorage.getItem("Customer"));
    console.log(localStorage.getItem("Hotelier"));
    return(
        <div>
        <CustomerNavBar/>
        <Button href='/login'>login</Button>
        <br/><br/>
        <Button href='/adminLogin'>Admin</Button>
        <br/><br/>
        <Button onClick={Logout}>logout</Button>
        <GlobalStyle/>
        </div>
    );
}