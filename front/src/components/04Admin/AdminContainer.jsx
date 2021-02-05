import React, { Component } from 'react';

// import http from '../10Services/httpService';
// import apiEndpoint from '../10Services/endpoint';

import NavBar from '../00Navigation/NavBar';
import AdminCreateUser from '../04Admin/AdminCreateUser';
import { UserListContainer } from './UserListContainer';

export class AdminContainer extends Component {


    render() {
        return (
            <div>
                <NavBar />
                <div className="container">

                    <div className="row">
                        <div className="col-5">
                            <AdminCreateUser />
                        </div>

                        <div className="col-7 pt-2">
                            <UserListContainer />
                        </div>


                    </div>

                </div>


            </div>
        )
    }
}

export default AdminContainer
