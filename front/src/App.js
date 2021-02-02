import React from "react";
import { Switch, Route } from "react-router-dom";

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
  token: null,
};

const reducer = (state, action) => {
  switch (action.type){
    case "LOGIN":
      localStorage.setItem("username", action.payload.username);
      localStorage.setItem("token", action.payload.token);
      return{
        ...state,
        isAuthenticated: true,
        username: action.payload.username,
        role: action.payload.role,
        token: action.payload.token 
      };
    case "LOGOUT":
      localStorage.clear();
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
    const [state, distpatch] = React.useReducer(reducer, initState);
  return (
    <AuthContext.Provider value={{state, distpatch}}>
      <div className="container-fluid px-0">
        <Switch>
          <Route exact path="/" component={Login} />
          <Route path="/home" component={Main} />
          <Route path="/admin" component={Admin} />
          <Route path="/naudotojai" component={UserListContainer} />
          <Route path="*" component={NotFound} />
          <Route component={NotFound} />
        </Switch>
      </div>
    </AuthContext.Provider>
  );
}

export default App;
