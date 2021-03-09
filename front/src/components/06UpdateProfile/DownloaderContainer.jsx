import React, { Component } from 'react';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

export class DownloaderContainer extends Component {

    getUserDetailsArchive() {

        const method = 'GET';
        const url = `${apiEndpoint}/api/users/zip`;

        http.request({
            url,
            method,
            responseType: 'blob'
        }).then(({ data }) => {

            const downloadUrl = window.URL.createObjectURL(new Blob([data]));

            const link = document.createElement('a');

            link.href = downloadUrl;
            link.setAttribute('download', 'naudotojas.zip');

          //  document.body.appendChild(link);
            link.click();
            link.remove();
        });
    }

    render() {

        return (

            <div className="pt-5" >
                
                <a href="/#" className="pl-2 pt-3" onClick={this.getUserDetailsArchive}>Atsisiųsti informaciją apie kaupiamus asmens duomenis (.zip)</a>

            </div>
        )
    }
}

export default DownloaderContainer