import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";

import "./index.css";
import "./App.css";

import Login from "./components/01Login/LoginContainer";
import Main from "./components/02Main/MainContainer";
import NotFound from "./components/03NotFound/NotFound";
import Admin from "./components/04Admin/AdminContainer";
import UserListContainer from "./components/04Admin/UserListContainer";
import AuthContext from "./components/11Context/AuthContext";

const initState = {
  isAuthenticated: false,
  username: null,
  role: null,
};

const checkIfLoggedIn = (props) => {
  if (sessionStorage.length > 0) {
    console.log(sessionStorage.getItem("username"));
    console.log("how many entries: " + sessionStorage.length);
    console.log(props);
  }
}

const reducer = (state, action) => {
  switch (action.type){
    case "LOGIN":
      sessionStorage.setItem("username", action.payload.username);
      sessionStorage.setItem("role", action.payload.role);
      return{
        ...state,
        isAuthenticated: true,
        username: action.payload.username,
        role: action.payload.role,
      };
    case "LOGOUT":
      sessionStorage.clear();
      return{
        ...state,
        isAuthenticated: false,
        username: null,
        role: null
      };
    default:
      return state;
  }
};

function App() {
    const [state, dispatch] = React.useReducer(reducer, initState);
    checkIfLoggedIn(state);
  return (
    <AuthContext.Provider value={{state, dispatch}}>
      <div className="container-fluid px-0">
        <Switch>
          {/* <Route exact path="/" component={Login} /> */}
          <Route exact path="/" render={() => state.isAuthenticated ? <Redirect to="/home" /> : <Login />} />
          <Route path="/home" render={() => state.isAuthenticated ? <Main /> : <Redirect to="/" />} />
          {/* <Route path="/home" component={Main} /> */}
          <Route path="/admin" component={Admin} />
          <Route path="/naudotojai" component={UserListContainer} />
          <Route path="*" component={NotFound} />
          <Route component={NotFound} />
        </Switch>
      </div>
      {!state.isAuthenticated ? <Redirect to="/" />: <Redirect to="/home" />}
    </AuthContext.Provider>
  );
}

export default App;
