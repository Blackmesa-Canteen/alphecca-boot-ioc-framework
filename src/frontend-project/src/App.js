import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import LoginPage from "./pages/Login";
import Signuppage from "./pages/Signup";
import HomePage from "./pages/home/Home";
import AdminLogin from "./pages/AdminLogin";
function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/adminLogin" element={<AdminLogin />} />
          <Route path="/signup" element={<Signuppage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
