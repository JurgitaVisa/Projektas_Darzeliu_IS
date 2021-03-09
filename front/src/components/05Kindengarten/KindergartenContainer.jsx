import React, { Component } from 'react';

import '../../App.css';

import { KindergartenListContainer } from './KindergartenListContainer';
import KindergartenInputForm from './KindergartenInputForm';

export class KindergartenContainer extends Component {

    render() {
        return (
            <div>
                <div className="container pt-4">

                    <div className="row ">
                        <div className="bg-light pb-3 col-12 col-sm-12 col-md-12 col-lg-3 pt-1">
                            <KindergartenInputForm />
                        </div>

                        <div className="col-12 col-sm-12 col-md-12 col-lg-9 pt-1">
                            <h6 className="py-3"><b>Darželių sąrašas su laisvų vietų grupėse skaičiais</b></h6>
                            <KindergartenListContainer />
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default KindergartenContainer;
