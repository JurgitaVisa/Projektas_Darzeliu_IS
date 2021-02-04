import React from 'react';
import { useHistory } from 'react-router';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import AuthContext from "../11Context/AuthContext";


import Logout from './Logout';

export default function LogoutContainer() {

    const history = useHistory();
    const { dispatch } = React.useContext(AuthContext);

    const handleLogout = e => {
        http
      .post(`${apiEndpoint}/logout`)
      .then(response => {
        history.push("/");
        console.log("atsijungimas")
        dispatch({
          type: "LOGOUT",
          payload: null
        })
      })
      .catch(error => {
        console.log("Error on logout", error);
      });        

    }

    return (
        <div>
            <Logout onLogout={handleLogout} />
        </div>
    )
}
