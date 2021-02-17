import React, { Component } from 'react'
import Select from 'react-select'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';

import inputValidator from '../08CommonComponents/InputValidator'

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
            additionalGuardian: {
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
                    livesInVilnius: false,
                    childIsAdopted: false,
                    familyHasThreeOrMoreChildrenInSchools: false,
                    guardianInSchool: false,
                    guardianDisability: false,
                },
                kindergartenPriorities: {
                    1: "",
                    2: "",
                    3: "",
                    4: "",
                    5: ""
                }
            },
            kindergartenList: []
        }
        this.mainGuardianOnChange = this.mainGuardianOnChange.bind(this);
        this.additionalGuardianOnChange = this.additionalGuardianOnChange.bind(this);
        this.childOnChange = this.childOnChange.bind(this);
        this.checkboxOnChange = this.checkboxOnChange.bind(this);
    }

    componentDidMount() {
        /** get logged in user data */
        http.get(`${apiEndpoint}/api/users/user`)
            .then((response) => {
                this.setState({
                    mainGuardian: {
                        ...this.state.mainGuardian,
                        name: response.data.name,
                        surname: response.data.surname,
                        personalCode: response.data.personalCode,
                        phone: response.data.phone.slice(4),
                        email: response.data.username,
                        address: response.data.address
                    }
                })
                /** get kindergarten list */
                var kindergartenList = [];
                http.get(`${apiEndpoint}/api/darzeliai`)
                    .then((response) => {
                        kindergartenList = response.data;
                        this.setState({
                            kindergartenList
                        })
                        console.log(kindergartenList);
                        console.log(this.state.kindergartenList[0]);
                    })
            })
            .catch((error) => {
                swal({
                    title: "Įvyko klaida",
                    text: "Įvyko klaida perduodant duomenis iš serverio.",
                    icon: "error",
                    button: "Gerai"
                })
            })
    }

    /** FORMOS */
    /** Atstovu formos */
    userForm(mainGuardian) {
        if(mainGuardian) {
            return (
                <div className="form">
                    <div className="row">
                        <h6>Atstovas 1</h6>
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtName">Vardas <span className="fieldRequired">*</span></label>
                        <input 
                            type="text"
                            id="txtMainName"
                            name="name"
                            placeholder="Vardas"
                            className="form-control"
                            value={this.state.mainGuardian.name}
                            onChange={this.mainGuardianOnChange}
                            onInvalid={(e) => inputValidator(e)}
                            required
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtSurname">Pavardė <span className="fieldRequired">*</span></label>
                        <input
                            type="text"
                            id="txtMainSurname"
                            name="surname"
                            placeholder="Pavardė"
                            className="form-control"
                            value={this.state.mainGuardian.surname}
                            onChange={this.mainGuardianOnChange}
                            onInvalid={(e) => inputValidator(e)}
                            required
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtPersonalCode">Asmens kodas <span className="fieldRequired">*</span></label>
                        <input
                            type="text"
                            id="txtMainPersonalCode"
                            name="personalCode"
                            placeholder="Asmens kodas"
                            className="form-control"
                            value={this.state.mainGuardian.personalCode}
                            onChange={this.mainGuardianOnChange}
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
                                id="txtMainPhone"
                                name="phone"
                                placeholder="Telefono numeris"
                                className="form-control"
                                value={this.state.mainGuardian.phone}
                                onChange={this.mainGuardianOnChange}
                                onInvalid={(e) => inputValidator(e)}
                                required pattern="[0-9]{8}">
                            </input>
                        </div>
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtEmail">El. paštas <span className="fieldRequired">*</span></label>
                        <input
                            type="text"
                            id="txtMainEmail"
                            name="email"
                            placeholder="El. paštas"
                            className="form-control"
                            value={this.state.mainGuardian.email}
                            onChange={this.mainGuardianOnChange}
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
                            id="txtMainAddress"
                            name="address"
                            placeholder="Adresas"
                            value={this.state.mainGuardian.address}
                            onChange={this.mainGuardianOnChange}
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
                    <div className="row">
                        <h6>Atstovas 2</h6>
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtName">Vardas</label>
                        <input 
                            type="text"
                            id="txtAdditionalName"
                            name="name"
                            placeholder="Vardas"
                            className="form-control"
                            value={this.state.additionalGuardian.name}
                            onChange={this.additionalGuardianOnChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtSurname">Pavardė</label>
                        <input
                            type="text"
                            id="txtAdditionalSurname"
                            name="surname"
                            placeholder="Pavardė"
                            className="form-control"
                            value={this.state.additionalGuardian.surname}
                            onChange={this.additionalGuardianOnChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtPersonalCode">Asmens kodas</label>
                        <input
                            type="text"
                            id="txtAdditionalPersonalCode"
                            name="personalCode"
                            placeholder="Asmens kodas"
                            className="form-control"
                            value={this.state.additionalGuardian.personalCode}
                            onChange={this.additionalGuardianOnChange}
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
                                id="txtAdditionalPhone"
                                name="phone"
                                placeholder="Telefono numeris"
                                className="form-control"
                                value={this.state.additionalGuardian.phone}
                                onChange={this.additionalGuardianOnChange}
                                onInvalid={(e) => inputValidator(e)}
                                pattern="[0-9]{8}">
                            </input>
                        </div>
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtEmail">El. paštas</label>
                        <input
                            type="text"
                            id="txtAdditionalEmail"
                            name="email"
                            placeholder="El. paštas"
                            className="form-control"
                            value={this.state.additionalGuardian.email}
                            onChange={this.additionalGuardianOnChange}
                            onInvalid={(e) => inputValidator(e)}
                            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                        />
                    </div>
                    <div className="row form-group">
                        <label htmlFor="txtAddress">Adresas</label>
                        <input
                            type="text"
                            className="form-control"
                            id="txtAdditionalAddress"
                            name="address"
                            placeholder="Adresas"
                            value={this.state.additionalGuardian.address}
                            onChange={this.additionalGuardianOnChange}
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
                <div className="row">
                        <h6>Vaiko duomenys</h6>
                    </div>
                <div className="row form-group">
                    <label htmlFor="txtName">Vaiko vardas <span className="fieldRequired">*</span></label>
                    <input 
                        type="text"
                        id="txtChildName"
                        name="name"
                        placeholder="Vaiko vardas"
                        className="form-control"
                        value={this.state.child.name}
                        onChange={this.childOnChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[A-zÀ-ž]{2,32}"
                    />
                </div>
                <div className="row form-group">
                    <label htmlFor="txtSurname">Vaiko pavardė <span className="fieldRequired">*</span></label>
                    <input
                        type="text"
                        id="txtChildSurname"
                        name="surname"
                        placeholder="Vaiko pavardė"
                        className="form-control"
                        value={this.state.child.surname}
                        onChange={this.childOnChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[A-zÀ-ž]{2,32}"
                    />
                </div>
                <div className="row form-group">
                    <label htmlFor="txtPersonalCode">Asmens kodas <span className="fieldRequired">*</span></label>
                    <input
                        type="text"
                        id="txtChildPersonalCode"
                        name="personalCode"
                        placeholder="Asmens kodas"
                        className="form-control"
                        value={this.state.child.personalCode}
                        onChange={this.childOnChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[0-9]{11}"
                    />
                </div>
                {/** Gimimo data */}
                <div className="row form-group">
                    <label htmlFor="txtBirthdate">Gimimo data <span className="fieldRequired">*</span></label>
                    <input
                        type="text"
                        id="txtChildBirthdate"
                        name="birthdate"
                        placeholder="Gimimo data"
                        className="form-control datepicker"
                        value={this.state.child.birthdate}
                        onChange={this.childOnChange}
                    />
                </div>
            </div>
        )
    }

    /** Checkbox forma prioritetams */
    checkboxPriorityForm() {
        return (
            <div className="row">
                <div className="col">
                    <div className="form">
                        <div className="row">
                            <div className="form-check">
                                <div className="row">
                                    <h6>Vaiko priėmimo tvarkos prioritetai</h6>
                                </div>
                                <div className="row">
                                    <p>Pažymėkite tinkamus prioritetus</p>
                                </div>
                            </div>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input"
                            name="livesInVilnius"
                            id="chkLivesInVilnius"
                            checked={this.state.child.acceptancePriorities.livesInVilnius}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="livesInVilnius">Vaiko deklaruojama gyvenamoji vieta yra Vilniaus miesto savivaldybė</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input" 
                            name="childIsAdopted" 
                            id="chkChildIsAdopted"
                            checked={this.state.child.acceptancePriorities.childIsAdopted}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="childIsAdopted">Vaikas yra įvaikintas</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input"
                            name="familyHasThreeOrMoreChildrenInSchools"
                            id="chkFamilyHasThreeOrMoreChildrenInSchools"
                            checked={this.state.child.acceptancePriorities.familyHasThreeOrMoreChildrenInSchools}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="familyHasThreeOrMoreChildrenInSchools">Šeima augina (globoja) tris ir daugiau vaikų, kurie mokosi pagal bendrojo ugdymo programas</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox"
                            className="form-check-input"
                            name="guardianInSchool"
                            id="chkGuardianInSchool"
                            checked={this.state.child.acceptancePriorities.guardianInSchool}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="guardianInSchool">Vienas iš tėvų (globėjų) mokosi bendrojo ugdymo mokykloje</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input"
                            name="guardianDisability"
                            id="chkGuardianDisability"
                            checked={this.state.child.acceptancePriorities.guardianDisability}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="guardianDisability">Vienas iš tėvų (globėjų) turi ne daugiau kaip 40 procentų darbingumo lygio</label>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    /** Darzeliu prioritetu forma */
    kindergartenPriorityForm() {
        return (
            <div className="row">
                <div className="col">
                    <div className="form">
                        <div className="row">
                            <h6>Darželių prioritetas</h6>
                        </div>
                        <div className="row">
                            <p>Pasirinkite darželių prioritetą, daugiausiai leidžiamos 5 įstaigos.</p>
                        </div>
                        <div className="form-group">
                            <label htmlFor="1">1 prioritetas <span className="fieldRequired">*</span></label>
                            <Select
                                name="1"
                            />
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    /** Pagrindinio atstovo formos onChange */
    mainGuardianOnChange(e) {
        this.setState({
            mainGuardian: {
                ...this.state.mainGuardian,
                [e.target.name]: e.target.value
            }
        })
    }

    /** Antro atstovo formos onChange */
    additionalGuardianOnChange(e) {
        this.setState({
            additionalGuardian: {
                ...this.state.additionalGuardian,
                [e.target.name]: e.target.value
            }
        })
    }

    /** Vaiko formos onChange */
    childOnChange(e) {
        this.setState({
            child: {
                ...this.state.child,
                [e.target.name]: e.target.value
            }
        })
    }

    /** Checkbox onChange */
    checkboxOnChange(e) {
        this.setState({
            ...this.state,
            child: {
                ...this.state.child,
                acceptancePriorities: {
                    ...this.state.child.acceptancePriorities,
                    [e.target.name]: e.target.checked
                }
            }
        })
        console.log(this.state.child.acceptancePriorities);
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
                    <div className="col-1" />
                    <div className="col">
                        {/**Atstovas 2 */}
                        <form>
                            {
                                this.userForm(false)
                            }
                        </form>
                    </div>
                    <div className="col-1" />
                    <div className="col">
                        {/**Vaiko duomenys */}
                        <form>
                            {
                                this.childForm()
                            }
                        </form>
                    </div>
                </div>
                {/**Vaiko priemimo tvarkos prioritetai */}
                {
                    this.checkboxPriorityForm()
                }
                {
                    this.kindergartenPriorityForm()
                }
            </div>
        )
    }
}
