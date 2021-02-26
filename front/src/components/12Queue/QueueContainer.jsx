import React, { Component } from 'react';
import swal from 'sweetalert';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

import QueueTable from './QueueTable';
import Pagination from '../08CommonComponents/Pagination';
import SearchBox from './../08CommonComponents/SeachBox';
import Buttons from './Buttons';

export class QueueContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            applications: [],
            pageSize: 12,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            isActive: false,
            currentButtonValue: ""
        }
    }
    componentDidMount() {
        this.getApplicationState();

    }

    getApplicationState() {
        http
            .get(`${apiEndpoint}/api/status`)
            .then((response) => {
                let buttonValue = response.data ? "On" : "Off"
                this.setState(
                    {
                        isActive: response.data,
                        currentButtonValue: buttonValue
                    },
                    function () {
                        this.getApplications(this.state.currentPage, "");
                    }
                );
            }).catch(error => {
                console.log("Queue status error", error);
            });
    }

    getApplications(currentPage, personalCode) {

        const { pageSize, isActive } = this.state;
        currentPage -= 1;

        console.log("Ar aktyvus statusas", isActive);

        if (isActive) {
            var uri = `${apiEndpoint}/api/prasymai/manager?page=${currentPage}&size=${pageSize}`;

            if (personalCode !== "") {
                uri = `${apiEndpoint}/api/prasymai/manager/page/${personalCode}?page=${currentPage}&size=${pageSize}`;

            }
        } else {
            // var uri = `${apiEndpoint}/api/prasymai/manager?page=${currentPage}&size=${pageSize}`;

            // if (personalCode !== "") {
            //     uri = `${apiEndpoint}/api/prasymai/manager/page/${personalCode}?page=${currentPage}&size=${pageSize}`;

            // }

        }

        console.log("**URI**", uri);
        if (uri) {
            http
                .get(uri)
                .then((response) => {

                    this.setState({
                        applications: response.data.content,
                        totalPages: response.data.totalPages,
                        totalElements: response.data.totalElements,
                        numberOfElements: response.data.numberOfElements,
                        currentPage: response.data.number + 1
                    });

                }).catch(error => {
                    console.log("Queue container error", error);
                });

        }

    }

    resetState() {
        this.setState({
            applications: [],
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: ""
        });
    }

    handleClick = (e) => {
        const buttonValue = e.currentTarget.value;
        console.log(buttonValue);

        if (buttonValue !== this.state.currentButtonValue) {
            this.resetState();

            if (buttonValue === "On") {
                http.post(`${apiEndpoint}/api/status/${true}`)
                    .then(() => {
                        this.setState({
                            isActive: true,
                            currentButtonValue: buttonValue
                        }, function () {
                            this.getApplications(1, "");
                        });
                    });

            } else {
                http.post(`${apiEndpoint}/api/status/${false}`)
                    .then(() => {
                        this.setState({
                            isActive: false,
                            currentButtonValue: buttonValue
                        }, function () {
                            this.getApplications(1, "");
                        });
                    })
            }
        }
    }

    handleProcessQueue = () => {
        const buttonValue = "Process";

        if (buttonValue !== this.state.currentButtonValue) {
            this.resetState();

            http.post(`${apiEndpoint}/api/queue/process`)
                .then((response) => {
                    alert(response.data);
                    this.setState({
                        currentButtonValue: buttonValue
                    }, function () {
                        this.getApplications(1, "");
                    });
                })
        }
    }

    handleConfirmQueue = () => {
        const buttonValue = "Confirm";

        if (buttonValue !== this.state.currentButtonValue) {
            swal({
                text: "Ar tikrai norite patvirtinti eiles?\nPo patvirtinimo bus automatiškai išsiųsti pranešimai globėjams.",
                buttons: ["Ne", "Taip"],
                dangerMode: true,
            }).then((actionConfirmed) => {
                if (actionConfirmed) {
                    this.resetState();

                    http.post(`${apiEndpoint}/api/queue/confirm`)
                        .then((response) => {
                            alert(response.data);
                            this.setState({
                                currentButtonValue: buttonValue
                            }, function () {
                                this.getApplications(1, "");
                            });
                        });
                }
            })
        }
    }

    handleSearch = (e) => {

        console.log(e.currentTarget.value);

        const personalCode = e.currentTarget.value;
        this.setState({ searchQuery: personalCode });
        this.getApplications(1, personalCode);
    }

    handleDelete = (item) => {

        swal({
            text: "Ar tikrai norite ištrinti prašymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const id = item.id;
                const { currentPage, numberOfElements } = this.state;
                const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
                console.log("Trinti prašymą", id);

                http
                    .delete(`${apiEndpoint}/api/prasymai/user/delete/${id}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                        this.getApplications(page, "");

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


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getApplications(page, this.state.searchQuery);
    };


    render() {

        const size = this.state.applications.length;

        const { applications, totalElements, pageSize, searchQuery, isActive, currentButtonValue } = this.state;

        const placeholder = "Ieškoti pagal vaiko asmens kodą..."


        return (

            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Prašymų eilė</h6>
                {isActive && <p className="pl-2 pt-3">Registracija vykdoma</p>}
                {!isActive && <p className="pl-2 pt-3">Šiuo metu registracija nevykdoma</p>}

                <Buttons
                    onClick={this.handleClick}
                    onProcess={this.handleProcessQueue}
                    onConfirm={this.handleConfirmQueue}
                    isActive={isActive}
                    currentButtonValue={currentButtonValue}
                    size={size}
                />

                {(size > 0 || searchQuery !== "") &&
                    <SearchBox
                        value={searchQuery}
                        onSearch={this.handleSearch}
                        placeholder={placeholder}
                    />
                }

                <div className=" ">

                    {isActive &&
                        <QueueTable
                            applications={applications}
                            onDelete={this.handleDelete}
                        />
                    }

                    <Pagination
                        itemsCount={totalElements}
                        pageSize={pageSize}
                        onPageChange={this.handlePageChange}
                        currentPage={this.state.currentPage}
                    />

                </div>
            </div>
        )
    }
}

export default QueueContainer