import React, { Component } from 'react';

import Table from '../08CommonComponents/Table';

class UserApplicationsTable extends Component {

    columns = [
        {
            key: 'date',
            path: 'date',
            label: 'Pateikimo data',
            content: application => <span>{application.submitedAt}</span>
        },

        {
            key: 'childSurname',
            path: 'childSurname',
            label: 'Vaiko vardas, pavardė',
            content: application => <span>{application.childName} {application.childSurname}</span>
        },
        {
            key: 'status',
            path: 'status',
            label: 'Prašymo statusas',
            content: application => <span>{application.status} </span>
        },
        {
            key: 'kindergarten',
            path: 'kindergarten',
            label: 'Priimta į darželį',
            content: application =>
                <span>
                    {application.status === 'Patvirtintas' ? <span>{application.kindergartenName}</span> : <span>-</span>}
                </span>
        },
        {
            key: 'waiting',
            path: 'waiting',
            label: 'Laukiančiųjų eilės numeris',
            content: application =>
                <span>
                    {application.status === 'Laukiantis' ? <span>{application.numberInWaitingList}</span> : <span>-</span>}
                </span>
        },
        {
            key: 'delete',
            label: 'Ištrinti prašymą',
            content: application => <button onClick={() => this.props.onDelete(application)} id="btnDeleteApplication" className="btn btn-outline-danger btn-sm btn-block">Ištrinti</button>

        }

    ]


    render() {
        const { applications } = this.props;

        return (
            <Table
                columns={this.columns}
                data={applications}

            />
        );
    }
}


export default UserApplicationsTable;