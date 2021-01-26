import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import NavBar from '../00Navigation/NavBar';

function drawForm(role) {
    if(role == "admin") {
        return (
        <div className="innerForm">
            <div className="form-group">
                <label for="txtName">Vardas:</label>
                <input type="text" className="form-control" id="txtName"></input>
            </div>
            <div className="form-group">
                <label for="txtLastname">Pavardė:</label>
                <input type="text" className="form-control" id="txtLastname"></input>
            </div>
            <div className="form-group">
                <label for="txtEmail">El. paštas:</label>
                <input type="text" className="form-control" id="txtEmail"></input>
            </div>
            <div className="form-group">
                <label for="txtPassword">Slaptažodis:</label>
                <input type="text" className="form-control" id="txtPassword"></input>
            </div>
        </div>
        )
    }
    else if(role == "worker") {
        return (
        <div className="innerForm">
            <div className="form-group">
                <label for="txtName">Vardas:</label>
                <input type="text" className="form-control" id="txtName"></input>
            </div>
            <div className="form-group">
                <label for="txtLastname">Pavardė:</label>
                <input type="text" className="form-control" id="txtLastname"></input>
            </div>
            <div className="form-group">
                <label for="txtEmail">El. paštas:</label>
                <input type="text" className="form-control" id="txtEmail"></input>
            </div>
            <div className="form-group">
                <label for="txtPassword">Slaptažodis:</label>
                <input type="text" className="form-control" id="txtPassword"></input>
            </div>
        </div>
        )
    }
    else if(role == "user") {
        return (
        <div className="innerForm">
            <div className="form-group">
                <label for="txtName">Vardas:</label>
                <input type="text" className="form-control" id="txtName"></input>
            </div>
            <div className="form-group">
                <label for="txtLastname">Pavardė:</label>
                <input type="text" className="form-control" id="txtLastname"></input>
            </div>
            <div className="form-group">
                <label for="txt">Gimimo data:</label>
                <input type="date" className="form-control" id="txtBirthdate"></input>
            </div>
            <div className="form-group">
                <label for="txtIdentificationCode">Asmens kodas:</label>
                <input type="number" className="form-control" id="txtIdentificationCode"></input>
            </div>
            <div className="form-group">
                <label for="txtAddress">Deklaruota gyv. vieta:</label>
                <input type="text" className="form-control" id="txtAddress"></input>
            </div>
            <div className="form-group">
                <label for="txtTelNo">Telefonas:</label>
                <input type="number" className="form-control" id="txtTelNo"></input>
            </div>
            <div className="form-group">
                <label for="txtEmail">El. paštas:</label>
                <input type="text" className="form-control" id="txtEmail"></input>
            </div>
            <div className="form-group">
                <label for="txtUsername">Vartotojo vardas:</label>
                <input type="text" className="form-control" id="txtUsername"></input>
            </div>
            <div className="form-group">
                <label for="txtPassword">Slaptažodis:</label>
                <input type="text" className="form-control" id="txtPassword"></input>
            </div>
        </div>
        )
    }
}

export default class AdminCreateUser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            role: "admin"
        }
    }
    roleDropdownOnChange = e => {
        console.log(e.target.value);
        this.setState({
            role: e.target.value
        })
    }
    const 
    render() {
        return (
            <div>
                <h1>Naujo vartotojo sukūrimas</h1>
                <form>
                    <div className="form-group">
                        <label for="role-selector">Vartotojo rolė:</label>
                        <select name="role-selector" id="selectpicker" className="form-control" onChange={this.roleDropdownOnChange}>
                            <option value="admin">Administratorius</option>
                            <option value="worker">Ugdymo įstaigos darbuotojas</option>
                            <option value="user">Vartotojas</option>
                        </select>
                    </div>
                    {drawForm(this.state.role)}
                    <button className="btn btn-primary float-right">Išsaugoti</button>
                </form>

            </div>
        )
    }
}
