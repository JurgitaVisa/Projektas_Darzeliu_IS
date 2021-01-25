import React from 'react';
import { useHistory } from 'react-router';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import Logout from './Logout';

export default function LogoutContainer() {

    const history = useHistory();

    const handleLogout = e => {
        http
      .post(`${apiEndpoint}/logout`)
      .then(response => {
        history.push("/");
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
