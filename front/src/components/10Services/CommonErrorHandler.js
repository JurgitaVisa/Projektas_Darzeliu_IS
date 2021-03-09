import React from 'react';
import AuthContext from "../11Context/AuthContext";
import swal from "sweetalert";
import axios from 'axios';

const CommonErrorHandler = ({children}) => {
    const { dispatch } = React.useContext(AuthContext);
    
    React.useMemo(() => {
        axios.interceptors.response.use(response => response, async(error) => {
            const expectedError = error.response && error.response.status >= 400 && error.response.status < 500;
            console.log("CommonErrorHandler kalba")
            if (!expectedError) {
                console.log("Logging unexpected error", error);
                swal('Įvyko klaida, puslapis nurodytu adresu nepasiekiamas');
                dispatch({
                    type: "ERROR",
                    payload: null
                })
            } else if (error.response.status === 401) {
                swal('Neprisijungta')
                dispatch({
                    type: "ERROR",
                    payload: error.response.status
                })
                console.log("iš klaidų gaudyklės")
                console.log(error.response.status)
            } else if (error.response.status === 403) {
                swal('Prieiga uždrausta')
                dispatch({
                    type: "ERROR",
                    payload: error.response.status
                })
            }
        });
    }, [dispatch])
    return children;
}

export default CommonErrorHandler
