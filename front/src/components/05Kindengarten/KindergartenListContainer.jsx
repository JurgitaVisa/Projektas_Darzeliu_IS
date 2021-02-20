import React, { Component } from 'react';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import KindergartenListTable from './KindergartenListTable';
import Pagination from './../08CommonComponents/Pagination';
import SearchBox from './../08CommonComponents/SeachBox';
export class KindergartenListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            darzeliai: [],
            elderates: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            inEditMode: false,
            editRowId: "",
            editedKindergarten: null,
            errorMessages: {}
        }
    }
    componentDidMount() {
        this.getKindergartenInfo(this.state.currentPage, "");
        this.getElderates();
        document.addEventListener("keydown", this.handleEscape, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleEscape, false);
    }

    handleEscape = (e) => {
        if (e.key === 'Escape') {
            this.onCancel();
            //this.props.history.push("/darzeliai");  
            setTimeout(function () {
                window.location.reload();
            }, 10);
        }
    }


    getKindergartenInfo(currentPage, name) {

        const { pageSize } = this.state;
        currentPage -= 1;

        var uri = `${apiEndpoint}/api/darzeliai/manager/page?page=${currentPage}&size=${pageSize}`;

        if (name !== "") {
            uri = `${apiEndpoint}/api/darzeliai/manager/page/${name}?page=${currentPage}&size=${pageSize}`;

        }

        http
            .get(uri)
            .then((response) => {

                this.setState({
                    darzeliai: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

            }).catch(error => {
                console.log(error);
                console.log("Darzeliai container error", error.response);
                if (error && error.response.status === 401)

                    swal({
                        text: "Puslapis pasiekiamas tik teises turintiems naudotojams",
                        button: "Gerai"
                    });
                // this.props.history.replace("/home");
            }
            );
    }

    getElderates() {
        http
            .get(`${apiEndpoint}/api/darzeliai/manager/elderates`)
            .then((response) => {
                this.setState({ elderates: response.data });
            })
            .catch((error) => {
                console.log("Darzeliai container error", error.response);
                if (error && error.response.status === 401)

                    swal({
                        text: "Puslapis pasiekiamas tik teises turintiems naudotojams",
                        button: "Gerai"
                    });
            });
    }

    handleSearch = (e) => {

        const name = e.currentTarget.value;
        this.setState({ searchQuery: name });
        this.getKindergartenInfo(1, name);
    }

    handleDelete = (item) => {

        swal({
            text: "Ar tikrai norite ištrinti darželį?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const id = item.id;
                const { currentPage, numberOfElements } = this.state;
                const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
                console.log("Trinti darzeli", id);

                http
                    .delete(`${apiEndpoint}/api/darzeliai/manager/delete/${id}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                        this.getKindergartenInfo(page, "");

                    }).catch(error => {
                        if (error && error.response.status > 400 && error.response.status < 500)
                            swal({
                                text: "Veiksmas neleidžiamas",
                                button: "Gerai"
                            });
                    });
            }
        });
    }

    handleEditKindergarten = (item) => {

        this.setState({
            inEditMode: true,
            editRowId: item.id,
            editedKindergarten: item
        });
    }

    onCancel = () => {
        this.setState(
            {
                inEditMode: false,
                editRowId: "",
                editedKindergarten: null
            }
        )
    }

    handleChange = ({ target: input }) => {

        const errorMessages = this.state.errorMessages;

        if (input.validity.valueMissing || input.validity.patternMismatch) {
            errorMessages[input.name] = `*${input.title}`;
        } else {
            delete errorMessages[input.name];
        }
        const kindergarten = this.state.editedKindergarten;
        kindergarten[input.name] = input.value;
        this.setState({
            editedKindergarten: kindergarten,
            errorMessages: errorMessages
        });
    }

    handleSaveEdited = () => {
        const { editedKindergarten, editRowId, errorMessages } = this.state;

        console.log("Koreguoti axios.put", editRowId, editedKindergarten)

        if (Object.keys(errorMessages).length === 0) {
            http.put(`${apiEndpoint}/api/darzeliai/manager/update/${editRowId}`, editedKindergarten)
                .then(() => {
                    this.onCancel();
                    this.getKindergartenInfo(this.state.currentPage, this.state.searchQuery);
                }).catch(error => {
                    if (error && error.response.status === 409) {
                        swal({
                            text: error.response.data,
                            button: "Gerai"
                        });
                    }
                    else if (error && error.response.status > 400 && error.response.status < 500)
                        swal({
                            text: "Veiksmas neleidžiamas",
                            button: "Gerai"
                        });
                })
        }
    }


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getKindergartenInfo(page, this.state.searchQuery);
    };



    render() {

        const { darzeliai, elderates, totalElements, pageSize, searchQuery, inEditMode, editRowId, errorMessages } = this.state;

        const hasErrors = Object.keys(errorMessages).length === 0 ? false : true;

        return (
            <React.Fragment>

                <SearchBox
                    value={searchQuery}
                    onSearch={this.handleSearch}
                />

                <KindergartenListTable
                    darzeliai={darzeliai}
                    elderates={elderates}
                    inEditMode={inEditMode}
                    editRowId={editRowId}
                    errorMessages={errorMessages}
                    hasErrors={hasErrors}
                    onDelete={this.handleDelete}
                    onEditData={this.handleEditKindergarten}
                    onEscape={this.handleEscape}
                    onChange={this.handleChange}
                    onSave={this.handleSaveEdited}
                />

                <Pagination
                    itemsCount={totalElements}
                    pageSize={pageSize}
                    onPageChange={this.handlePageChange}
                    currentPage={this.state.currentPage}
                />


            </React.Fragment>
        )
    }
}

export default KindergartenListContainer;