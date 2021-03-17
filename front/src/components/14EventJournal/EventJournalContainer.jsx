import React, { Component } from 'react';
import Pagination from "react-js-pagination";

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';
import Spinner from '../08CommonComponents/Spinner'

import EventJournalTable from './EventJournalTable';

export class EventJournalContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            entries: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            entriesLoaded: false,
        }
    }
    componentDidMount() {
        this.getJournalEntries(this.state.currentPage);
    }

    getJournalEntries(currentPage) {

        const { pageSize } = this.state;
        let page = currentPage - 1;

        if (page < 0) page = 0;

        var uri = `${apiEndpoint}/admin/getjournal/page?page=${page}&size=${pageSize}`;

        http
            .get(uri)
            .then((response) => {
                this.setState({
                    entries: response.data.content.map((entry) => ({
                        ...entry,
                        id: entry.entryID
                    })),
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1,
                    entriesLoaded: true
                });
            })
            .catch(() => { });
    }

    handlePageChange = (page) => {

        this.setState({ currentPage: page });
        this.getJournalEntries(page);
    };

    render() {
        return (

            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Sistemos įvykių žurnalas</h6>
                {this.state.entriesLoaded ? (
                    <EventJournalTable entries={this.state.entries} />
                ) : (<Spinner />)}

                <div className="d-flex justify-content-center">
                    <Pagination
                        itemClass="page-item"
                        linkClass="page-link"
                        activePage={this.state.currentPage}
                        itemsCountPerPage={this.state.pageSize}
                        totalItemsCount={this.state.totalElements}
                        pageRangeDisplayed={15}
                        onChange={this.handlePageChange.bind(this)}
                    />
                </div>
               
            </div>
        )
    }
}

export default EventJournalContainer
