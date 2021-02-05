import React from "react";

// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faFilePdf } from "@fortawesome/free-solid-svg-icons";

import NavBar from "../00Navigation/NavBar";
import AuthContext from "../11Context/AuthContext";


export const MainContainer = () => {
    const currentUser = React.useContext(AuthContext);

  return (
    <div>
      <NavBar />
      <div className="container">
        <h5 className="h5 pb-3">Sveiki prisijungę prie darželių IS</h5>
        <h6 className="h6 pb-3">
          Prisijungęs naudotojas:{" "}
          <span className="text-primary">{currentUser.state.username} </span>
        </h6>

        
        <br />
        <br />
        {/* <FontAwesomeIcon icon={faFilePdf} size="5x" className="text-primary" /> */}
        {/* <p>Atsisiųsti naudotojo instrukciją</p> */}
      </div>
    </div>
  );
};

export default MainContainer;
