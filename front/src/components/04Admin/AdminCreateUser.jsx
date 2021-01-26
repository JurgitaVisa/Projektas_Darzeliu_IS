import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

/*
    Todo:
        Solve the warning message when changing from admin or worker to user.
*/

export default class AdminCreateUser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            role: "admin",
            name: "",
            lastname: "",
            birthdate: "",
            identificationCode: "",
            address: "",
            telno: "",
            email: "",
            username: "",
            password: ""
        }
        this.roleDropdownOnChange = this.roleDropdownOnChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    drawForm (role) {
        if(role === "admin" || role === "worker") {
            return (
            <div className="innerForm">
                <div className="form-group">
                    <label for="txtName">Vardas:</label>
                    <input type="text" className="form-control" id="txtName" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtLastname">Pavardė:</label>
                    <input type="text" className="form-control" id="txtLastname" name="lastname" onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtEmail">El. paštas:</label>
                    <input type="email" className="form-control" id="txtEmail" name="email" value={this.state.email} onChange={this.handleChange} placeholder="El. paštas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtPassword">Slaptažodis:</label>
                    <input type="password" className="form-control" id="txtPassword" name="password" onChange={this.handleChange} placeholder="Slaptažodis" required="required" minLength="8"></input>
                </div>
            </div>
            )
        }
        else if(role === "user") {
            return (
            <div className="innerForm">
                <div className="form-group">
                    <label for="txtName">Vardas:</label>
                    <input type="text" className="form-control" id="txtName" name="name" onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtLastname">Pavardė:</label>
                    <input type="text" className="form-control" id="txtLastname" name="lastname" onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txt">Gimimo data:</label>
                    <input type="date" className="form-control" id="txtBirthdate" name="birthdate" onChange={this.handleChange} placeholder="Gimimo data" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtIdentificationCode">Asmens kodas:</label>
                    <input type="text" className="form-control" id="txtIdentificationCode" name="identificationCode" onChange={this.handleChange} placeholder="Asmens kodas" required="required" pattern="[0-9]{11}"></input>
                </div>
                <div className="form-group">
                    <label for="txtAddress">Deklaruota gyv. vieta:</label>
                    <input type="text" className="form-control" id="txtAddress" name="address" onChange={this.handleChange} placeholder="Deklaruota gyv. vieta" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtTelNo">Telefonas:</label>
                    <input type="tel" className="form-control" id="txtTelNo" name="telno" onChange={this.handleChange} placeholder="+370xxxxxxxx" required="required" pattern="[+,0-9]{12}"></input>
                </div>
                <div className="form-group">
                    <label for="txtEmail">El. paštas:</label>
                    <input type="email" className="form-control" id="txtEmail" name="email" onChange={this.handleChange} placeholder="vardenis@pavardenis.lt" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtUsername">Vartotojo vardas:</label>
                    <input type="text" className="form-control" id="txtUsername" name="username" onChange={this.handleChange} placeholder="Vartotojo vardas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtPassword">Slaptažodis:</label>
                    <input type="password" className="form-control" id="txtPassword" name="password" onChange={this.handleChange} placeholder="Slaptažodis" required="required" minLength="8"></input>
                </div>
            </div>
            )
        }
    }

    roleDropdownOnChange(event) {
        event.preventDefault()
        this.setState({
            role: event.target.value,
            //reset state
            name: "",
            lastname: "",
            birthdate: "",
            identificationCode: "",
            address: "",
            telno: "",
            email: "",
            username: "",
            password: ""
        })
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    handleSubmit(event) {
        event.preventDefault();
        console.log(this.state);
        /*
            Todo:
                Post data to server
                Handle errors
        */
    }

    const 
    render() {
        return (
            <div>
                <h1>Naujo vartotojo sukūrimas</h1>
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label for="role-selector">Vartotojo rolė:</label>
                        <select name="role-selector" id="selectpicker" className="form-control" value={this.state.role} onChange={this.roleDropdownOnChange}>
                            <option value="admin">Administratorius</option>
                            <option value="worker">Ugdymo įstaigos darbuotojas</option>
                            <option value="user">Vartotojas</option>
                        </select>
                    </div>
                    {this.drawForm(this.state.role)}
                    <button type="submit" className="btn btn-primary float-right">Išsaugoti</button>
                </form>
            </div>
        )
    }
}
