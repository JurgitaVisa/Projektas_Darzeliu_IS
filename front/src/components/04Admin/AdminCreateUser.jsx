import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

/*
    Todo:
        handleSubmit();
        Optional: Bootstrap validation
*/

export default class AdminCreateUser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            role: "admin",
            name: "test",
            surname: "test",
            birthdate: "",
            identificationCode: "",
            address: "",
            telno: "",
            email: "test@test",
            password: ""
        }
        this.roleDropdownOnChange = this.roleDropdownOnChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    drawForm (role) {
        if(role === "admin" || role === "manager") {
            return (
            <div className="innerForm">
                <div className="form-group">
                    <label for="txtName">Vardas:</label>
                    <input type="text" className="form-control" id="txtName" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtSurname">Pavardė:</label>
                    <input type="text" className="form-control" id="txtSurname" name="surname" value={this.state.surname} onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtEmail">El. paštas:</label>
                    <input type="email" className="form-control" id="txtEmail" name="email" value={this.state.email} onChange={this.handleChange} placeholder="El. paštas" required="required"></input>
                </div>
            </div>
            )
        }
        else if(role === "user") {
            return (
            <div className="innerForm">
                <div className="form-group">
                    <label for="txtName">Vardas:</label>
                    <input type="text" className="form-control" id="txtName" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtSurname">Pavardė:</label>
                    <input type="text" className="form-control" id="txtSurname" name="surname" value={this.state.surname} onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txt">Gimimo data:</label>
                    <input type="date" className="form-control" id="txtBirthdate" name="birthdate" value={this.state.birthdate} onChange={this.handleChange} placeholder="Gimimo data" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtIdentificationCode">Asmens kodas:</label>
                    <input type="text" className="form-control" id="txtIdentificationCode" name="identificationCode" value={this.state.identificationCode} onChange={this.handleChange} placeholder="Asmens kodas" required="required" pattern="[0-9]{11}"></input>
                </div>
                <div className="form-group">
                    <label for="txtAddress">Deklaruota gyv. vieta:</label>
                    <input type="text" className="form-control" id="txtAddress" name="address" value={this.state.address} onChange={this.handleChange} placeholder="Deklaruota gyv. vieta" required="required"></input>
                </div>
                <div className="form-group">
                    <label for="txtTelNo">Telefonas:</label>
                    <input type="tel" className="form-control" id="txtTelNo" name="telno" value={this.state.telno} onChange={this.handleChange} placeholder="+370xxxxxxxx" required="required" pattern="[+,0-9]{12}"></input>
                </div>
                <div className="form-group">
                    <label for="txtEmail">El. paštas:</label>
                    <input type="email" className="form-control" id="txtEmail" name="email" value={this.state.email} onChange={this.handleChange} placeholder="vardenis@pavardenis.lt" required="required"></input>
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
            surname: "",
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
        //Check each field
        if(event.target.value.length>=0) {
            console.log(event.target.value.length)

        }
    }

    handleSubmit(event) {
        /*
            Todo:
                Post data to server
                Handle errors
        */
        event.preventDefault();
        if(this.state.role==="admin" || this.state.role==="manager") {
            this.setState({
                username: this.state.email,
                password: this.state.email
            }, () => 
            http.post(`${apiEndpoint}/createuser`, {
                "name": this.state.name,
                "password": this.state.password,
                "role": this.state.role,
                "surname": this.state.surname,
                "username": this.state.username
            })
                .then((response) => {
                    
                })
            )
        }
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
                            <option value="manager">Ugdymo įstaigos darbuotojas</option>
                            <option value="user">Vartotojas</option>
                        </select>
                    </div>
                    {this.drawForm(this.state.role)}
                    <p><b>Pradinis slaptažodis yra el. paštas!</b></p>
                    <button type="submit" className="btn btn-primary float-right">Išsaugoti</button>
                </form>
            </div>
        )
    }
}
