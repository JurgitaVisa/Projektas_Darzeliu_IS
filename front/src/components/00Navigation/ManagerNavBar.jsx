import React from 'react';
import { NavLink } from 'react-router-dom';

import logo from '../../images/logo.png';
import '../../App.css';

import LogoutContainer from './LogoutContainer';

function Navigation(props) {
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

                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" id="navManagerKindergartenList" to={"/darzeliai"}>Darželių sąrašas</NavLink>
                            </li>

                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" id="navManagerKindergartenList" to={"/eile"}>Prašymų eilė</NavLink>
                            </li>

                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" id="navManagerApplicationQueue" to={"/statistika"}>Prašymų statistika</NavLink>
                            </li>
                            
                            <li className="nav-item mr-2">
                                <NavLink className="nav-link" id="navManagerMyAccount" to={"/profilis/atnaujinti"}>Mano paskyra</NavLink>
                            </li>

                            <li className="nav-item nav-item mr-2" id="navManagerLogout">
                                <LogoutContainer />
                            </li>

                        </ul>

                    </div>
                </div>
            </nav>
            <div>{props.children}</div>
        </div >

    );

}

export default Navigation;