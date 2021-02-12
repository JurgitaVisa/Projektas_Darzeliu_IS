import React from "react";
import { Switch, Route } from "react-router-dom";

import "./index.css";
import "./App.css";

import Login from "./components/01Login/LoginContainer";
import Main from "./components/02Main/MainContainer";
import NotFound from "./components/03NotFound/NotFound";
import Admin from "./components/04Admin/AdminContainer";
import UserListContainer from "./components/04Admin/UserListContainer";
import KindergartenContainer from './components/05Kindengarten/KindergartenContainer';
import AdminNavBar from "./components/00Navigation/AdminNavBar";
import UserNavBar from "./components/00Navigation/UserNavBar";
import ManagerNavBar from "./components/00Navigation/ManagerNavBar";

import AuthContext from "./components/11Context/AuthContext";
// import http from "./components/10Services/httpService";
// import apiEndpoint from "./components/10Services/endpoint";

var initState = {
  isAuthenticated: false,
  username: null,
  role: null,
};

const checkLogin = () => {

  if (sessionStorage.length > 0) {
    initState = {
      isAuthenticated: true,
      username: sessionStorage.getItem("username"),
      role: sessionStorage.getItem("role"),
    };

  } else
    initState = {
      isAuthenticated: false,
      username: null,
      role: null,
    };

  /* galima naudoti useEffect ir jame kviesti /api/loggedUser */

  // problema su kreipimusi į serverį yra ta, kad užklausa yra asincroninė ir kol grąžinami rezultatai, tolimesnis kodas dirba su isAuthenticated === false, nors realiai yra prisijungęs useris
  // http
  //   .get(`${apiEndpoint}/api/loggedUser`)
  //   .then((resp) => {
  //     console.log("user " + resp.data + " is logged in <- message from checkIfLoggedIn");
  //     initState.isAuthenticated = true;
  //     return true;
  //   })
  //   .catch((error) => {
  //     console.log("deja");
  //     initState.isAuthenticated = false;
  //     return false;
  //   });
};

const reducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      sessionStorage.setItem("username", action.payload.username);
      sessionStorage.setItem("role", action.payload.role);
      return {
        ...state,
        isAuthenticated: true,
        username: action.payload.username,
        role: action.payload.role,
      };
    case "LOGOUT":
      sessionStorage.clear();
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
  checkLogin();
  const [state, dispatch] = React.useReducer(reducer, initState);

  if (state.isAuthenticated){
    switch (state.role) {
      case "ADMIN":
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <AdminNavBar>
                <Switch>
                  <Route exact path="/" component={Admin} />
                  <Route path="/home" component={Admin} />
                  <Route path="/admin" component={Admin} />
                  <Route path="/naudotojai" component={UserListContainer} />
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
                  <Route exact path="/" component={Main} />{" "}
                  {/* TODO MainContainer yra laikinai. Vėliau, kai bus visi komponentai, jo nereikės*/}
                  <Route path="/home" component={Main} />{" "}
                  {/* TODO MainContainer yra laikinai. Vėliau, kai bus visi komponentai, jo nereikės*/}
                  <Route path="/darzeliai" component={KindergartenContainer} />
                  <Route path="*" component={NotFound} />
                </Switch>
              </ManagerNavBar>
            </div>
          </AuthContext.Provider>
        );
      case "USER":
        return (
          <AuthContext.Provider value={{ state, dispatch }}>
            <div className="container-fluid px-0">
              <UserNavBar>
                <Switch>
                  <Route exact path="/" component={Main} />{" "}
                  {/* TODO MainContainer yra laikinai. Vėliau, kai bus visi komponentai, jo nereikės*/}
                  <Route path="/home" component={Main} />{" "}
                  {/* TODO MainContainer yra laikinai. Vėliau, kai bus visi komponentai, jo nereikės*/}
                  <Route path="/prasymai" component={Main} />{" "}
                  {/* TODO MainContainer yra laikinai. Vėliau, kai bus visi komponentai, jo nereikės*/}
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

  } else return (
    <div>
    <AuthContext.Provider value={{ state, dispatch }}>
      <Login />
    </AuthContext.Provider>
    </div>
  )
}

export default App;
