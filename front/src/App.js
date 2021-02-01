import React from "react";
import { Switch, Route } from "react-router-dom";

import "./index.css";
import "./App.css";

import Login from "./components/01Login/LoginContainer";
import Main from "./components/02Main/MainContainer";
import NotFound from "./components/03NotFound/NotFound";
import Admin from "./components/04Admin/AdminContainer";
import UserContext from "./components/11Context/UserContext";

export class App extends React.Component {
  constructor(props) {
    super(props);
    this.updateUserName = () => {
      this.setState(state => ({
        userName: state.userName
      }));
    };
    this.state = {
      userName: "App.constructor",
      updateUserName: this.updateUserName,
    }
  }

  render() {
    console.log(this.state)
    return (
      <UserContext.Provider value={this.state}>
        <div className="container pt-5">
          <Switch>
            <Route exact path="/" component={Login} />
            <Route path="/home" component={Main} />
            <Route path="/admin" component={Admin} />

            <Route path="*" component={NotFound} />
            <Route component={NotFound} />
          </Switch>
        </div>
      </UserContext.Provider>
    );
  }
}

export default App;
