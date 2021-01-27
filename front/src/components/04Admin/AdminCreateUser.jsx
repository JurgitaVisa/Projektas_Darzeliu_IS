import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

/*
    Todo:
        handleSubmit();
        fix Naudotojo prisijungimai style
        Optional: Bootstrap validation
*/

const styleFieldRequired = {
    color: "red",
    textTransform: "uppercase",
    fontSize: "10px",
    fontWeight: "bold"
}

export default class AdminCreateUser extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            role: "ADMIN",
            name: "",
            surname: "",
            birthdate: "",
            identificationCode: "",
            address: "",
            telno: "",
            email: "",
            password: ""
        }
        this.roleDropdownOnChange = this.roleDropdownOnChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    drawSelector (role) {
        if(role === "ADMIN" || role === "MANAGER") {
            return (
                <div className="form-row">
                    <div className="form-group col">
                        <label for="role-selector">Naudotojo rolė:</label>
                        <select name="role-selector" id="selectpicker" className="form-control" value={this.state.role} onChange={this.roleDropdownOnChange}>
                            <option value="ADMIN">Administratorius</option>
                            <option value="MANAGER">Švietimo specialistas</option>
                            <option value="USER">Vaiko atstovas</option>
                        </select>
                    </div>
                    <div className="form-group col">
                        <label for="txtEmail">El. paštas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="email" className="form-control" id="txtEmail" name="email" value={this.state.email} onChange={this.handleChange} placeholder="El. paštas" required="required"></input>
                    </div>
                </div>
            )
        }
        else if(role === "USER") {
            return (
                <div className="form-row">
                    <div className="form-group col-6">
                        <label for="role-selector">Naudotojo rolė:</label>
                        <select name="role-selector" id="selectpicker" className="form-control" value={this.state.role} onChange={this.roleDropdownOnChange}>
                            <option value="ADMIN">Administratorius</option>
                            <option value="MANAGER">Švietimo specialistas</option>
                            <option value="USER">Vaiko atstovas</option>
                        </select>
                    </div>
                </div>
            )
        }
    }

    drawForm (role) {
        if(role === "ADMIN" || role === "MANAGER") {
            return (
                <div className="form-row">
                    <div className="form-group col">
                        <label for="txtName">Vardas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtName" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                    </div>
                    <div className="form-group col">
                        <label for="txtSurname">Pavardė <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtSurname" name="surname" value={this.state.surname} onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                    </div>
                </div>
            )
        }
        else if(role === "USER") {
            return (
            <div className="innerForm">
                <div className="form-row">
                    <div className="form-group col">
                        <label for="txtEmail">El. paštas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="email" className="form-control" id="txtEmail" name="email" value={this.state.email} onChange={this.handleChange} placeholder="El. paštas" required="required" maxLength="32"></input>
                    </div>
                    <div className="form-group col">
                        <label for="txtIdentificationCode">Asmens kodas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtIdentificationCode" name="identificationCode" value={this.state.identificationCode} onChange={this.handleChange} placeholder="Asmens kodas" required="required" pattern="[0-9]{11}"></input>
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col">
                        <label for="txtName">Vardas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtName" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Vardas" required="required"></input>
                    </div>
                    <div className="form-group col">
                        <label for="txtSurname">Pavardė <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtSurname" name="surname" value={this.state.surname} onChange={this.handleChange} placeholder="Pavardė" required="required"></input>
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col">
                        <label for="txtAddress">Adresas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="text" className="form-control" id="txtAddress" name="address" value={this.state.address} onChange={this.handleChange} placeholder="Adresas" required="required"></input>
                    </div>
                    <div className="form-group col">
                        <label for="txtTelNo">Telefonas <span style={styleFieldRequired}>Būtinas</span></label>
                        <input type="tel" className="form-control" id="txtTelNo" name="telno" value={this.state.telno} onChange={this.handleChange} placeholder="+370xxxxxxxx" required="required" pattern="[+,0-9]{12}"></input>
                    </div>
                </div>
                {/*
                <div className="form-group">
                    <label for="txt">Gimimo data:</label>
                    <input type="date" className="form-control" id="txtBirthdate" name="birthdate" value={this.state.birthdate} onChange={this.handleChange} placeholder="Gimimo data" required="required"></input>
                </div>
                */}
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
    }

    handleSubmit(event) {
        /*
            Todo:
                Post data to server
                Handle errors
        */
        event.preventDefault();
        if(this.state.role==="ADMIN" || this.state.role==="MANAGER") {
            this.setState({
                username: this.state.email,
                password: this.state.email
            }, () => 
            // Todo: change post url
            http.post(`http://localhost:8080/createuser`, {
                "name": this.state.name,
                "password": this.state.password,
                "role": this.state.role,
                "surname": this.state.surname,
                "username": this.state.username
            })
                .then((response) => {
                    console.log("Naujas vartotojas sukurtas");
                    console.log(this.state);
                    alert('Naujas vartotojas sėkmingai sukurtas!')
                })
            )
        }
    }

    const 
    render() {
        return (
            <div className="container">
                <h4><b>Naujo vartotojo sukūrimas</b></h4>
                <div className="row">
                <form className="col-8" onSubmit={this.handleSubmit}>
                    {this.drawSelector(this.state.role)}
                    {this.drawForm(this.state.role)}
                    <h4><b>Naudotojo prisijungimai</b></h4>
                    <div className="col-8">
                         <div className="row">
                            <div className="col-4">
                                <p><b>Vartotojo vardas</b></p>
                            </div>
                            <div className="col-8">
                                <p>{this.state.email}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-4">
                                <p><b>Slaptažodis</b></p>
                            </div>
                            <div className="col-8">
                                <p>{this.state.email}</p>
                            </div>
                        </div>
                    </div>
                    <button type="submit" className="btn btn-primary float-left">Sukurti</button>
                </form>
                </div>
            </div>
        )
    }
}
