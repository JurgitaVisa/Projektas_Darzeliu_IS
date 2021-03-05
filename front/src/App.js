import React, { useEffect, useReducer } from "react";
import { Switch, Route } from "react-router-dom";

import "./index.css";
import "./App.css";
import Spinner from "./components/08CommonComponents/Spinner"

import Login from "./components/01Login/LoginContainer";
import NotFound from "./components/03NotFound/NotFound";
import Admin from "./components/04Admin/AdminContainer";
import UserListContainer from "./components/04Admin/UserListContainer";
import KindergartenContainer from './components/05Kindengarten/KindergartenContainer';
import UpdateProfileFormContainer from './components/06UpdateProfile/UpdateProfileFormContainer';
import CreateApplicationFormContainer from './components/07Application/CreateApplicationFormContainer';

import AdminNavBar from "./components/00Navigation/AdminNavBar";
import UserNavBar from "./components/00Navigation/UserNavBar";
import ManagerNavBar from "./components/00Navigation/ManagerNavBar";

import AuthContext from "./components/11Context/AuthContext";
import http from "./components/10Services/httpService";
import apiEndpoint from "./components/10Services/endpoint";
import { UserHomeContainer } from './components/02Main/UserHomeContainer';
import { KindergartenStatContainer } from './components/09Statistics/KindergartenStatContainer';
import { QueueContainer } from "./components/12Queue/QueueContainer";

var initState = {
  isAuthenticated: null,
  username: null,
  role: null,
};

const reducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      return {
        ...state,
        isAuthenticated: true,
        username: action.payload.username,
        role: action.payload.role,
      };
    case "LOGOUT":
      return {
        ...state,
        isAuthenticated: false,
        username: null,
        role: null,
      };
    default:
      return state;
  }
};

function App() {
  const [state, dispatch] = useReducer(reducer, initState);
  console.log(state);

  useEffect(() => {
    if (state.isAuthenticated === null) {
      console.log("pradedam nuskaityti login statusą")
      http
        .get(`${apiEndpoint}/api/loggedUserRole`)
        .then((resp) => {
          console.log("user " + resp.data + " is logged in");
          dispatch({
            type: "LOGIN",
            payload: { role: resp.data },
          });
        })
        .catch((error) => {
          dispatch({
            type: "LOGOUT",
            payload: null,
          });
          console.log("deja");
        });
    }
  });

  if (state.isAuthenticated){
    switch (state.role) {
      case "ADMIN":
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <AdminNavBar>
                <Switch> 
                  <Route exact path="/" component={Admin} />
                  <Route exact path="/home" component={Admin} />
                  <Route exact path="/admin" component={Admin} />
                  <Route exact path="/statistika" component={KindergartenStatContainer} />
                  <Route exact path="/naudotojai" component={UserListContainer} />
                  <Route exact path="/profilis/atnaujinti" component={UpdateProfileFormContainer} />
                  <Route path="*" component={NotFound} />
                </Switch>
              </AdminNavBar>
            </div>
          </AuthContext.Provider>
        );
      case "MANAGER":
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <ManagerNavBar>
                <Switch>
                  <Route exact path="/" component={KindergartenContainer} />
                  <Route exact path="/home" component={KindergartenContainer} />
                  <Route exact path="/statistika" component={KindergartenStatContainer} />
                  <Route exact path="/darzeliai" component={KindergartenContainer} />
                  <Route exact path="/eile" component={QueueContainer} />
                  <Route exact path="/profilis/atnaujinti" component={UpdateProfileFormContainer} />
                  <Route path="*" component={NotFound} />                  
                </Switch>               
              </ManagerNavBar >
            </div>            
          </AuthContext.Provider>
        );
      case "USER":
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <UserNavBar>
                <Switch>
                  <Route exact path="/" component={UserHomeContainer} />
                  <Route exact path="/home" component={UserHomeContainer} />
                  <Route exact path="/prasymai" component={UserHomeContainer} />
                  <Route exact path="/statistika" component={KindergartenStatContainer} />
                  <Route exact path="/prasymai/naujas" component={CreateApplicationFormContainer} />
                  <Route exact path="/profilis/atnaujinti" component={UpdateProfileFormContainer} />
                  <Route path="*" component={NotFound} />
                </Switch>
              </UserNavBar>
            </div>
          </AuthContext.Provider>
        );
      default:
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <NotFound />
            </div>
          </AuthContext.Provider>
        );
    }
  } else if (state.isAuthenticated === false)
    return (
      <div>
        <AuthContext.Provider value={{ state, dispatch }}>
          <Login />
        </AuthContext.Provider>
      </div>
    );
    else return (<Spinner />);  
}

export default App;
