import React, { Component } from 'react';

import '../../App.css';

// import http from '../10Services/httpService';
// import apiEndpoint from '../10Services/endpoint';

import AdminCreateUser from '../04Admin/AdminCreateUser';
import { UserListContainer } from './UserListContainer';

export class AdminContainer extends Component {


    render() {
        return (
            <div>
                <div className="container pt-4">
                    
                    <div className="row ">
                        <div className="bg-light pb-3 col-12 col-sm-12 col-md-12 col-lg-3">
                            <AdminCreateUser />
                        </div>

                        <div className="col-12 col-sm-12 col-md-12 col-lg-9 pt-1">
                            <UserListContainer />
                        </div>


                    </div>

                </div>


            </div>
        )
    }
}

export default AdminContainer
