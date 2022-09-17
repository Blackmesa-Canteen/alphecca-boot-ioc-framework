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

const Search = ({ item }) => {
  return (
    <SearchContainer>
      <SearchImg
        src="https://cf.bstatic.com/xdata/images/hotel/square600/261707778.webp?k=fa6b6128468ec15e81f7d076b6f2473fa3a80c255582f155cae35f9edbffdd78&o=&s=1"
        alt=""
      />
      <SearchDescription>
        <SearchTitle className="siTitle">Tower Street Apartments</SearchTitle>
        <SearchDistance className="siDistance">500m from center</SearchDistance>
        <SearchTaxiOp>Free airport taxi</SearchTaxiOp>
        <SearchSubtitle>Studio Apartment with Air conditioning</SearchSubtitle>
        <SearchFeatures className="siFeatures">
          Entire studio • 1 bathroom • 21m² 1 full bed
        </SearchFeatures>
        <SearchCancelOp>Free cancellation </SearchCancelOp>
        <SearchOpSubtitle>
          You can cancel later, so lock in this great price today!
        </SearchOpSubtitle>
      </SearchDescription>
      <SearchDetails>
        <SearchRating>
          <SearchRatingSpan>Excellent</SearchRatingSpan>
          <SearchRatingBtn>8.9</SearchRatingBtn>
        </SearchRating>
        <SearchDetailWrap>
          <SearchPrice>$124</SearchPrice>
          <SearchTaxOp>Includes taxes and fees</SearchTaxOp>
          <SearchCheckedBtn className="siCheckButton">
            See availability
          </SearchCheckedBtn>
        </SearchDetailWrap>
      </SearchDetails>
    </SearchContainer>
  );
};

export default Search;
