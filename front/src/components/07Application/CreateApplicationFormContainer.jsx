import React, { Component } from 'react'
import inputValidator from '../08CommonComponents/InputValidator';

export default class CreateApplicationFormContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mainGuardian: {
                name: "",
                surname: "",
                personalCode: "",
                phone: "",
                email: "",
                address: "",
            },
            secondGuardian: {
                name: "",
                surname: "",
                personalCode: "",
                phone: "",
                email: "",
                address: "",
            },
            child: {
                name: "",
                surname: "",
                personalCode: "",
                birthdate: "",
                acceptancePriorities: {
                    livesInVilnius: "",
                    childIsAdopted: "",
                    familyHasThreeOrMoreChildrenInSchools: "",
                    guardianInSchool: "",
                    guardianDisability: "",
                },
                kindergartenPriorities: {
                    1: "",
                    2: "",
                    3: "",
                    4: "",
                    5: ""
                }
            }
        }
    }

    /** FORMOS */
    /** Atstovu formos */
    userForm(mainGuardian) {
        if(mainGuardian) {
            return (
                <div className="form">
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
        else {
            return (
                <div className="form">
                    <div className="row form-group">
                        <label htmlFor="txtName">Vardas</label>
                        <input 
                            type="text"
                            id="txtName"
                            name="name"
                            placeholder="Vardas"
                            className="form-control"
                            value={this.state.name}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtSurname">Pavardė</label>
                        <input
                            type="text"
                            id="txtSurname"
                            name="surname"
                            placeholder="Pavardė"
                            className="form-control"
                            value={this.state.surname}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtPersonalCode">Asmens kodas</label>
                        <input
                            type="text"
                            id="txtPersonalCode"
                            name="personalCode"
                            placeholder="Asmens kodas"
                            className="form-control"
                            value={this.state.personalCode}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[0-9]{11}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtTelNo">Telefonas</label>
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
                                pattern="[0-9]{8}">
                            </input>
                        </div>
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtEmail">El. paštas</label>
                        <input
                            type="text"
                            id="txtEmail"
                            name="email"
                            placeholder="El. paštas"
                            className="form-control"
                            value={this.state.email}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtAddress">Adresas</label>
                        <input
                            type="text"
                            className="form-control"
                            id="txtAddress"
                            name="address"
                            placeholder="Adresas"
                            value={this.state.address}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                        />
                    </div>
                </div>
            )
        }
    }
    /** Vaiko forma */
    childForm() {
        return (
            <div className="form">
                <div className="row form-group">
                    <label htmlFor="txtName">Vaiko vardas <span className="fieldRequired">*</span></label>
                    <input 
                        type="text"
                        id="txtName"
                        name="name"
                        placeholder="Vaiko vardas"
                        className="form-control"
                        value={this.state.name}
                        onChange={this.handleChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[A-zÀ-ž]{2,32}"
                    />
                </div>
                <div className="row form-group">
                    <label htmlFor="txtSurname">Vaiko pavardė <span className="fieldRequired">*</span></label>
                    <input
                        type="text"
                        id="txtSurname"
                        name="surname"
                        placeholder="Vaiko pavardė"
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
                {/** Gimimo data */}
                <div className="row form-group">
                    <label htmlFor="txtBirthdate">Gimimo data</label>
                    <input
                        type="text"
                        id="txtBirthdate"
                        name="birthdate"
                        placeholder="Gimimo data"
                        className="form-control datepicker"
                    />
                </div>
            </div>
        )
    }

    /** Pagrindinio atstovo formos onChange */
    mainGuardianOnChange(e) {

    }
    /** Antro atstovo formos onChange */
    additionalGuardianOnChange(e) {

    }
    /** Vaiko formos onChange */
    childOnChange(e) {

    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        {/**Atstovas 1 */}
                        <form>
                            {
                                this.userForm(true)
                            }
                        </form>
                    </div>
                    <div className="col">
                        {/**Atstovas 2 */}
                        <form>
                            {
                                this.userForm(false)
                            }
                        </form>
                    </div>
                    <div className="col">
                        {/**Vaiko duomenys */}
                        <form>
                            {
                                this.childForm()
                            }
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}
