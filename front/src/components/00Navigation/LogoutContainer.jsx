import React from 'react';
import {useHistory} from 'react-router-dom';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import AuthContext from "../11Context/AuthContext";


import Logout from './Logout';

export default function LogoutContainer() {

    const { dispatch } = React.useContext(AuthContext);
    const history = useHistory();

    const handleLogout = e => {
        http
      .post(`${apiEndpoint}/logout`)
      .then(response => {
        dispatch({ 
          type: "LOGOUT"
        })
        history.push("/")
      })
      .catch(error => {
        //console.log("Error on logout", error);
      });        

    }

    return (
        <div>
            <Logout onLogout={handleLogout} />
        </div>
    )
}
