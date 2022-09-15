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
  const [dates, setDates] = useState(location.state.dates);
  const [openDate, setOpenDate] = useState(false);
  const [options, setOptions] = useState(location.state.options);
  const [min, setMin] = useState(undefined);
  const [max, setMax] = useState(undefined);

  const { data, loading, error, reFetch } = useFetch(
    `/hotels?city=${destination}&min=${min || 0}&max=${max || 999}`
  );

  const handleClick = () => {
    reFetch();
  };

  const navigate = useNavigate();
  const Click = () => {
    navigate("/propertypage");
  };

  return (
    <div>
      <Navbar />
      <Header />
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
                dates[0].startDate,
                "MM/dd/yyyy"
              )} to ${format(dates[0].endDate, "MM/dd/yyyy")}`}</ListOptionSpan>
              {openDate && (
                <DateRange
                  onChange={(item) => setDates([item.selection])}
                  minDate={new Date()}
                  ranges={dates}
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
                  <ListOptionInput
                    type="number"
                    onChange={(e) => setMin(e.target.value)}
                  />
                </ListOptionItem>
                <ListOptionItem>
                  <span>
                    Max price <small>per night</small>
                  </span>
                  <ListOptionInput
                    type="number"
                    onChange={(e) => setMax(e.target.value)}
                  />
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
            <ListSearchBtn onClick={handleClick}>Search</ListSearchBtn>
          </ListSearch>
          <ListResult>
            {loading ? (
              "loading"
            ) : (
              <>
                {data.map((item) => (
                  <SearchItem />
                ))}
              </>
            )}
          </ListResult>
          <button onClick={Click}>Click Me!</button>
        </ListWrapper>
      </ListConatiner>
    </div>
  );
};

export default List;
