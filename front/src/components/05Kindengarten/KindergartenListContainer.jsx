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
<<<<<<< HEAD
            totalElements: 0,
            numberOfElements: 0
=======
            totalElements: 0
>>>>>>> INTL-93_DarzelisBack
        }
    }
    componentDidMount() {

        this.getKindergartenInfo(this.state.currentPage);

    }

    getKindergartenInfo(currentPage) {
        const { pageSize } = this.state;
        currentPage -= 1;
        http
<<<<<<< HEAD
            .get(`${apiEndpoint}/api/darzeliai/page?page=${currentPage}&size=${pageSize}`)
            .then((response) => {

                this.setState({
                    darzeliai: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
=======
            .get(`${apiEndpoint}/api/darzeliai/page?page%20data=${currentPage}&size=${pageSize}`)
            .then((response) => {                
                this.setState({ 
                    darzeliai: response.data.content, 
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    currentPage: response.data.number+1                
>>>>>>> INTL-93_DarzelisBack
                });

            }).catch(error => {
                console.log("Darzeliai container error", error.response);
                if (error && error.response.status === 401)
                    swal("Puslapis pasiekiamas tik teises turintiems naudotojams")
<<<<<<< HEAD
                // this.props.history.replace("/home");
=======
               // this.props.history.replace("/home");
>>>>>>> INTL-93_DarzelisBack
            }
            );
    }

    handleDelete = (item) => {
        const id = item.id;
<<<<<<< HEAD
        const { currentPage, numberOfElements } = this.state;
        const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
        console.log("Trinti darzeli", id);

=======
        console.log("Trinti darzeli", id);
//TODO kai trina paskutinį elementą tame lape, turėtų mesti į ankstesnį psl
>>>>>>> INTL-93_DarzelisBack
        http
            .delete(`${apiEndpoint}/api/darzeliai/manager/delete/${id}`)
            .then((response) => {
                swal(response.data);
<<<<<<< HEAD
                this.getKindergartenInfo(page);
=======
                this.getKindergartenInfo(this.state.currentPage);
>>>>>>> INTL-93_DarzelisBack

            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) swal("Veiksmas neleidžiamas");

            });
<<<<<<< HEAD
    }

    handleEditKindergarten = (item) => {
=======

    }

    handleEditKindergarten = (item) =>{
>>>>>>> INTL-93_DarzelisBack
        const id = item.id;
        console.log("Taisyti darzeli", id);
    }


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getKindergartenInfo(page);
<<<<<<< HEAD
    };
=======
    };    
>>>>>>> INTL-93_DarzelisBack

    render() {

        const { totalElements, pageSize, darzeliai } = this.state;

        return (
<<<<<<< HEAD
            <div className="container">
=======
            <div >
>>>>>>> INTL-93_DarzelisBack

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