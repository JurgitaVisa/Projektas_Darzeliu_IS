import React from 'react';
import { useLocation, Link } from 'react-router-dom';

const NotFound = () => {
    const location = useLocation();

    return (
        <div>
            <p className="ml-2">Puslapis adresu: {location.pathname} nerastas</p>
            <Link to="/home" className="btn btn-primary ml-2">Pradinis</Link>
        </div>
    );
}

export default NotFound;