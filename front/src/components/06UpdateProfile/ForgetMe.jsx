import React from 'react';
import { useHistory } from 'react-router-dom';
import swal from 'sweetalert';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import AuthContext from "../11Context/AuthContext";

export default function ForgetMe() {

    const { dispatch } = React.useContext(AuthContext);
    const history = useHistory();

    const handleForgetMe = () => {

        swal({
            text: "DĖMESIO! Šio veiksmo negalėsite atšaukti!\n\nPatvirtinus veiksmą, bus ištrinta Jūsų paskyra ir prašymai.\nAr tikrai norite ištrinti savo paskyrą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {

                http.delete(`${apiEndpoint}/api/users/user/deletemydata`)
                    .then(() => {
                        http.post(`${apiEndpoint}/logout`)
                            .then(() => {
                                swal({
                                    text: "Ištrinta sėkmingai",
                                    button: "Gerai",
                                });
                            })
                    }).then(() => {
                        dispatch({
                            type: "LOGOUT"
                        })
                        history.push("/")
                    }).catch(() => {});
            }
        })
    }

    return (

        <button onClick={handleForgetMe} id="btnFrogetMeAndLogout" className="btn btn-outline-danger " >Ištrinti mano paskyrą</button>

    )

}

