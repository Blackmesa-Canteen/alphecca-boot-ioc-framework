import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const SearchContainer = styled.div`
  border: 1px solid lightgray;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 20px;
`;

export const SearchImg = styled.img`
  width: 200px;
  height: 200px;
  object-fit: cover;
`;

export const SearchDescription = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 2;
`;

export const SearchTitle = styled.h1`
  font-size: 20px;
  color: black;
`;

export const SearchDistance = styled.span`
  font-size: 12px;
`;

export const SearchTaxiOp = styled.span`
  font-size: 12px;
  background-color: #008009;
  color: white;
  width: max-content;
  padding: 3px;
  border-radius: 5px;
`;

export const SearchSubtitle = styled.span`
  font-size: 12px;
  font-weight: bold;
`;

export const SearchFeatures = styled.span`
  font-size: 12px;
`;

export const SearchCancelOp = styled.span`
  font-size: 12px;
  color: #0071c2;
  font-weight: bold;
`;

export const SearchOpSubtitle = styled.span`
  font-size: 12px;
  color: #0071c2;
`;

export const SearchDetails = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const SearchRating = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const SearchRatingSpan = styled.span`
  font-weight: 500;
`;

export const SearchRatingBtn = styled.button`
  background-color: #003580;
  color: white;
  padding: 5px;
  font-weight: bold;
  border: none;
`;

export const SearchDetailWrap = styled.div`
  text-align: right;
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

export const SearchPrice = styled.span`
  font-size: 24px;
`;

export const SearchTaxOp = styled.span`
  font-size: 12px;
  color: gray;
`;

export const SearchCheckedBtn = styled.button`
  background-color: #008000;
  color: white;
  font-weight: bold;
  padding: 10px 5px;
  border: none;
  cursor: pointer;
  border-radius: 15px;
`;

export const AmenityContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

export const AmenityIcon = styled(FontAwesomeIcon)`
  color: grey;
`;
