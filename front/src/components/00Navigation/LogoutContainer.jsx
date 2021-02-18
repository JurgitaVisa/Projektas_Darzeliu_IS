import React from 'react';
import { useHistory } from 'react-router';

import '../../App.css';

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
        console.log("atsijungimas")
        dispatch({        // svarbu eiliškumas - pirma dispatch: išvalo kontekstą, ištrina sessionStorage, isAutthenticated -> false
          type: "LOGOUT",
          payload: null
        })
        history.push("/");  // tada tik history.push("/") ir kraunamas pradinis puslapis jau atsijungus naudotojui
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
