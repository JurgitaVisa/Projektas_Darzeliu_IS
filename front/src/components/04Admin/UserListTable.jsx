import React, { Component } from 'react';

import Table from '../08CommonComponents/Table';
//import Modal from '../08CommonComponents/Modal';

class UserListTable extends Component {

    columns = [
        {                     
            key: 'username',
            path: 'username',
            label: 'Naudotojo vardas',
            content: naudotojas => <span>{naudotojas.username}</span>
        },

        {            
            key: 'role',
            path: 'role',
            label: 'Rolė',
            content: naudotojas => <span>{naudotojas.role}</span>
        },

       
        {            
            key: 'update',
            label: 'Pirminis slaptažodis',
            content: naudotojas => <button onClick={() => this.props.onRestorePassword(naudotojas)} className="btn btn-outline-primary btn-sm btn-block">Atkurti</button>
        },
       
        {            
            key: 'delete',
            label: 'Ištrinti naudotoją',
            content: naudotojas => <button onClick={() => this.props.onDelete(naudotojas)} className="btn btn-outline-danger btn-sm btn-block">Ištrinti</button>
           
        }
        
    ]


    render() {
        const { naudotojai } = this.props;

        return (
            <Table
                columns={this.columns}
                data={naudotojai}

            />
        );
    }
}


export default UserListTable;