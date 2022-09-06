import React from 'react';
import { Button } from 'react-bootstrap';
import NavBarcomp from '../components/NavBar';
import { GlobalStyle } from '../components/UserStyle';

export default function HomePage(){
    return(
        <div>
        <NavBarcomp/>
        <Button href='/login'>login</Button>
        <br/><br/>
        <Button href='/adminLogin'>Admin</Button>
        
        <GlobalStyle/>
        </div>
    );
}