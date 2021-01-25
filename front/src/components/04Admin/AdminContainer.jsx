import React, { Component } from 'react';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import NavBar from '../00Navigation/NavBar';


export class AdminContainer extends Component {

//pvz:

// constructor(props) {
//     super(props);
//     this.state = {
//         data: null
//     }
// }
    // componentDidMount() {
    //     const currentId = this.props.match.params.id;
    //     this.setState({ currentId: currentId });

    //     if (currentId === "new") return;

    //     http.get(`${apiEndpoint}/api/congratulations/${currentId}`)
    //         .then((response) => {
    //             this.setState({ data: response.data });
    //          }).catch(error => {
    //             if(error.response && response.status === 404) alert("Kažkoks pranešimo tekstas susijęs su klaida; su get nelabai įmanoma tokia klaida")
    //
    //             return this.props.history.replace("/");
    //         });

    // }

    render() {
        return (
            <div>
                <NavBar/>
                <h5>Administratoriaus langas (čia bus naudotojų sąrašas ir naujo naudotojo įvedimo mygtukas)</h5>
                <p>Servisus siūlau kviesti per "httpService" ir "apiEndpoint" (užkomentuotas pavyzdys įdėtas Admin komponente), kad galėtume bet kada pakeisti axios arba url adresą, jei reiks. </p>
                <p>Pavadinimai gali būti keičiami, kad visiems būtų aiškūs. Aš juos iš Mosh tutorial paėmiau, kai mokiausi; 
                vėliau adresus reiks sutvarkyti ir papildyti. </p>
            </div>
        )
    }
}

export default AdminContainer
