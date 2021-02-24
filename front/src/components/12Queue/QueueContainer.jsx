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
            isActive: false
        }
    }
    componentDidMount() {
        this.getApplications(this.state.currentPage, "");
        //TODO: set is active 
    }

    getApplications(currentPage, personalCode) {

        const { pageSize } = this.state;
        currentPage -= 1;

        var uri = `${apiEndpoint}/api/prasymai/manager?page=${currentPage}&size=${pageSize}`;

        if (personalCode !== "") {
            uri = `${apiEndpoint}/api/darzeliai/manager/page/${personalCode}?page=${currentPage}&size=${pageSize}`;

        }
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

    handleClick=(e)=>{
        console.log(e.currentTarget.value);
    }

    handleSearch = (e) => {

        console.log(e.currentTarget.value);

        const personalCode = e.currentTarget.value;
        this.setState({ searchQuery: personalCode });
        // this.getKindergartenInfo(1, personalCode);
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
        
        const placeholder = "Ieškoti pagal vaiko asmens kodą..."
        const { applications, totalElements, pageSize, searchQuery, isActive } = this.state;


        return (

            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Prašymų eilė</h6>
               {isActive && <p className="pl-2 pt-3">Registracija vykdoma</p>}
               {!isActive && <p className="pl-2 pt-3">Šiuo metu registracija nevykdoma</p>}

                <Buttons
                    onClick={this.handleClick}
                    isActive={isActive}
                />

                <SearchBox
                    value={searchQuery}
                    onSearch={this.handleSearch}
                    placeholder={placeholder}
                />

                <div className=" pt-2">


                    <QueueTable
                        applications={applications}
                        onDelete={this.handleDelete}
                    />

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