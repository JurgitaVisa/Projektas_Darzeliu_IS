import React from "react";

import '../../App.css';

// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faFilePdf } from "@fortawesome/free-solid-svg-icons";

import AuthContext from "../11Context/AuthContext";
import Admin from "../04Admin/AdminContainer";
import NotFound from "../03NotFound/NotFound";
import KindergartenContainer from "../05Kindengarten/KindergartenContainer";


export const MainContainer = () => {
  const currentUser = React.useContext(AuthContext);
  switch (currentUser.state.role){
    case "ADMIN": return (
      <div className="mt-3">
        <Admin />
      </div>); 
    case "MANAGER": return (
      <div className="mt-3">
        <KindergartenContainer />
      </div>); 
    case "USER": return (
      <div className="mt-3">
        <h1>Pateiktų prašymų puslapis</h1>
      </div>); 
    default: return (
      <div className="mt-3">
        <NotFound />
      </div>
    )
  }
};

export default MainContainer;
