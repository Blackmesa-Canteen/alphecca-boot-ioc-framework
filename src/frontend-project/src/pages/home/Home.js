import React from "react";
import { useParams } from "react-router-dom";
import Featured from "../../components/features/feature/Featured";
import PropertyList from "../../components/features/propertylist/PropertyList";
import Header from "../../components/header/Header";
import Mail from "../../components/mail/Mail";
import { HomeNavBar,CustomerNavBar } from "../../components/navbar/NavBar";
import { HomeContainer, HomeTitle } from "./HomeElements";

export default function HomePage() {
  const {id} = useParams();
  console.log(id)
  return (
    <div>
      {(typeof id==='undefined')?<HomeNavBar /> :<CustomerNavBar value= {id}/>}
      <Header item = {id}/>
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
