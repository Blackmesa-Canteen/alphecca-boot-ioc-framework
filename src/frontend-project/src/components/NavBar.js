import "bootstrap/dist/css/bootstrap.min.css";
import { Nav, Navbar, Button } from "react-bootstrap";
import { IoPersonCircleSharp } from "react-icons/io5";
import { Input, AlpheccaLogo } from "./CustomerStyle";
import styled from "styled-components";
import { Logout } from "../API/CommonApi";

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
export function HotelierNavBar(props) {
  return (
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
      <Nav style={{ position: "absolute", right: "1%" }}>
        <div style={{ marginTop: 5, marginRight: 5 }}>
          <Button
            onClick={() => {
              Logout("Hotelier");
            }}
          >
            Log out
          </Button>
        </div>
        <IoPersonCircleSharp color="white" fontSize="50" />
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
      <Nav>
        <Nav.Link href="/admin">Customer</Nav.Link>
        <Nav.Link href="/admin/hotels">Hotels</Nav.Link>
        <Nav.Link href="/admin/hoteliers">Hoteliers</Nav.Link>
        </Nav>
        <Nav style={{ position: "absolute", right: "1%" }}>
        <Button
          onClick={() => {
            Logout("Admin");
          }}
        >
          Log out
        </Button>
      </Nav>
    </Navbar>
  );
}
