import React, { Component } from 'react';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import KindergartenListTable from './KindergartenListTable';
import Pagination from './../08CommonComponents/Pagination';


export class KindergartenListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            darzeliai: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0
        }
    }
    componentDidMount() {

        this.getKindergartenInfo(this.state.currentPage);

    }

    getKindergartenInfo(currentPage) {
        const { pageSize } = this.state;
        currentPage -= 1;
        http
            .get(`${apiEndpoint}/api/darzeliai/page?page=${currentPage}&size=${pageSize}`)
            .then((response) => {

                this.setState({
                    darzeliai: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

            }).catch(error => {
                console.log("Darzeliai container error", error.response);
                if (error && error.response.status === 401)
                    swal("Puslapis pasiekiamas tik teises turintiems naudotojams")
                // this.props.history.replace("/home");
            }
            );
    }

    handleDelete = (item) => {
        const id = item.id;
        const { currentPage, numberOfElements } = this.state;
        const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
        console.log("Trinti darzeli", id);

        http
            .delete(`${apiEndpoint}/api/darzeliai/manager/delete/${id}`)
            .then((response) => {
                swal(response.data);
                this.getKindergartenInfo(page);

            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) swal("Veiksmas neleidžiamas");

            });
    }

    handleEditKindergarten = (item) => {
        const id = item.id;
        console.log("Taisyti darzeli", id);
    }


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getKindergartenInfo(page);
    };

    render() {

        const { totalElements, pageSize, darzeliai } = this.state;

        return (
            <div className="container">

                <KindergartenListTable
                    darzeliai={darzeliai}
                    onDelete={this.handleDelete}
                    onEditData={this.handleEditKindergarten}
                />

                <Pagination
                    itemsCount={totalElements}
                    pageSize={pageSize}
                    onPageChange={this.handlePageChange}
                    currentPage={this.state.currentPage}
                />


            </div>
        )
    }
}

export default KindergartenListContainer