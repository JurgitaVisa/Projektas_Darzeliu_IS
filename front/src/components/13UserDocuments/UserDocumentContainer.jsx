import React, { Component } from 'react'

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';
import UserDocumentListTable from './UserDocumentListTable';

export default class UserDocumentContainer extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            documentList: [],
            showUploadForm: false,
            documentToUpload: "",
            documentValid: false,
        };
        this.uploadDocument = this.uploadDocument.bind(this);
        this.uploadForm = this.uploadForm.bind(this);
        this.uploadDocumentOnChange = this.uploadDocumentOnChange.bind(this);
        this.getDocuments = this.getDocuments.bind(this);
    }
    
    componentDidMount() {
        this.getDocuments();
    }

    getDocuments() {
        http.get(`${apiEndpoint}/api/documents/documents`)
            .then((response) => {
                this.setState({
                    documentList: response.data
                })
                console.log(this.state);
            })
            .catch((error) => {
                console.log(error);
            })
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
                this.getDocuments();
                swal({
                    text: "Pažyma buvo įkelta sėkmingai",
                    buttons: "Gerai"
                })
                this.setState({showUploadForm: false, documentToUpload: ""})
            })
            .catch((error) => {
                console.log(error);
                swal({
                    text: "Įvyko klaida įkeliant pažymą"
                })
            })
    }

    validateDocument = (doc) => {
        if(doc.type === "application/pdf" && doc.size <= 128000) {
            this.setState({documentValid: true});
        }
        else {
            this.setState({documentValid: false});
        }
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
        this.validateDocument(file);
    }

    uploadForm = () => {
        if(this.state.showUploadForm) {
            return (
                <div className="form">
                    <div className="form-group">
                        <h6 className="py-3">Pažyma privalo būti .pdf formato ir neužimti daugiau negu 128KB vietos.</h6>
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
                                disabled={!this.state.documentValid}
                            >
                                Įkelti
                            </button>
                            <button
                                id="btnCancelUpload"
                                className="btn btn-secondary"
                                style={{marginLeft: "64px"}}
                                onClick={() => {this.setState({showUploadForm: false})}}
                            >
                                Atšaukti
                            </button>
                        </div>   
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
                            onClick={() => {this.setState({showUploadForm: true})}}
                        >
                            Įkelti naują
                        </button>
                    </div>
                </div>
            )
        }
    }

    handleDelete = (document) => {
        swal({
            text: "Ar tikrai norite ištrinti pažymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if(actionConfirmed) {
                http.delete(`${apiEndpoint}/api/documents/delete/${document.documentId}`)
                    .then((response) => {
                        this.getDocuments();
                        swal({
                            text: "Pažyma buvo sėkmingai ištrinta",
                            button: "Gerai"
                        })
                    })
                    .catch((error) => {
                        console.log(error);
                        swal({
                            text: "Įvyko klaida",
                            button: "Gerai"
                        })
                    })
            }
        })
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
                    <UserDocumentListTable
                        documents={this.state.documentList}
                        onDelete={this.handleDelete}
                    />
                }
            </div>
        )
    }
}
