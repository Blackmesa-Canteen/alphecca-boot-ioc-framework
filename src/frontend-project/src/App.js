import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/Login"
import Signuppage from "./pages/Signup";
function App() {
  return (
    <div>
       <Router>
        <Switch>
          
          <Route path="/login" component={LoginPage} exact />
          <Route path ="/signup" component={Signuppage} exact/>

        </Switch>

      </Router>

    </div>
  );
}

export default App;
