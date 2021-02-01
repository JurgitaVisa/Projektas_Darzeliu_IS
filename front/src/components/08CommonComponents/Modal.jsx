import React from 'react'

const Modal = ({ item, onDelete }) => {
    return (
        <div className="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">

                    <div className="modal-body">
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        Ar tikrai norite trinti?
                    </div>
                    <div className="modal-footer">
                        <button onClick={() => onDelete(item)} type="button" data-dismiss="modal" className="btn btn-danger">Trinti</button>
                        <button type="button" className="btn btn-secondary" data-dismiss="modal">Atšaukti</button>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Modal
