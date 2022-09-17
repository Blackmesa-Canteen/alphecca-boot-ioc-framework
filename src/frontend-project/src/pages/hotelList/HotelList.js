import { CustomerNavBar } from "../../components/NavBar";
import Header from "../../components/header/Header";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import { format } from "date-fns";
import { DateRange } from "react-date-range";
import SearchItem from "../../components/search/Search";
import useFetch from "../../hooks/useFetch";
import {
  ListContainer,
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
  const [postcode, setPostcode] = useState(location.state.postcode);
  const [dates, setDates] = useState(location.state.dates);
  const [openDate, setOpenDate] = useState(false);
  const [options, setOptions] = useState(location.state.options);

  const { data, loading, error } = useFetch(
    `http://localhost:8088/api/shared/hotel/search?currency=AUD&pageNum=1&pageSize=4&postCode=${postcode}&sortBy=1&sortOrder=0`
  );

  const ShowResult = () => {
    if (loading === true) {
      return <p>loading</p>;
    }
    if (data.length === 0) {
      return <p>no results</p>;
    } else {
      return (
        <>
          {data.map((item) => (
            <SearchItem item={item} key={item.id} />
          ))}
        </>
      );
    }
  };

  return (
    <div>
      <CustomerNavBar />
      <Header type="list" />
      <ListContainer>
        <ListWrapper>
          <ListResult>{ShowResult()}</ListResult>
        </ListWrapper>
      </ListContainer>
    </div>
  );
};

export default List;
