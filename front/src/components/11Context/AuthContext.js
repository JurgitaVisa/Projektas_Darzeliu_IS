import React from "react";

const AuthContext = React.createContext(null);

// Galima čia sudėti pradinį tikrinimą, kad kraunant kontekstą būtų iškviečiama get loggeduser ir tada užkrautų prisiloginusio userio info
// kiti komponentai nesikrautų, kol AuthContext === {}

export default AuthContext;
