import { Link } from "react-router-dom";
import {
  SearchCancelOp,
  SearchCheckedBtn,
  SearchContainer,
  SearchDescription,
  SearchDetails,
  SearchDetailWrap,
  SearchDistance,
  SearchFeatures,
  SearchImg,
  SearchOpSubtitle,
  SearchPrice,
  SearchRating,
  SearchRatingBtn,
  SearchRatingSpan,
  SearchSubtitle,
  SearchTaxiOp,
  SearchTaxOp,
  SearchTitle,
} from "./SearchElements";

const SearchItem = ({ item }) => {
  return (
    <SearchContainer>
      <SearchImg src={item.photos[0]} alt="" />
      <SearchDescription>
        <SearchTitle className="siTitle">{item.name}</SearchTitle>
        <SearchDistance className="siDistance">
          {item.distance}m from center
        </SearchDistance>
        <SearchTaxiOp>Free airport taxi</SearchTaxiOp>
        <SearchSubtitle>Studio Apartment with Air conditioning</SearchSubtitle>
        <SearchFeatures className="siFeatures">{item.desc}</SearchFeatures>
        <SearchCancelOp>Free cancellation </SearchCancelOp>
        <SearchOpSubtitle>
          You can cancel later, so lock in this great price today!
        </SearchOpSubtitle>
      </SearchDescription>
      <SearchDetails>
        {item.rating && (
          <SearchRating>
            <SearchRatingSpan>Excellent</SearchRatingSpan>
            <SearchRatingBtn>{item.rating}</SearchRatingBtn>
          </SearchRating>
        )}
        <SearchDetailWrap>
          <SearchPrice>${item.cheapestPrice}</SearchPrice>
          <SearchTaxOp>Includes taxes and fees</SearchTaxOp>
          <Link to={`/hotels/${item._id}`}>
            <SearchCheckedBtn className="siCheckButton">
              See availability
            </SearchCheckedBtn>
          </Link>
        </SearchDetailWrap>
      </SearchDetails>
    </SearchContainer>
  );
};

export default SearchItem;
