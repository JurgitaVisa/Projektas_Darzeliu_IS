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
import http from "./components/10Services/httpService";
import apiEndpoint from "./components/10Services/endpoint";

var initState = {
  isAuthenticated: false,
  username: null,
  role: null,
};

const checkIfLoggedIn = () => {
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
  checkIfLoggedIn();
  const [state, dispatch] = React.useReducer(reducer, initState);

  return (
    <AuthContext.Provider value={{ state, dispatch }}>
      {/* TODO prieiga prie puslapių pagal roles. 
      TODO Atskiri NavBar'ai useriui, adminui ir švietimo specialistui*/}

      <div className="container-fluid px-0">
        <Switch>
          <Route
            exact
            path="/"
            render={() => state.isAuthenticated ? <Redirect to="/home" /> : <Login />
            }
          />
          <Route
            path="/home"
            render={() => state.isAuthenticated ? <Main /> : <Redirect to="/" />
            }
          />
          <Route
            path="/admin"
            render={() => state.isAuthenticated ? (state.role === "ADMIN" ? <Admin /> : <NotFound />) : <Redirect to="/" />}
          />
          <Route
            path="/naudotojai"
            render={() => state.isAuthenticated ? (state.role === "ADMIN" ? <UserListContainer /> : <NotFound />) : <Redirect to="/" />}
          />
          <Route
            path="*"
            render={() =>
              state.isAuthenticated ? <NotFound /> : <Redirect to="/" />
            }
          />
          <Route component={NotFound} />
        </Switch>
      </div>
    </AuthContext.Provider>
  );
}

export default App;
