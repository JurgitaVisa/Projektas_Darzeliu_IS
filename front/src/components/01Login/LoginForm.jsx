import React, { useContext } from "react";
import UserContext from "../11Context/UserContext";

const LoginForm = ({
  username,
  password,
  loginError,
  onPassChange,
  onUsernameChange,
  onSubmit,
}) => {
  let { userCredentials } = useContext(UserContext);
  console.log(UserContext + " from LoginForm");
  // console.log(userCredentials + " from LoginForm");
  return (
    <div className="container">
      <div className="row d-flex justify-content-center">
        <form onSubmit={onSubmit}>
          <div className="form-group">
            <label htmlFor="username">Naudotojo vardas</label>
            <input
              type="text"
              className="form-control"
              id="username"
              value={username}
              onChange={onUsernameChange}
              required
              data-toggle="tooltip"
              data-placement="top"
              title="Įveskite naudotojo prisijungimo vardą"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Slaptažodis</label>
            <input
              type="password"
              className="form-control"
              id="password"
              value={password}
              onChange={onPassChange}
              required
              data-toggle="tooltip"
              data-placement="top"
              title="Įveskite naudotojo slaptažodį"
            />
          </div>

          <button type="submit" className="btn btn-primary">
            Prisijungti
          </button>
        </form>
      </div>
      <div className="row d-flex justify-content-center px-5 mt-3">
        <div className="col-3">
          {loginError && (
            <div className="alert alert-danger" role="alert">
              Neteisingi prisijungimo duomenys!
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
