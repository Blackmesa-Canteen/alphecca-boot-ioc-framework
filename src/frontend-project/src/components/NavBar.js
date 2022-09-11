import "bootstrap/dist/css/bootstrap.min.css";
import { Nav, Navbar, Button } from "react-bootstrap";
import {IoPersonCircleSharp} from 'react-icons/io5'
import { Input, AlpheccaLogo } from "./CustomerStyle";
import styled from "styled-components";

const SearchBar = styled(Input)`
  width: 200px;
  margin-top: 0;
  margin-bottom: 0;
  border-bottom: 1px solid transparent;
`;

export function CustomerNavBar() {
  var notLogin = false;
  return (
    <Navbar bg="dark" variant="dark" sticky="top" expand="sm" collapseOnSelect>
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
      <Navbar.Toggle />
      <Navbar.Collapse>
        <Nav>
          <Nav.Link href="/about">About Us</Nav.Link>
          <Nav.Link href="/contact">Contact</Nav.Link>
          <SearchBar type="search" placeholder="Search Hotel" />
          {notLogin && <Button href="/login">Login</Button>}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}
export function HotelierNavBar(props){
  return(
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
      <Nav style={{position:"absolute", right:"1%"}}>
      <IoPersonCircleSharp color= "white" fontSize="50" />
      </Nav>
    </Navbar>
  );
}

export function AdminNavBar() {
  return (
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
    </Navbar>
  );
}

