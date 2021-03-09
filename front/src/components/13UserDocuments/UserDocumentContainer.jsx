import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';

export default class UserDocumentContainer extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            documentList: [],
            showUploadForm: false,
            documentToUpload: "",
        };
        this.uploadDocument = this.uploadDocument.bind(this);
        this.uploadForm = this.uploadForm.bind(this);
        this.uploadDocumentOnChange = this.uploadDocumentOnChange.bind(this);

    }
    
    uploadDocument(document) {
        const formData = new FormData();
        formData.append('name',document.name);
        formData.append('file',document);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        http.post(`${apiEndpoint}/api/documents/upload`, formData, config)
            .then((response) => {
                console.log("OK");
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            })
    }

    uploadDocumentOnChange(e) {
        const file = e.target.files[0];
        if(file.type === "application/pdf") {
            if(file.size <= 128000) {
                this.setState({documentToUpload: file});
            }
            else {
                swal({
                    text: "Pasirinktas failas yra per didelis",
                    icon: "error",
                })
            }
        }
        else {
            swal({
                text: "Pasirinktas failas yra ne .pdf",
                icon: "error",
            })
        }
    }

    uploadForm = () => {
        if(this.state.showUploadForm) {
            return (
                <div className="form">
                        <div className="form-group">
                            <h6 className="py-3">Pažyma privalo būti .pdf formato ir neužimti daugiau negu 128KB vietos!</h6>
                            <input 
                                type="file"
                                className="form-control-file"
                                id="inputUploadDocument"
                                onChange={this.uploadDocumentOnChange}
                            />
                        </div>
                        <div className="row">
                            <div className="col">
                                <button 
                                    id="btnUploadDocument"
                                    className="btn btn-primary"
                                    onClick={() => this.uploadDocument(this.state.documentToUpload)}
                                >
                                    Įkelti
                                </button>
                            </div>
                            {/**<div className="col">
                                <button
                                    id="btnCancelUpload"
                                    className="btn btn-danger"
                                    onClick={() => {this.setState({showUploadForm: false})}}
                                >
                                    Atšaukti
                                </button>
            </div>*/}
                            
                        </div>
                        
                </div>
            )
        }
        else {
            return (
                <div className="row">
                    <div className="col">
                        <button 
                            id="btnUploadDocument"
                            className="btn btn-primary"
                            onClick={() => {this.setState({...this.state, showUploadForm: true})}}
                        >
                            Įkelti naują
                        </button>
                    </div>
                </div>
            )
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h6 className="py-3"><b>Mano pažymos</b></h6>
                    </div>
                </div>
                {
                    //** Upload Form */
                    this.uploadForm()
                }
                {
                    //**UserDocumentList */
                }
            </div>
        )
    }
}
