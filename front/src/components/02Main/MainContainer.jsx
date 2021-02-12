import React from "react";

import '../../App.css';

// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faFilePdf } from "@fortawesome/free-solid-svg-icons";

import AdminNavBar from "../00Navigation/AdminNavBar";
import ManagerNavBar from "../00Navigation/ManagerNavBar";
import UserNavBar from "../00Navigation/UserNavBar";
import AuthContext from "../11Context/AuthContext";
import Admin from "../04Admin/AdminContainer";
import NotFound from "../03NotFound/NotFound";
import KindergartenContainer from "../05Kindengarten/KindergartenContainer";


export const MainContainer = () => {
  const currentUser = React.useContext(AuthContext);
  switch (currentUser.state.role){
    case "ADMIN": return (
      <div>
        <AdminNavBar />
        <Admin />
      </div>); 
    case "MANAGER": return (
      <div>
        <ManagerNavBar />
        <h1>Darželių įvedimas ir sąrašas</h1>
        {/* <KindergartenContainer /> */}
      </div>); 
    case "USER": return (
      <div>
        <UserNavBar />
        <h1>Pateiktų prašymų puslapis</h1>
      </div>); 
  }
};

export default MainContainer;
