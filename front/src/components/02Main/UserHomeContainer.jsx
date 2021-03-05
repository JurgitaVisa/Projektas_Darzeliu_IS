import React, { Component } from 'react';

import '../../App.css';
import swal  from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import UserApplicationsTable from './UserApplicationsTable';
export class UserHomeContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            applications: []
        }
    }
    componentDidMount() {

        this.getUserApplications();
    }

    getUserApplications() {
        http
            .get(`${apiEndpoint}/api/prasymai/user`)
            .then((response) => {
                
                this.setState({ applications: response.data });

            }).catch(error => {
                console.log("Naudotojai prasymai container error", error.response);
                if (error && error.response.status > 400 && error.response.status < 500)
                    swal({
                        text: "Veiksmas neleidžiamas",
                        button: "Gerai"
                    });
            });
    }

    handleDelete = (item) => {
       
        swal({
            text: "Ar tikrai norite ištrinti prašymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                http.delete(`${apiEndpoint}/api/prasymai/user/delete/${item.id}`)
                    .then((response) => {                       
                        swal({
                            text: response.data,
                            button: "Gerai"
                        })
                        this.getUserApplications();
                    }).catch((error) => {
                        if (error && error.response.status > 400 && error.response.status < 500)
                            swal({
                                text: "Veiksmas neleidžiamas",
                                button: "Gerai"
                            });
                    });
            }
        });
    }

    render() {
        const { length: count } = this.state.applications;

        if (count === 0) return <div className="container pt-5"><h6 className="pt-5">Jūs neturite pateiktų prašymų.</h6></div>

        return (

            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Mano prašymai</h6>

                <div className="row pt-2">
                    <div className="col-12 col-sm-12 col-md-12 col-lg-9">
                        <UserApplicationsTable
                            applications={this.state.applications}
                            onDelete={this.handleDelete}
                        />
                    </div>

                </div>
            </div>
        )
    }
}

export default UserHomeContainer