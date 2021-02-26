import React from 'react';

import '../../App.css';

const Buttons = (props) => {
    const { isActive, size, currentButtonValue, onClick, onProcess, onConfirm } = props;

    const alignment = size > 0 ? "btn btn-secondary btn-sm float-right" : "btn btn-secondary btn-sm float-left"

    if (isActive) {

        return (
            <div className="py-1">
                <button
                    value='Off'
                    onClick={(e) => onClick(e)}
                    id="btnStopRegistration"
                    className="btn btn-outline-danger btn-sm ml-2">
                    Stabdyti registraciją
                </button>
            </div>
        )

    } else {

        return (
            <div className="row py-3">

                <button
                    value='On'
                    onClick={(e) => onClick(e)}
                    id="btnStartRegistration"
                    className="btn btn-outline-primary btn-sm ml-3">
                    Pradėti registraciją
                    </button>

                <button
                    value='Process'
                    onClick={() => onProcess()}
                    id="btnFormQueue"
                    disabled={currentButtonValue === "Process"}
                    className="btn btn-primary btn-sm mx-2">
                    Formuoti eiles
                    </button>

                <button
                    value='Confirm'
                    onClick={() => onConfirm()}
                    id="btnConfirmQueue"
                    disabled={currentButtonValue === "Confirm"}
                    className={alignment}>
                    Patvirtinti eiles
                    </button>

            </div >

        )
    }
}

export default Buttons;