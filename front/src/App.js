import React from 'react';
import { Switch, Route } from 'react-router-dom';

import './index.css';
import './App.css';

import Login  from './components/01Login/LoginContainer';
import Main from './components/02Main/MainContainer';
import NotFound from './components/03NotFound/NotFound';
import Admin from './components/04Admin/AdminContainer';


function App() {
  return (
    <div className="container pt-5">       
        <Switch >
          <Route exact path="/" component={Login} />   
          <Route path="/home" component={Main} />      
          <Route path="/admin" component={Admin} />
          
          <Route path='*' component={NotFound} />
          <Route component={NotFound} />

        </Switch>
      </div>
  );
}

export default App;
