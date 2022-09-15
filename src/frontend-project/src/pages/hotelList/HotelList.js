import Navbar from "../../components/NavBar";
import Header from "../../components/header/Header";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import { format } from "date-fns";
import { DateRange } from "react-date-range";
import SearchItem from "../../components/search/Search";
import useFetch from "../../hooks/useFetch";
import { useNavigate } from "react-router-dom";
import {
  ListConatiner,
  ListItem,
  ListItemInput,
  ListItemLabel,
  ListOptions,
  ListOptionSpan,
  ListSearch,
  ListTitle,
  ListWrapper,
  ListOptionItem,
  ListOptionInput,
  ListResult,
  ListSearchBtn,
} from "./HotelListElements";

const List = () => {
  const location = useLocation();
  const [destination, setDestination] = useState(location.state.destination);
  const [date, setDate] = useState(location.state.date);
  const [openDate, setOpenDate] = useState(false);
  const [options, setOptions] = useState(location.state.options);

  const navigate = useNavigate();
  const Click = () => {
    navigate("/propertypage");
  };

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <ListConatiner>
        <ListWrapper>
          <ListSearch>
            <ListTitle>Search</ListTitle>
            <ListItem>
              <ListItemLabel>Destination</ListItemLabel>
              <ListItemInput placeholder={destination} type="text" />
            </ListItem>
            <ListItem>
              <ListItemLabel>Check-in Date</ListItemLabel>
              <ListOptionSpan onClick={() => setOpenDate(!openDate)}>{`${format(
                date[0].startDate,
                "MM/dd/yyyy"
              )} to ${format(date[0].endDate, "MM/dd/yyyy")}`}</ListOptionSpan>
              {openDate && (
                <DateRange
                  onChange={(item) => setDate([item.selection])}
                  minDate={new Date()}
                  ranges={date}
                />
              )}
            </ListItem>
            <ListItem>
              <ListItemLabel>Options</ListItemLabel>
              <ListOptions>
                <ListOptionItem>
                  <span>
                    Min price <small>per night</small>
                  </span>
                  <ListOptionInput type="number" />
                </ListOptionItem>
                <ListOptionItem>
                  <span>
                    Max price <small>per night</small>
                  </span>
                  <ListOptionInput type="number" />
                </ListOptionItem>
                <ListOptionItem>
                  <span>Adult</span>
                  <ListOptionInput
                    type="number"
                    min={1}
                    placeholder={options.adult}
                  />
                </ListOptionItem>
                <ListOptionItem>
                  <span>Children</span>
                  <ListOptionInput
                    type="number"
                    min={0}
                    placeholder={options.children}
                  />
                </ListOptionItem>
                <ListOptionItem>
                  <span>Room</span>
                  <ListOptionInput
                    type="number"
                    min={1}
                    placeholder={options.room}
                  />
                </ListOptionItem>
              </ListOptions>
            </ListItem>
            <ListSearchBtn>Search</ListSearchBtn>
          </ListSearch>
          <ListResult>
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
          </ListResult>
          <button onClick={Click}>Click Me!</button>
        </ListWrapper>
      </ListConatiner>
    </div>
  );
};

export default List;
