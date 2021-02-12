import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import apiEndpoint from './../10Services/endpoint';

import '../../App.css';

const NotFound = () => {
    const location = useLocation();

    return (
        <div> 
        <div className="container pt-5">
            <p className="ml-2">Puslapis adresu: {apiEndpoint}{location.pathname} nerastas</p>
            <Link to="/home" className="btn btn-primary ml-2">Pradinis</Link>
        </div>
        </div>
    );
}

export default NotFound;