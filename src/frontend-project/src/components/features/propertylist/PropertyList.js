import React from "react";
import {
  PLTitleH1,
  PLTitleH2,
  PropertyListContainer,
  PropertyListImg,
  PropertyListItem,
} from "./PLElements";

const PropertyList = () => {
  return (
    <PropertyListContainer>
      <PropertyListItem>
        <PropertyListImg
          src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
          alt=""
        />
        <div>
          <PLTitleH1>xxxHotel</PLTitleH1>
          <PLTitleH2>description</PLTitleH2>
        </div>
      </PropertyListItem>

      <PropertyListItem>
        <PropertyListImg
          src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
          alt=""
        />
        <div>
          <PLTitleH1>xxxHotel</PLTitleH1>
          <PLTitleH2>description</PLTitleH2>
        </div>
      </PropertyListItem>

      <PropertyListItem>
        <PropertyListImg
          src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
          alt=""
        />
        <div>
          <PLTitleH1>xxxHotel</PLTitleH1>
          <PLTitleH2>description</PLTitleH2>
        </div>
      </PropertyListItem>

      <PropertyListItem>
        <PropertyListImg
          src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
          alt=""
        />
        <div>
          <PLTitleH1>xxxHotel</PLTitleH1>
          <PLTitleH2>description</PLTitleH2>
        </div>
      </PropertyListItem>

      <PropertyListItem>
        <PropertyListImg
          src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
          alt=""
        />
        <div>
          <PLTitleH1>xxxHotel</PLTitleH1>
          <PLTitleH2>description</PLTitleH2>
        </div>
      </PropertyListItem>
    </PropertyListContainer>
  );
};

export default PropertyList;
