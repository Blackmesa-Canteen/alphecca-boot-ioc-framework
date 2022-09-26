import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/Login";
import Signuppage from "./pages/Signup";
import HomePage from "./pages/home/Home";
import AdminLogin from "./pages/Admin/AdminLogin";
import HotelList from "./pages/hotelList/HotelList";
import Property from "./pages/hotel/Hotel";
import Customer from "./pages/customer/Customer";
import RoomDetails from "./pages/hotel/Hotel";
import HotelierHome from "./pages/Hotelier/HotelierHome";
import AdminHome from "./pages/Admin/AdminHome"
import HotelTransaction from "./pages/Hotelier/Transaction";
import AllHotels from "./pages/Admin/AllHotel";
import AllHoteliers from "./pages/Admin/AllHoteliers";
function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/adminLogin" element={<AdminLogin />} />
          <Route path="/signup" element={<Signuppage />} />
          <Route path="/hotels" element={<HotelList />} />
          <Route path="/hotels/:id" element={<RoomDetails />} />
          <Route path="/propertypage" element={<Property />} />
          <Route path="/:id" element={<HomePage />} />
          <Route path="/customer" element={<Customer />} />
          <Route path="/hotelier" element={<HotelierHome/>} />
          <Route path="/hotelTransaction/:id" element={<HotelTransaction/>}/>

          <Route path = "/admin" element={<AdminHome/>}/>
          <Route path = "/admin/hotels" element={<AllHotels/>}/>
          <Route path = "/admin/hoteliers" element={<AllHoteliers/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
