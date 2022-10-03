import {
  faBed,
  faCalendarDays,
  faPerson,
  faSignsPost,
} from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import "react-date-range/dist/styles.css"; // main css file
import "react-date-range/dist/theme/default.css"; // theme css file
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import {
  HeaderWrap,
  HeaderContainer,
  HeaderP,
  HeaderBtn,
  HeaderSearch,
  HeaderSearchItem,
  HeaderSearchInput,
  SearchSpan,
  SearchIcon,
  HeaderDate,
  PeopleOptions,
  OptionItem,
  OptionCounter,
  OptionBtn,
} from "./HeaderElements";

const Header = (props) => {
  const [hotelName, setHotelName] = useState("");
  const [postcode, setPostCode] = useState("");
  const [openDate, setOpenDate] = useState(false);
  const [openOptions, setOpenOptions] = useState(false);
  const [options, setOptions] = useState({
    people: 1,
    room: 1,
  });
  const [date, setDates] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ]);

  const navigate = useNavigate();

  const handleOption = (name, operation) => {
    setOptions((prev) => {
      return {
        ...prev,
        [name]: operation === "i" ? options[name] + 1 : options[name] - 1,
      };
    });
  };

  const handleDateClick = () => {
    setOpenDate(!openDate);
    if (openOptions === true) {
      setOpenOptions(false);
    }
  };

  const handleOptionClick = () => {
    setOpenOptions(!openOptions);
    if (openDate === true) {
      setOpenDate(false);
    }
  };

  const handleSearch = () => {
    if (postcode === "" && hotelName === "") {
      alert("please enter a postcode or hotel name");
    } else {
      navigate("/hotels", { state: { hotelName, postcode, date, options } });
    }
  };

  const handleClick = () => {
    navigate("/login");
  };

  return (
    <HeaderWrap>
      {props.type === "list" ? (
        <div></div>
      ) : (
        <HeaderContainer>
          <h1>Alphecca Provides What You Want</h1>
          <HeaderP>
            Get ready for thousands of amazing hotels here - unlock premium
            services with a free Alphecca account
          </HeaderP>
          {typeof props.item === "undefined" && (
            <HeaderBtn onClick={handleClick}>Sign in / Sign up</HeaderBtn>
          )}
          <HeaderSearch>
            <HeaderSearchItem>
              <SearchIcon icon={faBed} />
              <HeaderSearchInput
                disabled={postcode !== ""}
                type="text"
                placeholder="Search by Hotel Name"
                onChange={(e) => setHotelName(e.target.value)}
              />
            </HeaderSearchItem>
            <HeaderSearchItem>
              <SearchIcon icon={faSignsPost} />
              <HeaderSearchInput
                disabled={hotelName !== ""}
                type="text"
                placeholder="Search by Postcode"
                onChange={(e) => setPostCode(e.target.value)}
              />
            </HeaderSearchItem>
            <HeaderSearchItem>
              <SearchIcon icon={faCalendarDays} />
              <SearchSpan onClick={handleDateClick}>{`${format(
                date[0].startDate,
                "MM/dd/yyyy"
              )} to ${format(date[0].endDate, "MM/dd/yyyy")}`}</SearchSpan>
              {openDate && (
                <HeaderDate
                  editableDateInputs={true}
                  onChange={(item) => setDates([item.selection])}
                  moveRangeOnFirstSelection={false}
                  ranges={date}
                  minDate={new Date()}
                />
              )}
            </HeaderSearchItem>
            <HeaderSearchItem>
              <SearchIcon icon={faPerson} />
              <SearchSpan onClick={handleOptionClick}>
                {`${options.people} people · ${options.room} room`}
              </SearchSpan>
              {openOptions && (
                <PeopleOptions>
                  <OptionItem>
                    <span>People</span>
                    <OptionCounter>
                      <OptionBtn
                        disabled={options.people <= 1}
                        onClick={() => handleOption("people", "d")}
                      >
                        -
                      </OptionBtn>
                      <span>{options.people}</span>
                      <OptionBtn onClick={() => handleOption("people", "i")}>
                        +
                      </OptionBtn>
                    </OptionCounter>
                  </OptionItem>
                  <OptionItem>
                    <span>Room</span>
                    <OptionCounter>
                      <OptionBtn
                        disabled={options.room <= 1}
                        onClick={() => handleOption("room", "d")}
                      >
                        -
                      </OptionBtn>
                      <span>{options.room}</span>
                      <OptionBtn onClick={() => handleOption("room", "i")}>
                        +
                      </OptionBtn>
                    </OptionCounter>
                  </OptionItem>
                </PeopleOptions>
              )}
            </HeaderSearchItem>
            <HeaderSearchItem>
              <HeaderBtn onClick={handleSearch}>Search</HeaderBtn>
            </HeaderSearchItem>
          </HeaderSearch>
        </HeaderContainer>
      )}
    </HeaderWrap>
  );
};

export default Header;
