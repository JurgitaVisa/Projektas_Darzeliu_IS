import React, { Component } from 'react';

// import http from '../10Services/httpService';
// import apiEndpoint from '../10Services/endpoint';

import NavBar from '../00Navigation/NavBar';
import AdminCreateUser from '../04Admin/AdminCreateUser';

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
                {/*<NavBar/>*/}
               
                <AdminCreateUser/>
            </div>
        )
    }
}

export default AdminContainer
