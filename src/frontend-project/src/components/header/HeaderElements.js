import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { DateRange } from "react-date-range";

export const HeaderWrap = styled.div`
  background-color: #003580;
  color: white;
  display: flex;
  justify-content: center;
  position: relative;
`;

export const HeaderContainer = styled.div`
  width: 100%;
  max-width: 1024px;
  margin: 20px 0px 100px 0px;
`;

export const HeaderP = styled.p`
  margin: 20px 0px;
`;

export const HeaderBtn = styled.button`
  background-color: #0071c2;
  color: white;
  font-weight: 500;
  border: none;
  padding: 10px;
  cursor: pointer;
`;

export const HeaderSearch = styled.div`
  height: 60px;
  background-color: white;
  border: 3px solid #febb02;
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 10px 0px;
  border-radius: 5px;
  position: absolute;
  bottom: -25px;
  width: 100%;
  max-width: 1024px;
`;

export const HeaderSearchItem = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

export const HeaderSearchInput = styled.input`
  border: none;
  outline: none;
`;

export const SearchSpan = styled.span`
  color: lightgray;
  cursor: pointer;
`;

export const SearchIcon = styled(FontAwesomeIcon)`
  color: lightgray;
`;

export const HeaderDate = styled(DateRange)`
  position: absolute;
  top: 50px;
  z-index: 2;
`;

export const PeopleOptions = styled.div`
  z-index: 2;
  position: absolute;
  top: 50px;
  background-color: white;
  color: gray;
  border-radius: 5px;
  -webkit-box-shadow: 0px 0px 10px -5px rgba(0, 0, 0, 0.4);
  box-shadow: 0px 0px 10px -5px rgba(0, 0, 0, 0.4);
`;

export const OptionItem = styled.div`
  width: 200px;
  display: flex;
  justify-content: space-between;
  margin: 10px;
`;

export const OptionCounter = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 12px;
  color: black;
`;

export const OptionBtn = styled.button`
  width: 30px;
  height: 30px;
  border: 1px solid #0071c2;
  color: #0071c2;
  cursor: pointer;
  background-color: white;
`;
