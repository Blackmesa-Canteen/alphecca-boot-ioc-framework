import { CustomerNavBar } from "../../components/NavBar";
import Header from "../../components/header/Header";
import Mail from "../../components/mail/Mail";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
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
          <HotelTitle>Grand Hotel</HotelTitle>
          <HotelAddress>
            <FontAwesomeIcon icon={faLocationDot} />
            <span>105 Batman Street</span>
          </HotelAddress>
          <HotelDistance>Excellent location - 500m from center</HotelDistance>
          <HotelPriceHighlight>Book a stay here for $249</HotelPriceHighlight>
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
              <HotelTitle>Stay in the heart of City</HotelTitle>
              <HotelDescription>
                Located a 5-minute walk from St. Florian's Gate in Krakow, Tower
                Street Apartments has accommodations with air conditioning and
                free WiFi. The units come with hardwood floors and feature a
                fully equipped kitchenette with a microwave, a flat-screen TV,
                and a private bathroom with shower and a hairdryer. A fridge is
                also offered, as well as an electric tea pot and a coffee
                machine. Popular points of interest near the apartment include
                Cloth Hall, Main Market Square and Town Hall Tower. The nearest
                airport is John Paul II International Kraków–Balice, 16.1 km
                from Tower Street Apartments, and the property offers a paid
                airport shuttle service.
              </HotelDescription>
            </DetailText>
            <DetailPrice>
              <DetailPriceH1>Perfect for a 9-night stay!</DetailPriceH1>
              <DetailPriceSpan>
                Located in the real heart of Krakow, this property has an
                excellent location score of 9.8!
              </DetailPriceSpan>
              <DetailPriceH2>
                <b>$549</b> (2 nights)
              </DetailPriceH2>
              <DetailPriceBtn>Book Now!</DetailPriceBtn>
            </DetailPrice>
          </HotelDetailContainer>
        </HotelWrapper>
        <Mail />
      </HotelContainer>
    </div>
  );
};

export default Hotel;
