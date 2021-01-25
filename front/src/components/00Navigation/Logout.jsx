import React from 'react'


export default function Logout({onLogout}) {
    return (
        <div>
             <button onClick={onLogout} className="btn btn-outline-primary m-1" >Atsijungti</button>
        </div>
    )
}
