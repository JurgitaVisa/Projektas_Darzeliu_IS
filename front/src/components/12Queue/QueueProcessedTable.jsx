import React, { Component } from 'react';
import Table from '../08CommonComponents/Table';

class QueueProcessedTable extends Component {
    columns = [
        {
            key: 'childPersonalCode',
            path: 'childPersonalCode',
            label: 'Vaiko asmens kodas',
            content: application => <span> {application.childPersonalCode}</span>
        },
        {
            key: 'name',
            path: 'name',
            label: 'Vaiko vardas, pavardė',
            content: application => <span> {application.childName} {application.childSurname}</span>
        },

        {
            key: 'kindergartenName',
            path: 'kindergartenName',
            label: 'Darželio pavadinimas',
            content: application => <span> {application.kindergartenName ? application.kindergartenName : "-"} </span>
        },
        {
            key: 'status',
            path: 'status',
            label: 'Prašymo statusas',
            content: application => <span> {application.status ? application.status : "-"} </span>
        },
        {
            key: 'numberInWaitingList',
            path: 'numberInWaitingList',
            label: 'Laukiančiųjų eilės numeris',
            content: application => <span> {application.choise2 ? application.choise2 : "-"} </span>
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

export default QueueProcessedTable


