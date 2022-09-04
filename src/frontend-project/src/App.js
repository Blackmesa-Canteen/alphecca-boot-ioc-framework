import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";

function App() {
  return (
    <React.Fragment>
      <header></header>
      <main>
        <Routes>
          <Route path="/home" element={<Home />} />
        </Routes>
      </main>
    </React.Fragment>
  );
}

export default App;
