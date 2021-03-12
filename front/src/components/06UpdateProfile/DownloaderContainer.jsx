import React, { Component } from 'react';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';

class DownloaderContainer extends Component {

    getUserDetailsArchive() {

        const method = 'GET';
        const url = `${apiEndpoint}/api/users/user/zip`;

        http.request({
            url,
            method,
            responseType: 'blob'
        }).then(({ data }) => {

            const downloadUrl = window.URL.createObjectURL(new Blob([data]));

            const link = document.createElement('a');

            link.href = downloadUrl;
            link.setAttribute('download', 'naudotojas.zip');

            document.body.appendChild(link);
            link.click();
            link.remove();

            window.document.activeElement.blur();

        }).catch((error) => {
            console.log(error);
        });
    }

    render() {

        return (
            
            <button className="btn btn-link pt-5 px-0" onClick={this.getUserDetailsArchive} id="btnGetUserZip">Atsisiųsti informaciją apie kaupiamus asmens duomenis (.zip)</button>

        )
    }
}

export default DownloaderContainer