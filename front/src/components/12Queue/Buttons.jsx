import React from 'react';

import '../../App.css';

const Buttons = (props) => {
    const { onClick, isActive } = props;

    if (isActive) {
        
        return (
            <div className="py-3">
                <button
                    value='Off'
                    onClick={(e) => onClick(e)}
                    id="btnStopRegistration"
                    className="btn btn-outline-danger btn-sm">
                    Stabdyti registraciją
                </button>

            </div>
        )

    } else {
        
        return (
            <div className="row py-3">
                <div className="col-8">
                    <button
                        value='On'
                        onClick={(e) => onClick(e)}
                        id="btnStartRegistration"
                        className="btn btn-outline-primary btn-sm ml-2">
                        Pradėti registraciją
                        </button>
                    <button
                        value='Process'
                        onClick={(e) => onClick(e)}
                        id="btnFormQueue"
                        className="btn btn-primary btn-sm ml-2">
                        Formuoti eiles
                        </button>
                </div>

                <div className="col-4">
                    <button
                        value='Confirm'
                        onClick={(e) => onClick(e)}
                        id="btnConfirmQueue"
                        className="btn btn-secondary btn-sm float-right">
                        Patvirtinti eiles
                        </button>
                </div>
            </div >


        )

    }


}

export default Buttons;