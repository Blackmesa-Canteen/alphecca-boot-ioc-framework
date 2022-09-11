import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import RolePage from "./pages/Login"
import Signuppage from "./pages/Signup";
import HomePage from "./pages/Home";
import AdminLogin from "./pages/AdminLogin"
import HotelierHome from "./pages/Hotelier/HotelierHome";
function App() {
  return (
    <div>
       <Router>
        <Switch>
          <Route path="/" component={HomePage} exact/>
          <Route path="/login" component={RolePage} exact />
          <Route path="/adminLogin" component={AdminLogin} exact/>
          <Route path ="/signup" component={Signuppage} exact/>

          <Route path="/hotelier" component={HotelierHome} exact/>

        </Switch>

      </Router>

    </div>
  );
}

export default App;
