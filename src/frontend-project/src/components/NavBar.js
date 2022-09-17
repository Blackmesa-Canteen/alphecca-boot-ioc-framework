import "bootstrap/dist/css/bootstrap.min.css";
import { Nav, Navbar, Button } from "react-bootstrap";
import { IoPersonCircleSharp } from "react-icons/io5";
import { AlpheccaLogo } from "./CustomerStyle";
import { Logout } from "../API/CommonApi";

export function CustomerNavBar() {
  return (
    <Navbar bg="black" variant="dark">
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
      <Nav style={{ position: "absolute", right: "1%" }}>
        <div style={{ marginTop: 5, marginRight: 5 }}>
          <Button
            onClick={() => {
              Logout("Customer");
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

export function HotelierNavBar(props) {
  return (
    <Navbar bg="black" variant="dark">
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
    </Navbar>
  );
}

export function HomeNavBar() {
  return (
    <Navbar bg="black" variant="dark" sticky="top" expand="sm" collapseOnSelect>
      <Navbar.Brand href="/">
        <AlpheccaLogo />
      </Navbar.Brand>
      <Navbar.Toggle />
      <Navbar.Collapse>
        <Nav>
          <Nav.Link href="/about">About Us</Nav.Link>
          <Nav.Link href="/contact">Contact</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}
