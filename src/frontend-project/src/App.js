import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/Login"
import Signuppage from "./pages/Signup";
import HomePage from "./pages/Home";
import AdminLogin from "./pages/AdminLogin"
function App() {
  return (
    <div>
       <Router>
        <Switch>
          <Route path="/" component={HomePage} exact/>
          <Route path="/login" component={LoginPage} exact />
          <Route path="/adminLogin" component={AdminLogin} exact/>
          <Route path ="/signup" component={Signuppage} exact/>

        </Switch>

      </Router>

    </div>
  );
}

export default App;
