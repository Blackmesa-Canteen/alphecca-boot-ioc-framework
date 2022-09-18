import { CustomerNavBar } from "../../components/navbar/NavBar";
import Header from "../../components/header/Header";
import Mail from "../../components/mail/Mail";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useLocation, useNavigate } from "react-router-dom";
import { BASE_URL } from "../../API/CommonApi";
import useFetch from "../../API/CustomerApi";
import {
  ArrowIcon,
  BookBtn,
  CloseIcon,
  DetailPrice,
  DetailPriceBtn,
  DetailPriceH1,
  DetailPriceH2,
  DetailPriceSpan,
  DetailText,
  HotelAddress,
  HotelContainer,
  HotelDescription,
  HotelDetailContainer,
  HotelDistance,
  HotelImg,
  HotelImgContainer,
  HotelImgWrapper,
  HotelPriceHighlight,
  HotelTitle,
  HotelWrapper,
  SlideContainer,
  SlideImg,
  SlideWrapper,
} from "./HotelElements";

import {
  faCircleArrowLeft,
  faCircleArrowRight,
  faCircleXmark,
  faLocationDot,
} from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";

const Hotel = () => {
  const images = [
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707778.jpg?k=56ba0babbcbbfeb3d3e911728831dcbc390ed2cb16c51d88159f82bf751d04c6&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707367.jpg?k=cbacfdeb8404af56a1a94812575d96f6b80f6740fd491d02c6fc3912a16d8757&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261708745.jpg?k=1aae4678d645c63e0d90cdae8127b15f1e3232d4739bdf387a6578dc3b14bdfd&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707776.jpg?k=054bb3e27c9e58d3bb1110349eb5e6e24dacd53fbb0316b9e2519b2bf3c520ae&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261708693.jpg?k=ea210b4fa329fe302eab55dd9818c0571afba2abd2225ca3a36457f9afa74e94&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707389.jpg?k=52156673f9eb6d5d99d3eed9386491a0465ce6f3b995f005ac71abc192dd5827&o=&hp=1",
    },
  ];

  const location = useLocation();
  const id = location.pathname.split("/")[2];
  const { data, loading, error } = useFetch(
    BASE_URL + `/shared/hotel/query?hotelId=${id}&currency=AUD`
  );

  const [open, setOpen] = useState(false);
  const [slideNumber, setSlideNumber] = useState(0);

  const handleMove = (direction) => {
    let newSlideNumber;

    if (direction === "l") {
      newSlideNumber = slideNumber === 0 ? 5 : slideNumber - 1;
    } else {
      newSlideNumber = slideNumber === 5 ? 0 : slideNumber + 1;
    }

    setSlideNumber(newSlideNumber);
  };

  const handleOpen = (i) => {
    setSlideNumber(i);
    setOpen(true);
  };

  return (
    <div>
      <CustomerNavBar />
      <Header type="list" />
      {loading ? (
        "loading"
      ) : (
        <HotelContainer>
          {open && (
            <SlideContainer>
              <CloseIcon icon={faCircleXmark} onClick={() => setOpen(false)} />
              <ArrowIcon
                icon={faCircleArrowLeft}
                onClick={() => handleMove("l")}
              />
              <SlideWrapper>
                <SlideImg src={images[slideNumber].src} alt="" />
              </SlideWrapper>
              <ArrowIcon
                icon={faCircleArrowRight}
                onClick={() => handleMove("r")}
              />
            </SlideContainer>
          )}

          <HotelWrapper>
            <BookBtn>Book Now</BookBtn>
            <HotelTitle>{data.name}</HotelTitle>
            <HotelAddress>
              <FontAwesomeIcon icon={faLocationDot} />
              <span>{data.address}</span>
            </HotelAddress>
            <HotelPriceHighlight>
              Book a stay here for just ${data.minPrice} per night!
            </HotelPriceHighlight>
            <HotelImgContainer>
              {images.map((image, i) => (
                <HotelImgWrapper key={i}>
                  <HotelImg
                    onClick={() => handleOpen(i)}
                    src={image.src}
                    alt=""
                  />
                </HotelImgWrapper>
              ))}
            </HotelImgContainer>
            <HotelDetailContainer>
              <DetailText>
                <HotelTitle>Room Description</HotelTitle>
                <HotelDescription>
                  A detailed description of this room!
                </HotelDescription>
                <HotelDescription>Bla bla bla bla bla bla bla</HotelDescription>
              </DetailText>
              <DetailPrice>
                <DetailPriceBtn>Book Now!</DetailPriceBtn>
              </DetailPrice>
            </HotelDetailContainer>
          </HotelWrapper>
          <Mail />
        </HotelContainer>
      )}
    </div>
  );
};

export default Hotel;
