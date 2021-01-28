import React from 'react';

const LoginForm = (({ username, password, loginError, onPassChange, onUsernameChange, onSubmit }) => {

    return (

        <div className="row d-flex justify-content-center">

            <form onSubmit={onSubmit}>
                <div className="form-group">
                    <label htmlFor="username">
                        Naudotojo vardas
                    </label>
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
                    <label htmlFor="password">
                        Slaptažodis
                    </label>
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

                <button type="submit" className="btn btn-primary">Prisijungti</button>

                {/* <input className="form-control" type="text" value={username} onChange={onUsernameChange} />
                <input type="password" value={password} onChange={onPassChange} />
                <input type="submit" /> */}

            </form>

            {loginError && <div className="alert alert-danger" role="alert">
                Neteisingi prisijungimo duomenys!
            </div>
            }
        </div>


    );
});

export default LoginForm;