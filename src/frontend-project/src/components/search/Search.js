import { Link } from "react-router-dom";
import {
  AmenityContainer,
  AmenityIcon,
  SearchCancelOp,
  SearchCheckedBtn,
  SearchContainer,
  SearchDescription,
  SearchDetails,
  SearchDetailWrap,
  SearchFeatures,
  SearchImg,
  SearchOpSubtitle,
  SearchPrice,
  SearchRating,
  SearchRatingBtn,
  SearchRatingSpan,
  SearchSubtitle,
  SearchTaxOp,
  SearchTitle,
} from "./SearchElements";
import {
  faDumbbell,
  faMartiniGlass,
  faMugSaucer,
  faWifi,
  faFan,
  faShirt,
  faCloudSun,
  faSquareParking,
  faPersonSwimming,
  faUtensils,
  faBellConcierge,
} from "@fortawesome/free-solid-svg-icons";

const Search = ({ item }) => {
  const ShowIcon = (amenity) => {
    const description = amenity.description;
    const id = amenity.id;
    if (description === "Air Condition") {
      return <AmenityIcon icon={faFan} alt="Air Conditioner" key={id} />;
    }
    if (description === "Bar") {
      return <AmenityIcon icon={faMartiniGlass} alt="Bar" key={id} />;
    }
    if (description === "Breakfast") {
      return <AmenityIcon icon={faMugSaucer} alt="Breakfast" key={id} />;
    }
    if (description === "Free Wifi") {
      return <AmenityIcon icon={faWifi} alt="Free Wifi" key={id} />;
    }
    if (description === "Gym") {
      return <AmenityIcon icon={faDumbbell} alt="Gym" key={id} />;
    }
    if (description === "Laundry") {
      return <AmenityIcon icon={faShirt} alt="Laundry" key={id} />;
    }
    if (description === "Outdoor") {
      return <AmenityIcon icon={faCloudSun} alt="Outdoor" key={id} />;
    }
    if (description === "Parking") {
      return <AmenityIcon icon={faSquareParking} alt="Parking" key={id} />;
    }
    if (description === "Swimming Pool") {
      return (
        <AmenityIcon icon={faPersonSwimming} alt="Swimming Pool" key={id} />
      );
    }
    if (description === "Restaurant") {
      return <AmenityIcon icon={faUtensils} alt="Restaurant" key={id} />;
    }
    if (description === "Room Service") {
      return <AmenityIcon icon={faBellConcierge} alt="Room Service" key={id} />;
    }
  };

  return (
    <SearchContainer>
      <SearchImg
        src="https://dummyimage.com/420x320/ff7f7f/333333.png&text=Sample"
        alt=""
      />
      <SearchDescription>
        <SearchTitle>{item.name}</SearchTitle>
        <SearchSubtitle>Amenities:</SearchSubtitle>
        <AmenityContainer>
          {item.amenities.map((amenity) => ShowIcon(amenity))}
        </AmenityContainer>
        <SearchCancelOp>Great Price! </SearchCancelOp>
        <SearchOpSubtitle>
          Get the most valuable price for today!
        </SearchOpSubtitle>
      </SearchDescription>
      <SearchDetails>
        <SearchDetailWrap>
          <SearchPrice>${item.money.amount}</SearchPrice>
          <SearchTaxOp>Includes taxes and fees</SearchTaxOp>
          <Link to={`/hotels/${item.hotelId}`}>
            <SearchCheckedBtn>Check Details</SearchCheckedBtn>
          </Link>
        </SearchDetailWrap>
      </SearchDetails>
    </SearchContainer>
  );
};

export default Search;
