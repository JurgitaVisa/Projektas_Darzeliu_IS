import React, { Component } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFilePdf } from '@fortawesome/free-solid-svg-icons';

import NavBar from '../00Navigation/NavBar';
import http from '../10Services/httpService';
import apiEndpoint from './../10Services/endpoint';

class MainContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: ""
        }
    }
    componentDidMount() {
        http
            .get(`${apiEndpoint}/api/loggedUser`)
            .then((response) => {
                console.log(response);
                this.setState({ currentUser: response.data });

            }).catch(error => {
                console.log("Main container error", error.response);
                // if (error && error.response.status === 401)
                //     alert("Puslapis pasiekiamas tik administratoriaus teises turintiems naudotojams")
                this.props.history.replace("/");
            }
            );

    }


    render() {
        return (
            <div >
                <NavBar />
                <div className="container">
                    <h5 className="h5 pb-3">Sveiki prisijungę prie darželių IS</h5>
                    <h6 className="h6 pb-3">Prisijungęs naudotojas: <span className="text-primary">{this.state.currentUser} </span></h6>

                    <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dolorum neque sed, sunt voluptatum tempore esse, corrupti alias iure tenetur eum perferendis, eius non accusamus nemo sequi sit. Ab, enim eos?</p>
                    <br/>
                    <br/>
                    <FontAwesomeIcon icon={faFilePdf} size="5x" className="text-primary" /> 
                    <p>Atsisiųsti naudotojo instrukciją</p>
                </div>
            </div>
        )
    }
}

export default MainContainer
