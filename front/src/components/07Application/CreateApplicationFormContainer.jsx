import React, { Component } from 'react';
import Select from 'react-select';

import DatePicker from "react-datepicker";
import { registerLocale } from  "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import lt from 'date-fns/locale/lt';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';

import inputValidator from '../08CommonComponents/InputValidator';

import '../../App.css';
import '../08CommonComponents/datePickerStyle.css';
import { subYears } from 'date-fns';

registerLocale('lt', lt)

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
                username: ""
            },
            additionalGuardian: {
                name: "",
                surname: "",
                personalCode: "",
                phone: "",
                email: "",
                address: ""
            },
            birthdate: subYears(new Date(),1),
            childName: "",
            childPersonalCode: "",
            childSurname: "",
            kindergartenChoises: {
                kindergartenId1: "",
                kindergartenId2: "",
                kindergartenId3: "",
                kindergartenId4: "",
                kindergartenId5: ""
            },
            priorities: {
                childIsAdopted: false,
                familyHasThreeOrMoreChildrenInSchools: false,
                guardianDisability: false,
                guardianInSchool: false,
                livesInVilnius: false
            },
            kindergartenList: []
        }
        this.mainGuardianOnChange = this.mainGuardianOnChange.bind(this);
        this.additionalGuardianOnChange = this.additionalGuardianOnChange.bind(this);
        this.childOnChange = this.childOnChange.bind(this);
        this.checkboxOnChange = this.checkboxOnChange.bind(this);
        this.submitHandle = this.submitHandle.bind(this);
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
                        phone: response.data.phone.slice(3),
                        email: response.data.username,
                        address: response.data.address,
                        username: response.data.username,
                        role: response.data.role
                    }
                })
                /** get kindergarten list */
                var kindergartenList = [];
                http.get(`${apiEndpoint}/api/darzeliai`)
                    .then((response) => {
                        kindergartenList = response.data.map((k) => ({value: k.id, label: k.name + " (" + k.address + ")", disabled: "no"}));
                        this.setState({
                            kindergartenList
                        })
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
                        <h6 className="formHeader">Atstovas 1</h6>
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
                        <h6 className="formHeader">Atstovas 2</h6>
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
                        <h6 className="formHeader">Vaiko duomenys</h6>
                    </div>
                <div className="row form-group">
                    <label htmlFor="txtName">Vaiko vardas <span className="fieldRequired">*</span></label>
                    <input 
                        type="text"
                        id="txtChildName"
                        name="childName"
                        placeholder="Vaiko vardas"
                        className="form-control"
                        value={this.state.childName}
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
                        name="childSurname"
                        placeholder="Vaiko pavardė"
                        className="form-control"
                        value={this.state.childSurname}
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
                        name="childPersonalCode"
                        placeholder="Asmens kodas"
                        className="form-control"
                        value={this.state.childPersonalCode}
                        onChange={this.childOnChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[0-9]{11}"
                    />
                </div>
                {/** Gimimo data */}
                <div className="row form-group">
                    <label htmlFor="txtBirthdate">Gimimo data <span className="fieldRequired">*</span></label>
                    <DatePicker
                        className="form-control"
                        locale="lt"
                        dateFormat="yyyy/MM/dd"
                        selected={this.state.birthdate}
                        onChange={(e) => {
                            this.setState({birthdate: e})
                            }
                        }
                        minDate={subYears(new Date(), 6)}
                        maxDate={subYears(new Date(), 1)}
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
                                    <h6 className="formHeader">Vaiko priėmimo tvarkos prioritetai</h6>
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
                            checked={this.state.priorities.livesInVilnius}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="livesInVilnius">Vaiko deklaruojama gyvenamoji vieta yra Vilniaus miesto savivaldybė</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input" 
                            name="childIsAdopted" 
                            id="chkChildIsAdopted"
                            checked={this.state.priorities.childIsAdopted}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="childIsAdopted">Vaikas yra įvaikintas</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input"
                            name="familyHasThreeOrMoreChildrenInSchools"
                            id="chkFamilyHasThreeOrMoreChildrenInSchools"
                            checked={this.state.priorities.familyHasThreeOrMoreChildrenInSchools}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="familyHasThreeOrMoreChildrenInSchools">Šeima augina (globoja) tris ir daugiau vaikų, kurie mokosi pagal bendrojo ugdymo programas</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox"
                            className="form-check-input"
                            name="guardianInSchool"
                            id="chkGuardianInSchool"
                            checked={this.state.priorities.guardianInSchool}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="guardianInSchool">Vienas iš tėvų (globėjų) mokosi bendrojo ugdymo mokykloje</label>
                        </div>
                        <div className="form-check">
                            <input type="checkbox" 
                            className="form-check-input"
                            name="guardianDisability"
                            id="chkGuardianDisability"
                            checked={this.state.priorities.guardianDisability}
                            onChange={this.checkboxOnChange}/>
                            <label className="form-check-label" htmlFor="guardianDisability">Vienas iš tėvų (globėjų) turi ne daugiau kaip 40 procentų darbingumo lygio</label>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    /** Darzeliu sarasas i options formata */
    kindergartenListToSelect(kList, priorityFieldName) {
        var optionsList = kList.map((k) => ({value: k.id, label: k.name + " (" + k.address + ")", name: priorityFieldName}));
        return optionsList;
    }

    /** Darzeliu prioritetu forma */
    kindergartenPriorityForm() {
        return (
            <div className="row">
                <div className="col">
                    <div className="form">
                        <div className="row">
                            <h6 className="formHeader">Darželių prioritetas</h6>
                        </div>
                        <div className="row">
                            <p>Pasirinkite darželių prioritetą, daugiausiai leidžiamos 5 įstaigos.</p>
                        </div>
                        <div className="form-group">
                            <label htmlFor="kindergartenId1">1 prioritetas <span className="fieldRequired">*</span></label>
                            <Select
                                name="kindergartenId1"
                                placeholder="Pasirinkite darželį iš sąrašo"
                                options={this.state.kindergartenList}
                                onChange={(e) => {
                                            if(e.value !== this.state.kindergartenChoises.kindergartenId1) {
                                                const lastIdValue = this.state.kindergartenChoises.kindergartenId1;
                                                this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId1": e.value}})
                                                this.state.kindergartenList.forEach(element => {
                                                    if(element.value===lastIdValue) {
                                                        element.disabled = "no"
                                                    }
                                                })
                                            }
                                            this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId1": e.value}})
                                            this.state.kindergartenList.forEach(element => {
                                                if(element.value===e.value) {
                                                     element.disabled = "yes"
                                                }
                                            })
                                        }
                                }
                                isOptionDisabled={(option) => option.disabled === "yes"}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="kindergartenId2">2 prioritetas</label>
                            <Select
                                name="kindergartenId2"
                                placeholder="Pasirinkite darželį iš sąrašo"
                                options={this.state.kindergartenList}
                                onChange={(e) => {
                                            if(e.value !== this.state.kindergartenChoises.kindergartenId2) {
                                                const lastIdValue = this.state.kindergartenChoises.kindergartenId2;
                                                this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId2": e.value}})
                                                this.state.kindergartenList.forEach(element => {
                                                    if(element.value===lastIdValue) {
                                                        element.disabled = "no"
                                                    }
                                                })
                                            }
                                            this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId2": e.value}})
                                            this.state.kindergartenList.forEach(element => {
                                                if(element.value===e.value) {
                                                     element.disabled = "yes"
                                                }
                                            })
                                        }
                                }
                                isOptionDisabled={(option) => option.disabled === "yes"}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="kindergartenId3">3 prioritetas</label>
                            <Select
                                name="kindergartenId3"
                                placeholder="Pasirinkite darželį iš sąrašo"
                                options={this.state.kindergartenList}
                                onChange={(e) => {
                                            if(e.value !== this.state.kindergartenChoises.kindergartenId3) {
                                                const lastIdValue = this.state.kindergartenChoises.kindergartenId3;
                                                this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId3": e.value}})
                                                this.state.kindergartenList.forEach(element => {
                                                    if(element.value===lastIdValue) {
                                                        element.disabled = "no"
                                                    }
                                                })
                                            }
                                            this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId3": e.value}})
                                            this.state.kindergartenList.forEach(element => {
                                                if(element.value===e.value) {
                                                     element.disabled = "yes"
                                                }
                                            })
                                        }
                                }
                                isOptionDisabled={(option) => option.disabled === "yes"}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="kindergartenId4">4 prioritetas</label>
                            <Select
                                name="kindergartenId4"
                                placeholder="Pasirinkite darželį iš sąrašo"
                                options={this.state.kindergartenList}
                                onChange={(e) => {
                                            if(e.value !== this.state.kindergartenChoises.kindergartenId4) {
                                                const lastIdValue = this.state.kindergartenChoises.kindergartenId4;
                                                this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId4": e.value}})
                                                this.state.kindergartenList.forEach(element => {
                                                    if(element.value===lastIdValue) {
                                                        element.disabled = "no"
                                                    }
                                                })
                                            }
                                            this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId4": e.value}})
                                            this.state.kindergartenList.forEach(element => {
                                                if(element.value===e.value) {
                                                     element.disabled = "yes"
                                                }
                                            })
                                        }
                                }
                                isOptionDisabled={(option) => option.disabled === "yes"}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="kindergartenId5">5 prioritetas</label>
                            <Select
                                name="kindergartenId5"
                                placeholder="Pasirinkite darželį iš sąrašo"
                                options={this.state.kindergartenList}
                                onChange={(e) => {
                                            if(e.value !== this.state.kindergartenChoises.kindergartenId5) {
                                                const lastIdValue = this.state.kindergartenChoises.kindergartenId5;
                                                this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId5": e.value}})
                                                this.state.kindergartenList.forEach(element => {
                                                    if(element.value===lastIdValue) {
                                                        element.disabled = "no"
                                                    }
                                                })
                                            }
                                            this.setState({...this.state, kindergartenChoises: {...this.state.kindergartenChoises, "kindergartenId5": e.value}})
                                            this.state.kindergartenList.forEach(element => {
                                                if(element.value===e.value) {
                                                     element.disabled = "yes"
                                                }
                                            })
                                        }
                                }
                                isOptionDisabled={(option) => option.disabled === "yes"}
                            />
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    /** Pagrindinio atstovo formos onChange */
    mainGuardianOnChange(e) {
        inputValidator(e);
        this.setState({
            mainGuardian: {
                ...this.state.mainGuardian,
                [e.target.name]: e.target.value
            }
        })
    }

    /** Antro atstovo formos onChange */
    additionalGuardianOnChange(e) {
        inputValidator(e);
        this.setState({
            additionalGuardian: {
                ...this.state.additionalGuardian,
                [e.target.name]: e.target.value
            }
        })
    }

    /** Vaiko formos onChange */
    childOnChange(e) {
        inputValidator(e);
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    /** Checkbox onChange */
    checkboxOnChange(e) {
        this.setState({
            priorities: {
                ...this.state.priorities,
                [e.target.name]: e.target.checked
            }
        })
    }

    /** Handle submit */
    submitHandle(e) {
        e.preventDefault();
        if(!this.state.kindergartenChoises.kindergartenId1) {
            swal({
                title: "Įvyko klaida",
                text: "1 Prioritetas yra privalomas"
            })
            
        }
        else {
            http.post(`${apiEndpoint}/api/prasymai/user/new`, 
                {
                    "additionalGuardian": {
                        "address": this.state.additionalGuardian.address,
                        "email": this.state.additionalGuardian.email,
                        "name": this.state.additionalGuardian.name,
                        "personalCode": this.state.additionalGuardian.personalCode,
                        "phone": this.state.additionalGuardian.phone,
                        "surname": this.state.additionalGuardian.surname
                    },
                    "birthdate": this.state.birthdate.toLocaleDateString('en-CA'),
                    "childName": this.state.childName,
                    "childPersonalCode": this.state.childPersonalCode,
                    "childSurname": this.state.childSurname,
                    "kindergartenChoises": {
                        "kindergartenId1": this.state.kindergartenChoises.kindergartenId1,
                        "kindergartenId2": this.state.kindergartenChoises.kindergartenId2,
                        "kindergartenId3": this.state.kindergartenChoises.kindergartenId3,
                        "kindergartenId4": this.state.kindergartenChoises.kindergartenId4,
                        "kindergartenId5": this.state.kindergartenChoises.kindergartenId5,
                    },
                    "mainGuardian": {
                        "address": this.state.mainGuardian.address,
                        "email": this.state.mainGuardian.email,
                        "name": this.state.mainGuardian.name,
                        "personalCode": this.state.mainGuardian.personalCode,
                        "phone": this.state.mainGuardian.phone,
                        "role": this.state.mainGuardian.role,
                        "surname": this.state.mainGuardian.surname,
                        "username": this.state.mainGuardian.username
                    },
                    "priorities": {
                        "childIsAdopted": this.state.priorities.childIsAdopted,
                        "familyHasThreeOrMoreChildrenInSchools": this.state.priorities.familyHasThreeOrMoreChildrenInSchools,
                        "guardianDisability": this.state.priorities.guardianDisability,
                        "guardianInSchool": this.state.priorities.guardianInSchool,
                        "livesInVilnius": this.state.priorities.livesInVilnius
                      }
                }
                )
                .then((response) => {
                    swal({
                        title: "Užklausa atlikta sėkmingai",
                        text: response.text,
                        icon: "success",
                        button: "Gerai"
                    }).then(()=>{return window.location.href="/"})
                })
                .catch((error) => {
                    console.log(error)
                    swal({
                        title: "Įvyko klaida",
                        text: error.response.data,
                        icon: "warning",
                        button: "Gerai"
                    })
                })
        }
    }

    render() {
        return (
            <div className="container pt-4">
                <div className="form">
                    <form onSubmit={this.submitHandle}>
                        <div className="row">
                            <div className="col-3">
                                {
                                    /** Atstovas 1 */
                                    this.userForm(true)
                                }
                            </div>
                            <div className="col" />
                            <div className="col-3">
                                {
                                    /** Atstovas 2 */
                                    this.userForm(false)
                                }
                            </div>
                            <div className="col" />
                            <div className="col-3">
                                {
                                    /** Vaiko forma */
                                    this.childForm()
                                }
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                {
                                    this.checkboxPriorityForm()
                                }
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-6">
                                {
                                    this.kindergartenPriorityForm()
                                }
                            </div>
                        </div>
                        <div className="row">
                            <p>Dėmesio! Naujas prašymas anuliuoja prieš tai esantį aktyvų prašymą. Jei pirmu numeriu nurodytoje įstaigoje nėra laisvų vietų, vieta skiriama antru numeriu pažymėtoje įstaigoje, jei joje yra laisvų vietų ir t. t. Jeigu visuose prašyme pažymėtose įstaigose nėra laisvų vietų, prašymas lieka laukiančiųjų eilėje.</p>
                        </div>
                        <div className="row">
                            <button type="submit" className="btn btn-primary">Sukurti prašymą</button> 
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}
