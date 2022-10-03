import { useState, useEffect } from "react";
import { BASE_URL } from "../../API/CommonApi";
import { HomeNavBar, CustomerNavBar } from "../../components/navbar/NavBar";
import Header from "../../components/header/Header";
import { useLocation } from "react-router-dom";
import SearchItem from "../../components/search/Search";
import useFetch from "../../API/CustomerApi";
import { ListContainer, ListWrapper, ListResult } from "./HotelListElements";

const List = () => {
  // scroll to the top of the page after every render
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const location = useLocation();
  const [postcode, setPostcode] = useState(location.state.postcode);
  const [hotelName, setHotelName] = useState(location.state.hotelName);
  const customer = localStorage.getItem("Customer");

  const GetUrl = () => {
    if (postcode === "") {
      return (
        BASE_URL +
        `/shared/hotel/search?currency=AUD&pageNum=1&pageSize=4&hotelName=${hotelName}&sortBy=1&sortOrder=0`
      );
    } else if (hotelName === "") {
      return (
        BASE_URL +
        `/shared/hotel/search?currency=AUD&pageNum=1&pageSize=4&postCode=${postcode}&sortBy=1&sortOrder=0`
      );
    }
  };

  const { data, loading } = useFetch(GetUrl());

  const ShowResult = () => {
    if (loading === true) {
      return <p>loading</p>;
    }
    if (data.length === 0) {
      if (postcode === "") {
        return <p>Sorry, Hotel {hotelName} is not available</p>;
      } else {
        return <p>Sorry, no hotels available in postcode {postcode} area</p>;
      }
    } else {
      return (
        <div>
          {data.map((item) => (
            <SearchItem item={item} key={item.id} />
          ))}
        </div>
      );
    }
  };
  return (
    <div>
      {customer === null ? <HomeNavBar /> : <CustomerNavBar />}
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
