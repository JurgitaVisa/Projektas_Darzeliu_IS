import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

//import NavBar from '../00Navigation/NavBar';
import UserListTable from './UserListTable';
import Pagination from './../08CommonComponents/Pagination';
import { paginate } from './../08CommonComponents/paginate';


export class UserListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            naudotojai: [],
            pageSize: 10,
            currentPage: 1            
        }
    }
    componentDidMount() {
        http
            .get(`${apiEndpoint}/api/users/admin/allusers`)
            .then((response) => {
                this.setState({ naudotojai: response.data });

            }).catch(error => {
                console.log("Naudotojai container error", error.response);
                if (error && error.response.status === 401)
                    swal("Puslapis pasiekiamas tik administratoriaus teises turintiems naudotojams")
                this.props.history.replace("/home");
            }
            );

    }

    handleDelete = (item) => {
        const username = item.username;
        console.log("Trinti naudotoja", username);

        http
            .delete(`${apiEndpoint}/api/users/admin/delete/${username}`)
            .then((response) => {
                alert(response.data);
                http
                    .get(`${apiEndpoint}/api/users/admin/allusers`)
                    .then((response) => {
                        this.setState({ naudotojai: response.data });

                    });
            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) swal("Veiksmas neleidžiamas");

            }
            );

    }

    handleRestorePassword = (item) => {
        const username = item.username;
        console.log("Atstatyti slaptazodi naudotojui", username);

        http
            .put(`${apiEndpoint}/api/users/admin/password/${username}`)
            .then((response) => {
                alert(response.data);
            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) swal("Veiksmas neleidžiamas");
            }
            );

    }

    handlePageChange = (page) => {
        this.setState({ currentPage: page });
    };

    
    getPageData = () => {
        const {
            pageSize,
            currentPage,           
            naudotojai: allData
        } = this.state;         

        const naudotojai = paginate(allData, currentPage, pageSize);

        return { totalCount: allData.length, naudotojai: naudotojai }
    }

    render() {

        const { totalCount, naudotojai } = this.getPageData();
       
        return (
            <div >
                {/* <NavBar /> */}
                <div className="container">
                    {/* <div className="row ">
                        <div className="col-12 pb-2">
                            <h5 className="h5">Sistemos naudotojų sąrašas </h5>

                        </div>
                    </div> */}

                    {/* <Link to="/admin" className="btn btn-outline-primary my-2">Sukurti naują</Link> */}
                    <br />
                    <UserListTable
                        naudotojai={naudotojai}
                        onDelete={this.handleDelete}
                        onRestorePassword={this.handleRestorePassword}
                    />

                    <Pagination
                        itemsCount={totalCount}
                        pageSize={this.state.pageSize}
                        onPageChange={this.handlePageChange}
                        currentPage={this.state.currentPage}
                    />

                </div>
            </div>
        )
    }
}

export default UserListContainer