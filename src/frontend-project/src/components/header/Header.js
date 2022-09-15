import {
  faBed,
  faCalendarDays,
  faPerson,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext, useState } from "react";
import "react-date-range/dist/styles.css"; // main css file
import "react-date-range/dist/theme/default.css"; // theme css file
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import { SearchContext } from "../../context/SearchContext";
import { AuthContext } from "../../context/AuthContext";
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
  HeaderContainerList,
  HeaderList,
  HeaderListItemActive,
} from "./HeaderElements";

const Header = ({ type }) => {
  const [destination, setDestination] = useState("");
  const [openDate, setOpenDate] = useState(false);
  const [openOptions, setOpenOptions] = useState(false);
  const [options, setOptions] = useState({
    adult: 1,
    children: 0,
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

  const handleSearch = () => {
    navigate("/hotels", { state: { destination, date, options } });
  };

  return (
    <HeaderWrap>
      {type === "list" ? (
        <div></div>
      ) : (
        <HeaderContainer>
          <h1>A lifetime of discounts? It's Genius.</h1>
          <HeaderP>
            Get rewarded for your travels – unlock instant savings of 10% or
            more with a free Lamabooking account
          </HeaderP>
          <HeaderBtn>Sign in / Sign up</HeaderBtn>
          <HeaderSearch>
            <HeaderSearchItem>
              <SearchIcon icon={faBed} />
              <HeaderSearchInput
                type="text"
                placeholder="Destination"
                onChange={(e) => setDestination(e.target.value)}
              />
            </HeaderSearchItem>
            <HeaderSearchItem>
              <SearchIcon icon={faCalendarDays} />
              <SearchSpan onClick={() => setOpenDate(!openDate)}>{`${format(
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
              <SearchSpan onClick={() => setOpenOptions(!openOptions)}>
                {`${options.adult} adult · ${options.children} children · ${options.room} room`}
              </SearchSpan>
              {openOptions && (
                <PeopleOptions>
                  <OptionItem>
                    <span>Adult</span>
                    <OptionCounter>
                      <OptionBtn
                        disabled={options.adult <= 1}
                        onClick={() => handleOption("adult", "d")}
                      >
                        -
                      </OptionBtn>
                      <span>{options.adult}</span>
                      <OptionBtn onClick={() => handleOption("adult", "i")}>
                        +
                      </OptionBtn>
                    </OptionCounter>
                  </OptionItem>
                  <OptionItem>
                    <span>Children</span>
                    <OptionCounter>
                      <OptionBtn
                        disabled={options.children <= 0}
                        onClick={() => handleOption("children", "d")}
                      >
                        -
                      </OptionBtn>
                      <span>{options.children}</span>
                      <OptionBtn onClick={() => handleOption("children", "i")}>
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
