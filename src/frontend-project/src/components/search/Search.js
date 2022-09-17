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
  const ShowIcon = (description) => {
    if (description === "Air Condition") {
      return <AmenityIcon icon={faFan} alt="Air Conditioner" />;
    }
    if (description === "Bar") {
      return <AmenityIcon icon={faMartiniGlass} alt="Bar" />;
    }
    if (description === "Breakfast") {
      return <AmenityIcon icon={faMugSaucer} alt="Breakfast" />;
    }
    if (description === "Free Wifi") {
      return <AmenityIcon icon={faWifi} alt="Free Wifi" />;
    }
    if (description === "Gym") {
      return <AmenityIcon icon={faDumbbell} alt="Gym" />;
    }
    if (description === "Laundry") {
      return <AmenityIcon icon={faShirt} alt="Laundry" />;
    }
    if (description === "Outdoor") {
      return <AmenityIcon icon={faCloudSun} alt="Outdoor" />;
    }
    if (description === "Parking") {
      return <AmenityIcon icon={faSquareParking} alt="Parking" />;
    }
    if (description === "Swimming Pool") {
      return <AmenityIcon icon={faPersonSwimming} alt="Swimming Pool" />;
    }
    if (description === "Restaurant") {
      return <AmenityIcon icon={faUtensils} alt="Restaurant" />;
    }
    if (description === "Room Service") {
      return <AmenityIcon icon={faBellConcierge} alt="Room Service" />;
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
          {item.amenities.map((amenity) => ShowIcon(amenity.description))}
        </AmenityContainer>
        <SearchCancelOp>Great Price! </SearchCancelOp>
        <SearchOpSubtitle>
          Get the most valuable price for today!
        </SearchOpSubtitle>
      </SearchDescription>
      <SearchDetails>
        <SearchDetailWrap>
          <SearchPrice>$299</SearchPrice>
          <SearchTaxOp>Includes taxes and fees</SearchTaxOp>
          <SearchCheckedBtn>Check Details</SearchCheckedBtn>
        </SearchDetailWrap>
      </SearchDetails>
    </SearchContainer>
  );
};

export default Search;
