import React from 'react';
import { useHistory } from 'react-router';
import AuthContext from "../11Context/AuthContext";
import swal from "sweetalert";
import axios from 'axios';

const CommonErrorHandler = () => {
    const history = useHistory();
    const { dispatch } = React.useContext(AuthContext);

    const loginInstance = axios.create();

    loginInstance.interceptors.response.use(response => response, async(error) => {
        const expectedError = error.response && error.response.status >= 400 && error.response.status < 500;
        if (!expectedError) {
            console.log("Logging unexpected error", error);
            swal('Įvyko klaida, puslapis nurodytu adresu nepasiekiamas');
            dispatch({
                type: "ERROR",
                payload: null
            })
    }});
    
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
                swal('Nepasiekiamas')
                dispatch({
                    type: "ERROR",
                    payload: error.response.status
                })
            }
            if (history.location.pathname !== "/login") history.push("/login")
        });
    }, [dispatch, history])
    // return children;
}

export default CommonErrorHandler
