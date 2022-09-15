import React from "react";
import { Button } from "react-bootstrap";
import Header from "../components/header/Header";
import NavBarcomp from "../components/NavBar";
import { GlobalStyle } from "../components/UserStyle";

export default function HomePage() {
  return (
    <div>
      <NavBarcomp />
      <Header></Header>
    </div>
  );
}
