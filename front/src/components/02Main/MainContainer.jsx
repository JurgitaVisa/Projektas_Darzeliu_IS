import React from "react";

import '../../App.css';

// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faFilePdf } from "@fortawesome/free-solid-svg-icons";

import AuthContext from "../11Context/AuthContext";
import Admin from "../04Admin/AdminContainer";
import NotFound from "../03NotFound/NotFound";


export const MainContainer = () => {
  const currentUser = React.useContext(AuthContext);
  switch (currentUser.state.role){
    case "ADMIN": return (
      <div>
        <Admin />
      </div>); 
    case "MANAGER": return (
      <div>
        <h1>Darželių įvedimas ir sąrašas</h1>
        {/* <KindergartenContainer /> */}
      </div>); 
    case "USER": return (
      <div>
        <h1>Pateiktų prašymų puslapis</h1>
      </div>); 
    default: return (
      <div>
        <NotFound />
      </div>
    )
  }
};

export default MainContainer;
