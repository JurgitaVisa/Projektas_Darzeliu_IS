import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

var currentDate = (new Date().getUTCFullYear()) + "-" + dateFormat(new Date().getUTCMonth() + 1) + "-" + dateFormat(new Date().getUTCDate());

function dateFormat(num) {
    if(num>=1 && num<=9) {
        return "0" + num;
    }
    else return num;
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
            email: ""
        }
        this.roleDropdownOnChange = this.roleDropdownOnChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    drawSelector () {
        return (
            <div className="form-row">
                    <div className="form-group col">
                        <label htmlFor="role-selector">Naudotojo rolė:</label>
                        <select name="role-selector" id="selectpicker" className="form-control" value={this.state.role} onChange={this.roleDropdownOnChange}>
                            <option value="ADMIN">Administratorius</option>
                            <option value="MANAGER">Švietimo specialistas</option>
                            <option value="USER">Vaiko atstovas</option>
                        </select>
                    </div>
                    <div className="form-group col">
                        <label htmlFor="txtEmail">El. paštas <span className="fieldRequired">*</span></label>
                        <input 
                            type="text" 
                            className="form-control" 
                            id="txtEmail" 
                            name="email" 
                            value={this.state.email} 
                            placeholder="El. paštas" 
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            required
                            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                        />
                    </div>
            </div>
        )
    }

    drawForm (role) {
        if(role === "ADMIN" || role === "MANAGER") {
            return (
                <div className="form-row">
                    <div className="form-group col">
                        <label htmlFor="txtName">Vardas</label>
                        <input 
                            type="text"
                            className="form-control"
                            id="txtName"
                            name="name"
                            value={this.state.name}
                            onChange={this.handleChange}
                            placeholder="Vardas"
                            pattern="[A-z]{1,32}"
                        />
                    </div>
                    <div className="form-group col">
                        <label htmlFor="txtSurname">Pavardė</label>
                        <input 
                            type="text"
                            className="form-control"
                            id="txtSurname"
                            name="surname"
                            value={this.state.surname}
                            onChange={this.handleChange}
                            placeholder="Pavardė"
                            pattern="[A-z]{1,32}"
                        />
                    </div>
                </div>
            )
        }
        else if(role === "USER") {
            return (
            <div className="innerForm">
                <div className="form-row">
                    <div className="form-group col">
                        <label htmlFor="txt">Gimimo data <span className="fieldRequired">*</span></label>
                        <input 
                            type="date"
                            data-date-format="YYYY-MM-DD"
                            min='1900-01-01'
                            max={currentDate}
                            className="form-control"
                            id="txtBirthdate"
                            name="birthdate"
                            value={this.state.birthdate}
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="MMMM-MM-DD"
                            required
                        />
                    </div>
                    <div className="form-group col">
                        <label htmlFor="txtIdentificationCode">Asmens kodas <span className="fieldRequired">*</span></label>
                        <input 
                            type="text"
                            className="form-control"
                            id="txtIdentificationCode"
                            name="identificationCode"
                            value={this.state.identificationCode}
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="Asmens kodas"
                            required 
                            pattern="[0-9]{11}"
                        />
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col">
                        <label htmlFor="txtName">Vardas <span className="fieldRequired">*</span></label>
                        <input 
                            type="text"
                            className="form-control"
                            id="txtName"
                            name="name"
                            value={this.state.name}
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="Vardas"
                            required
                            pattern="[A-zÀ-ž]{1,32}"
                        />
                    </div>
                    <div className="form-group col">
                        <label htmlFor="txtSurname">Pavardė <span className="fieldRequired">*</span></label>
                        <input 
                            type="text" 
                            className="form-control" 
                            id="txtSurname" 
                            name="surname" 
                            value={this.state.surname} 
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="Pavardė" 
                            required
                            pattern="[A-zÀ-ž]{1,32}"
                        />
                    </div>
                </div>
                <div className="form-row">
                    <div className="form-group col">
                        <label htmlFor="txtAddress">Adresas <span className="fieldRequired">*</span></label>
                        <input 
                            type="text"
                            className="form-control"
                            id="txtAddress"
                            name="address"
                            value={this.state.address}
                            onChange={this.handleChange}
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="Adresas"
                            required
                        />
                    </div>
                    <div className="form-group col">
                        <label htmlFor="txtTelNo">Telefonas <span className="fieldRequired">*</span></label>
                        <input 
                            type="tel" 
                            className="form-control" 
                            id="txtTelNo" 
                            name="telno" 
                            value={this.state.telno}
                            onChange={this.handleChange} 
                            onInvalid={(e) => this.validateText(e)}
                            placeholder="+370xxxxxxxx" 
                            required pattern="[+,0-9]{12}"
                        />
                    </div>
                </div>
            </div>
            )
        }
    }

    resetState = () => {
        this.setState({
            name: "",
            surname: "",
            birthdate: "",
            identificationCode: "",
            address: "",
            telno: "",
            email: ""
        })
    }

    validateText(event) {
        const target = event.target;
        if(target.validity.valueMissing && target.id!="txtBirthdate") {
            target.setCustomValidity(target.placeholder + " yra privalomas laukelis")
        }
        else {
            if(target.id==="txtEmail") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Neteisingas el. pašto formatas")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if(target.id==="txtBirthdate") {
                if(target.validity.valueMissing) {
                    target.setCustomValidity("Gimimo data yra privalomas laukelis")
                }
                else if(target.validity.patternMismatch) {
                    target.setCustomValidity("Neteisingas gimimo datos formatas")
                }
                else if(target.validity.rangeOverflow) {
                    target.setCustomValidity("Gimimo data negali būti ateityje")
                }
                else if(target.validity.rangeUnderflow) {
                    target.setCustomValidity("Gimimo data negali būti ankstesnė nei 01.01.1900")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if(target.id==="txtIdentificationCode") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Asmens koda sudaro 11 skaičių, laukelyje įrašyta: " + target.value.length + " skaičiai.")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if(target.id==="txtName") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Netinkamo formato vardas")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if(target.id==="txtSurname") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Netinkamo formato pavardė")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if(target.id==="txtAddress") {
                target.setCustomValidity("");
            }
            else if(target.id==="txtTelNo") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Neteisingas telefono numerio formatas(+370)")
                }
                else {
                    target.setCustomValidity("");
                }
            }
        }
    }

    roleDropdownOnChange(event) {
        event.preventDefault()
        this.setState({
            role: event.target.value,
        })
        this.resetState();
    }

    handleChange(event) {
        const target = event.target;
        this.validateText(event);
        this.setState({
            [target.name]: target.value
        })
    }

    handleSubmit(event) {
        event.preventDefault();
        http.post(`${apiEndpoint}/api/users/admin/createuser`, {
            "address": this.state.address,
            "birthdate": this.state.birthdate,
            "email": this.state.email,
            "name": this.state.name,
            "password": this.state.email,
            "personalCode": this.state.identificationCode,
            "phone": this.state.telno,
            "role": this.state.role,
            "surname": this.state.surname,
            "username": this.state.email
        })
        .then((response) => {
            console.log("Naujas naudotojas sukurtas");
            console.log(this.state);
            alert('Naujas naudotojas sėkmingai sukurtas!');
            this.resetState();
        })
        .catch((error) => {
            console.log(error);
            alert('Įvyko klaida');
        })
    }
    render() {
        return (
            <div className="container">
                <h4><b>Naujo naudotojo sukūrimas</b></h4>
                <div className="row">
                <form className="col-8" onSubmit={this.handleSubmit}>
                    {this.drawSelector()}
                    {this.drawForm(this.state.role)}
                    <h4><b>Naudotojo prisijungimai</b></h4>
                    <div className="col-12">
                         <div className="row">
                            <div className="col-md-3">
                                <p><b>Naudotojo vardas</b></p>
                            </div>
                            <div className="col-md-9">
                                <p>{this.state.email}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                <p><b>Slaptažodis</b></p>
                            </div>
                            <div className="col-md-9">
                                <p>{this.state.email}</p>
                            </div>
                        </div>
                    </div>
                    <button className="btn btn-danger float-left" onClick={this.resetState}>Išvalyti</button>
                    <button type="submit" className="btn btn-primary float-right">Sukurti</button>
                </form>
                </div>
            </div>
        )
    }
}
