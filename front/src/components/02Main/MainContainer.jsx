import React, { Component } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFilePdf } from '@fortawesome/free-solid-svg-icons';

import NavBar from '../00Navigation/NavBar';

class MainContainer extends Component {
    render() {
        return (
            <div >
                <NavBar />
                <div className="container">
                    <h5 className="h5">Sveiki prisijungę prie darželių IS</h5>
                    <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dolorum neque sed, sunt voluptatum tempore esse, corrupti alias iure tenetur eum perferendis, eius non accusamus nemo sequi sit. Ab, enim eos?</p>
                    <br/>
                    <FontAwesomeIcon icon={faFilePdf} size="6x" className="text-primary" /> 
                    <p>Atsisiųsti naudotojo instrukciją</p>
                </div>
            </div>
        )
    }
}

export default MainContainer
