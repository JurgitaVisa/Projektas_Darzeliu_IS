import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import reportWebVitals from "./reportWebVitals";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

import "./index.css";
import App from "./App";

document.title =
  "Vilniaus miesto savivaldybės vaikų darželių informacinė sistema";

ReactDOM.render(
  <React.StrictMode>
      <BrowserRouter basename={process.env.PUBLIC_URL}>
        <Switch>
          <Route path="*" component={App} />
        </Switch>
      </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
