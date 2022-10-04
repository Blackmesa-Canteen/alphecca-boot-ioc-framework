import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const HotelContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
`;

export const HotelWrapper = styled.div`
  width: 100%;
  max-width: 1024px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  position: relative;
`;

export const HotelTitle = styled.h1`
  font-size: 24px;
`;

export const HotelAddress = styled.div`
  font-size: 12px;
  display: flex;
  align-items: center;
  gap: 10px;
`;

export const HotelDistance = styled.span`
  color: #0071c2;
  font-weight: 500;
`;

export const HotelPriceHighlight = styled.span`
  color: #008009;
  font-weight: 500;
`;

export const HotelImgContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

export const HotelImgWrapper = styled.div`
  width: 33%;
`;

export const HotelImg = styled.img`
  width: 100%;
  object-fit: cover;
  cursor: pointer;
`;

export const HotelDetailContainer = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin-top: 20px;
`;

export const DetailText = styled.div`
  flex: 3;
`;

export const DetailPrice = styled.div`
  flex: 1;
  background-color: #ebf3ff;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

export const DetailPriceH1 = styled.h1`
  font-size: 18px;
  color: #555;
`;

export const DetailPriceSpan = styled.span`
  font-size: 14px;
`;

export const DetailPriceH2 = styled.h2`
  font-weight: 300;
`;

export const DetailPriceBtn = styled.button`
  border: none;
  padding: 10px 20px;
  background-color: #0071c2;
  color: white;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
`;

export const HotelDescription = styled.p`
  font-size: 14px;
  margin-top: 20px;
`;

export const BookBtn = styled.button`
  position: absolute;
  top: 10px;
  right: 0;
  border: none;
  padding: 10px 20px;
  background-color: #0071c2;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  cursor: pointer;
`;

export const SlideContainer = styled.div`
  position: sticky;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.613);
  z-index: 999;
  display: flex;
  align-items: center;
`;

export const SlideWrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const SlideImg = styled.img`
  width: 80%;
  height: 80vh;
`;

export const CloseIcon = styled(FontAwesomeIcon)`
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 30px;
  color: lightgray;
  cursor: pointer;
`;

export const ArrowIcon = styled(FontAwesomeIcon)`
  margin: 20px;
  font-size: 50px;
  color: lightgray;
  cursor: pointer;
`;
