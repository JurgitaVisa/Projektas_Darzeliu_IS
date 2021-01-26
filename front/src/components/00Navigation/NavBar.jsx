import React from 'react';
import { NavLink } from 'react-router-dom';

import LogoutContainer from './LogoutContainer'

function Navigation() {

    return (
        <div className="pt-3">
            <nav className="navbar navbar-expand-md navbar-light bg-light">
                <NavLink className="navbar-brand" to={"/home"}>Logo(home)</NavLink>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        
                        <li className="nav-item p-1">
                            <NavLink className="nav-link" to={"/admin"}>Naudotojų administravimas</NavLink>
                        </li>                        
                        

                        <LogoutContainer/>

                    </ul>

                </div>
            </nav>
        </div >

    );

}

export default Navigation;