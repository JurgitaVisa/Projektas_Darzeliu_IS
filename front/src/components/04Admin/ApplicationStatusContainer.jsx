import React, { Component } from 'react';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';


export class ApplicationStatusContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isActive: false,
            isLocked: false
        }
    }
    componentDidMount() {
        this.getApplicationState();

    }

    getApplicationState() {
        http
            .get(`${apiEndpoint}/api/status`)
            .then((response) => {
                this.setState(
                    { isActive: response.data }
                );
            })
    }

    handleClick = (e) => {

        const buttonValue = e.currentTarget.value;

        if (buttonValue === "Unlock") {
            http.post(`${apiEndpoint}/api/queue/unlock`)
                .then((response) => {
                    this.setState({
                        //TODO: nustatyti statusą pagal atsakymą
                        isLocked: false
                    })
                })
        } else {
            http.post(`${apiEndpoint}/api/queue/lock`)
                .then((response) => {
                    //TODO: nustatyti statusą pagal atsakymą
                    this.setState({
                        isLocked: true
                    });
                })
        }
    }

    render() {

        const { isActive, isLocked } = this.state;

        return (

            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Prašymų sąrašo redagavimo administravimas</h6>
                {isActive && <p className="pl-2 pt-3">Registracija vykdoma</p>}
                {!isActive && <p className="pl-2 pt-3">Šiuo metu registracija nevykdoma</p>}

                {isLocked && <button className="btn btn-outline-primary btn-sm" id="btnUnlockQueueEdit">Atrakinti sąrašo redagavimą</button>}
                {!isLocked && <button className="btn btn-outline-danger btn-sm" id="btnLockQueueEdit">Užrakinti sąrašo redagavimą</button>}

            </div>
        )
    }
}

export default ApplicationStatusContainer