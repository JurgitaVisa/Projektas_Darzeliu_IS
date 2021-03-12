import React, { Component } from 'react';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';
import Spinner from '../08CommonComponents/Spinner'

import Pagination from './../08CommonComponents/Pagination';

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

        if (page < 0 ) page = 0;

        var uri = `${apiEndpoint}/admin/getjournal/page?page=${page}&size=${pageSize}`;

        http
            .get(uri)
            .then((response) => {
                this.setState({ entries: response.data,
                                entriesLoaded: true
                            });
                console.log(this.state.entries)
            })
            .catch(() => {});
    }

    render() {
        console.log(this.state.entriesLoaded)
        return (
            
            <div className="container pt-4" >

                <h6 className="pl-2 pt-3">Sistemos įvykių žurnalas</h6>
                {this.state.entriesLoaded ? 'užkrauta info' : (<Spinner />) }                
            </div>
        )
    }
}

export default EventJournalContainer
