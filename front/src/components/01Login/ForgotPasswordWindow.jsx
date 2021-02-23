import swal from "sweetalert";

import React from 'react';
import swalReact from '@sweetalert/with-react';

import InputValidator from '../08CommonComponents/InputValidator';

let formData = {
    email: "test@test.com",
}

export default function ForgotPasswordWindow() {
    console.log(formData)
    return (
        swalReact(
            <div style={{padding: "34px"}}>
                <h1>Slaptažodžio atstatymas</h1>
                <form>
                    <div className="form-group">
                        <label htmlFor="email">Paskyros el. paštas <span className="fieldRequired">*</span></label>
                    <input
                        type="email"
                        name="email"
                        className="form-control"
                        placeholder="Paskyros el. paštas"
                        value={formData.email}
                    />
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary float-right"
                    >
                        Atstatyti
                    </button>
                </form>
            </div>,
            {
                buttons: false
            }
        )
    )
}