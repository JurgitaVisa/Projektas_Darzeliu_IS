import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import NavBar from '../00Navigation/NavBar';
import UserListTable from './UserListTable';


export class UserListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            naudotojai: []
        }
    }
    componentDidMount() {
        http
            .get(`${apiEndpoint}/api/users/admin/allusers`)
            .then((response) => {
                this.setState({ naudotojai: response.data });

            }).catch(error => {
                console.log("Naudotojai container error", error.response);
                // if (error && error.response.status === 401)
                //     alert("Puslapis pasiekiamas tik administratoriaus teises turintiems naudotojams")
                this.props.history.replace("/home");
            }
            );

    }

    handleDelete = (item) => {
        const username = item.username;
        console.log("Trinti naudotoja", username);

        http
            .delete(`${apiEndpoint}/api/users/admin/delete/${username}`)
            .then((response) => {
                alert(response.data);
                http
                    .get(`${apiEndpoint}/api/users/admin/allusers`)
                    .then((response) => {
                        this.setState({ naudotojai: response.data });

                    });
            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) alert("Veiksmas neleidžiamas");

            }
            );

    }

    handleRestorePassword = (item) => {
        const username = item.username;
        console.log("Atstatyti slaptazodi naudotojui", username);

        http
            .put(`${apiEndpoint}/api/users/admin/password/${username}`)
            .then((response) => {
                alert(response.data);
            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) alert("Veiksmas neleidžiamas");
            }
            );

    }

    render() {
        return (
            <div >
                <NavBar />
                <div className="container">
                    <div className="row ">
                        <div className="col-12 pb-2">
                            <h5 className="h5">Sistemos naudotojų sąrašas </h5>

                        </div>
                    </div>

                    <Link to="/admin" className="btn btn-outline-primary my-2">Sukurti naują</Link>
                    <br />
                    <UserListTable 
                        naudotojai={this.state.naudotojai}
                        onDelete={this.handleDelete}
                        onRestorePassword={this.handleRestorePassword}
                    />
                </div>
            </div>
        )
    }
}

export default UserListContainer