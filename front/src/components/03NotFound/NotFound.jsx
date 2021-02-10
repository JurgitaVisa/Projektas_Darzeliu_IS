import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import apiEndpoint from './../10Services/endpoint';

import '../../App.css';

import NavBar from '../00Navigation/NavBar';


const NotFound = () => {
    const location = useLocation();

    return (
        <div>
        <NavBar />
        <div className="container pt-5">
            <p className="ml-2">Puslapis adresu: {apiEndpoint}{location.pathname} nerastas</p>
            <Link to="/home" className="btn btn-primary ml-2">Pradinis</Link>
        </div>
        </div>
    );
}

export default NotFound;