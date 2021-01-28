import React, { Component } from 'react';
import axios from "axios";

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import LoginForm from './LoginForm';

axios.defaults.withCredentials = true;

export class LoginContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loginError: false
        };
    }

    handleUsernameChange = (event) => {
        this.setState({ username: event.target.value })
    }

    handlePassChange = (event) => {
        this.setState({ password: event.target.value })
    }

    handleSubmit = (event) => {
        let userData = new URLSearchParams();
        userData.append('username'
            , this.state.username);
        userData.append('password'
            , this.state.password);
        http.post(`${apiEndpoint}/login`
            , userData,
            { headers: { 'Content-type': 'application/x-www-form-urlencoded' } })
            .then((resp) => {
                console.log("user " + resp.data.username + " logged in");
                this.props.history.push("/home");
            })
            .catch((error) => {
                console.log("Error log from Login submit", error);
                if (error.response.status === 401) {
                    this.setState({ loginError: true });
                } else alert("Prisijungimo klaida: " + error.response.status)
            });

        event.preventDefault();
    }


    render() {
        return (
            <div >
                <div className="text-center">
                    <h5>Sveiki atvykę! </h5>

                </div>

                <LoginForm
                    username={this.state.username}
                    password={this.state.password}
                    loginError={this.state.loginError}
                    onUsernameChange={this.handleUsernameChange}
                    onPassChange={this.handlePassChange}
                    onSubmit={this.handleSubmit}
                />
            </div>
        )
    }
}

export default LoginContainer

