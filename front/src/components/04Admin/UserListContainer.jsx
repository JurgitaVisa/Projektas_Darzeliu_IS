import React, { Component } from 'react';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import UserListTable from './UserListTable';
import Pagination from './../08CommonComponents/Pagination';

export class UserListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            naudotojai: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            passwordResetRequests: []
        }
    }
    componentDidMount() {

        this.getUserInfo(this.state.currentPage);
    }

    getUserInfo(currentPage) {

        const { pageSize } = this.state;
        currentPage -= 1;

        var uri = `${apiEndpoint}/api/users/admin/allusers?page=${currentPage}&size=${pageSize}`;

        http.get(`${apiEndpoint}/passwordresetrequests/getAllRequests`)
        .then((response) => {
            this.setState({
                ...this.state,
                passwordResetRequests: response.data,
            })
        })

        http
            .get(uri)
            .then((response) => {

                this.setState({
                    naudotojai: this.mapToViewModel(response.data.content, this.state.passwordResetRequests),
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

                

            }).catch(error => {
                console.log("User container error", error.response);
                if (error && error.response.status === 401)

                    swal({
                        text: "Puslapis pasiekiamas tik teises turintiems naudotojams",
                        button: "Gerai"
                    });
                // this.props.history.replace("/home");
            }
            );
    }

    checkIfUserIsRequestingPassword(UID, passList) {
        return passList.some(element => element.userId === UID);
    }

    mapToViewModel(data, passList) {
        const naudotojai = data.map((user)=> ({
            id: user.userId,
            username: user.username,
            role: user.role,
            isRequestingPasswordReset: this.checkIfUserIsRequestingPassword(user.userId, passList),
        }));

        return naudotojai;
    };

    handleDelete = (item) => {
        const username = item.username;
        console.log("Trinti naudotoja", username);

        swal({
            text: "Ar tikrai norite ištrinti naudotoją?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const { currentPage, numberOfElements } = this.state;
                const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;

                http
                    .delete(`${apiEndpoint}/api/users/admin/delete/${username}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });

                        this.getUserInfo(page);

                    }).catch(error => {
                        if (error && error.response.status > 400 && error.response.status < 500)
                            swal({
                                title: "Veiksmas neleidžiamas",
                                button: "Gerai"
                            });
                    });
            }
        });
    }

    handleRestorePassword = (item) => {
        const username = item.username;
        console.log("Atstatyti slaptazodi naudotojui", username);

        swal({
            text: "Ar tikrai norite atkurti pirminį slaptažodį?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                http
                    .put(`${apiEndpoint}/api/users/admin/password/${username}`)
                    .then((response) => {
                        const { currentPage, numberOfElements } = this.state;
                        const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
                        this.getUserInfo(page);
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                    }).catch(error => {
                        if (error && error.response.status > 400 && error.response.status < 500)
                            swal({
                                text: "Veiksmas neleidžiamas",
                                button: "Gerai"
                            });

                    });
            }
        })
    }

    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getUserInfo(page);
    };

    render() {

        const { length: count } = this.state.naudotojai;

        if (count === 0) return <div className="container pt-5"><h6 className="pt-5">Naudotojų sąrašas tuščias.</h6></div>

        const { naudotojai, totalElements, pageSize } = this.state;

        return (
            <React.Fragment >

                <UserListTable
                    naudotojai={naudotojai}
                    onDelete={this.handleDelete}
                    onRestorePassword={this.handleRestorePassword}
                />

                <Pagination
                    itemsCount={totalElements}
                    pageSize={pageSize}
                    onPageChange={this.handlePageChange}
                    currentPage={this.state.currentPage}
                />


            </React.Fragment>
        )
    }
}

export default UserListContainer