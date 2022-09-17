import React from "react";
import Featured from "../../components/features/feature/Featured";
import PropertyList from "../../components/features/propertylist/PropertyList";
import Header from "../../components/header/Header";
import Mail from "../../components/mail/Mail";
import { HomeNavBar } from "../../components/NavBar";
import { HomeContainer, HomeTitle } from "./HomeElements";

export default function HomePage() {
  return (
    <div>
      <HomeNavBar />
      <Header />
      <HomeContainer>
        <Featured />
        <HomeTitle>Browse by property type</HomeTitle>
        <PropertyList />
        <HomeTitle>Homes guests love</HomeTitle>
        <PropertyList />
        <Mail />
      </HomeContainer>
    </div>
  );
}
