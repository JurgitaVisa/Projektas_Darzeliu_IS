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
                    {
                        isActive: response.data.registrationActive,
                        isLocked: response.data.queueEditingLocked
                    }
                );
            })
            .catch(() => {})
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

                <h6 className="py-3">Prašymų sąrašo redagavimo administravimas</h6>

                {isActive && <p >Prašymų teikimo statusas:   Registracija vykdoma</p>}
                {!isActive && <p >Prašymų teikimo statusas:   Šiuo metu registracija nevykdoma</p>}

                {isLocked && <button onClick={this.handleClick} value="Unlock" className="btn btn-outline-primary btn-sm" id="btnUnlockQueueEdit">Atrakinti sąrašo redagavimą</button>}
                {!isLocked && <button onClick={this.handleClick} value="Lock" className="btn btn-outline-danger btn-sm" id="btnLockQueueEdit">Užrakinti sąrašo redagavimą</button>}

            </div>
        )
    }
}

export default ApplicationStatusContainer