import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import apiEndpoint from './../10Services/endpoint';

import '../../App.css';
import AuthContext from "../11Context/AuthContext";
import AdminNavBar from "../00Navigation/AdminNavBar";
import UserNavBar from "../00Navigation/UserNavBar";
import ManagerNavBar from "../00Navigation/ManagerNavBar";


const NotFound = () => {
    const location = useLocation();
    const { state } = React.useContext(AuthContext);

    return (
        <div> 
        {state.isAuthenticated && state.role === "ADMIN" && <AdminNavBar />}
        {state.isAuthenticated && state.role === "MANAGER" && <ManagerNavBar />}
        {state.isAuthenticated && state.role === "USER" && <UserNavBar />}
        <div className="container pt-5">
            <p className="ml-2">Puslapis adresu: {apiEndpoint}{location.pathname} nerastas</p>
            {/* <Link to="/home" className="btn btn-primary ml-2">Pradinis</Link> */}
        </div>
        </div>
    );
}

export default NotFound;