import React from "react";

import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import LoginPage from "./pages/Login";
import Signuppage from "./pages/Signup";
import HomePage from "./pages/home/Home";
import AdminLogin from "./pages/Admin/AdminLogin";
import HotelList from "./pages/hotelList/HotelList";
import Property from "./pages/hotel/Hotel";
import HotelierHome from "./pages/Hotelier/HotelierHome";
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
          <Route path="/propertypage" element={<Property />} />
          <Route path="/hotelier" element={<HotelierHome/>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
