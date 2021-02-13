import React, { Component } from 'react'
import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import inputValidator from '../08CommonComponents/InputValidator';
import swal from 'sweetalert';

export default class UpdateProfileFormContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            role: "",
            name: "",
            surname: "",
            personalCode: "",
            address: "",
            phone: "",
            email: "",
            passwordUpdate: false
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleUpdatePasswordButton = this.handleUpdatePasswordButton.bind(this);
        this.handleUpdatePasswordSubmit = this.handleUpdatePasswordSubmit.bind(this);

    }
    componentDidMount() {
        http.get(`${apiEndpoint}/api/users/user`)
            .then((response) => {
                this.setState({
                    role: response.data.role,
                    name: response.data.name,
                    surname: response.data.surname,
                    personalCode: response.data.personalCode,
                    address: response.data.address,
                    phone: response.data.phone,
                    email: response.data.username,
                    username: response.data.username
                })
                console.log(response);
            })
    }
    
    /** Update user info form */
    drawUpdateForm(role) {
        /** Admin & Manager form */
        if(role !== "USER") {
            return (
                    <div>
                        <div className="row form-group">
                            <label htmlFor="txtName">Vardas <span className="fieldRequired">*</span></label>
                            <input 
                                type="text"
                                id="txtName"
                                name="name"
                                placeholder="Vardas"
                                className="form-control"
                                value={this.state.name}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtSurname">Pavardė <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                id="txtSurname"
                                name="surname"
                                placeholder="Pavardė"
                                className="form-control"
                                value={this.state.surname}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtEmail">El. paštas <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                id="txtEmail"
                                name="email"
                                placeholder="El. paštas"
                                className="form-control"
                                value={this.state.email}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                            />
                        </div>
                    </div>
            )
        }
        /** User form */
        else {
            return (
                    <div>
                        <div className="row form-group">
                            <label htmlFor="txtName">Vardas <span className="fieldRequired">*</span></label>
                            <input 
                                type="text"
                                id="txtName"
                                name="name"
                                placeholder="Vardas"
                                className="form-control"
                                value={this.state.name}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtSurname">Pavardė <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                id="txtSurname"
                                name="surname"
                                placeholder="Pavardė"
                                className="form-control"
                                value={this.state.surname}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtPersonalCode">Asmens kodas <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                id="txtPersonalCode"
                                name="personalCode"
                                placeholder="Asmens kodas"
                                className="form-control"
                                value={this.state.personalCode}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[0-9]{11}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtTelNo">Telefonas <span className="fieldRequired">*</span></label>
                            <div className="input-group">
                                <div className="input-group-prepend">
                                    <div className="input-group-text">
                                        +370
                                    </div>
                                </div>
                                <input
                                    type="tel"
                                    id="txtTelNo"
                                    name="phone"
                                    placeholder="Telefono numeris"
                                    className="form-control"
                                    value={this.state.telno}
                                    onChange={this.handleChange}
                                    onInvalid={(e) => inputValidator(e)}
                                    required pattern="[0-9]{8}">
                                </input>
                            </div>
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtEmail">El. paštas <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                id="txtEmail"
                                name="email"
                                placeholder="El. paštas"
                                className="form-control"
                                value={this.state.email}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtAddress">Adresas <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                className="form-control"
                                id="txtAddress"
                                name="address"
                                placeholder="Adresas"
                                value={this.state.address}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                            />
                        </div>
                    </div>
            )
        }
    }

    /** Change handler */

    handleChange(e) {
        inputValidator(e);
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    /** Submit handler */

    handleSubmit(e) {
        e.preventDefault();
        console.log(this.state);
        http.put(`${apiEndpoint}/api/users/update`, {
            "address": this.state.address,
            "email": this.state.email,
            "name": this.state.name,
            "password": this.state.password,
            "personalCode": this.state.personalCode,
            "phone": this.state.phone,
            "role": this.state.role,
            "surname": this.state.surname,
            "username": this.state.username
        })
        .then((response) => {
            swal({
                title: "Užklausa atlikta sėkmingai",
                text: "Naudotojo duomenys buvo sėkmingai atnaujinti",
                icon: "success",
                button: "Gerai"
            })
        })
        .catch((error) => {
            swal({
                title: "Įvyko klaida",
                text: error.response.data,
                icon: "warning",
                button: "Gerai"
            })
        })
    }

    /**Update password form */

    drawUpdatePasswordForm() {
        if(this.state.passwordUpdate) {
            return (
                <div className="form">
                    <form onSubmit={this.handleUpdatePasswordSubmit}>
                        <div className="row form-group">
                            <label htmlFor="txtOldPassword">Senas slaptažodis <span className="fieldRequired">*</span></label>
                            <input
                                type="password"
                                id="txtOldPassword"
                                name="oldPassword"
                                className="form-control"
                                placeholder="Senas slaptažodis"
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                            />
                        </div>
                        <div className="row">
                            <label htmlFor="txtNewPassword">Įveskite naują slaptažodį</label>
                        </div>
                        <div className="row form-group">
                            <p className="fieldRequired">Slaptažodis turi būti ne mažiau 8 simbolių ilgio, turėti bent vieną didžiąją ir mažąją raides ir bent vieną skaičių.</p>
                            <input
                                type="password"
                                id="txtNewPassword"
                                name="newPassword"
                                className="form-control"
                                placeholder="Naujas slaptažodis"
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                            />
                        </div>
                        <div className="row form-group">
                            <label htmlFor="txtRepeatNewPassword">Pakartokite naują slaptažodį</label>
                            <input
                                type="password"
                                id="txtNewPasswordRepeat"
                                name="newPasswordRepeat"
                                className="form-control"
                                placeholder="Pakartokite naują slaptažodį"
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                required
                                pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                            />
                        </div>
                        <div className="row form-group">
                            <button type="submit" className="btn btn-primary">Išsaugoti</button>
                        </div>
                    </form>
                </div>
            )
        }
        else {
            return (
                <div>
                    <div className="row">
                        <button className="btn btn-primary" onClick={this.handleUpdatePasswordButton}>Keisti</button>
                    </div>
                </div>
            )
        }
    }

    /** Update password button handler */

    handleUpdatePasswordButton() {
        this.setState({
            passwordUpdate: true
        })
    }

    /** Update password submit handler */
    
    handleUpdatePasswordSubmit(e) {
        e.preventDefault();
        if(this.state.newPassword!==this.state.newPasswordRepeat) {
            swal({
                title: "Įvyko klaida",
                text: "Slaptažodžiai nesutampa.",
                icon: "warning",
                button: "Gerai"
            })
            console.log("Slaptazodziai nesutampa!");
        }
        else {
            /**
             * TODO:
             * Send new password to server */
        }
    }

    render(props) {
        return (
            <div>
                <div className="container">
                    <div className="form" >
                        <form onSubmit={this.handleSubmit}>
                            {this.drawUpdateForm(this.state.role)}
                            <div className="row">
                                <button type="submit" id="btnSubmit" className="btn btn-primary">Išsaugoti</button>
                            </div>
                        </form>
                    </div>
                    <div className="row">
                        <h6 className="py-3"><b>Naudotojo prisijungimai</b></h6>
                    </div>
                    <div className="row">
                        <div className="col">
                            <p>Naudotojo vardas</p>
                        </div>
                        <div className="col-10">
                            <p>{this.state.username}</p>
                        </div>
                    </div>
                    <div className="row">
                        <h6 className="py-3"><b>Keisti slaptažodį</b></h6>
                    </div>
                    {
                        this.drawUpdatePasswordForm()
                    }
                </div>
            </div>
        )
    }
}

