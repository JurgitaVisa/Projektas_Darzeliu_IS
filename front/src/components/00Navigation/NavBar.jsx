import React from 'react';
import { NavLink } from 'react-router-dom';

import logo from '../../images/logo.png';
import '../../App.css';

import LogoutContainer from './LogoutContainer';
import AuthContext from "../11Context/AuthContext";


function Navigation() {
    const currentUser = React.useContext(AuthContext);
    return (
        <div className="pb-4" >
            <nav className="navbar navbar-expand-md py-4 navbar-light bg-light">

                <div className="container">

                    <NavLink className="navbar-brand" to={"/home"}>
                        <img className="nav-img" src={logo} alt="logotipas" loading="lazy" />
                    </NavLink>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav ml-auto ">
                        {currentUser.state.role==="ADMIN" && ( 
                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" to={"/admin"}>Naudotojų administravimas</NavLink>
                            </li>)}

                            {currentUser.state.role==="MANAGER" && ( 
                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" to={"/darzeliai"}>Darželių sąrašas</NavLink>
                            </li>)}

                            {/* <li className="nav-item nav-item mr-4">
                                <NavLink className="nav-link" to={"/naudotojai"}>Naudotojų sąrašas</NavLink>
                            </li> */}

                            <li className="nav-item nav-item mr-2">
                                <LogoutContainer />
                            </li>

                        </ul>

                    </div>
                </div>
            </nav>
        </div >

    );

}

export default Navigation;