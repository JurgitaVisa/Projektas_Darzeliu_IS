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
            key: 'status',
            path: 'status',
            label: 'Prašymo statusas',
            content: application => <span> {application.status ? application.status : "-"} </span>
        },
        {
            key: 'kindergartenName',
            path: 'kindergartenName',
            label: 'Darželio pavadinimas',
            content: application => <span> {application.kindergartenName ? application.kindergartenName : "-"} </span>
        },
        {
            key: 'numberInWaitingList',
            path: 'numberInWaitingList',
            label: 'Laukiančiųjų eilės numeris',
            content: application => <span> {application.numberInWaitingList ? application.numberInWaitingList : "-"} </span>
        }
    ]

    additionalColumn = {
        key: 'deactivate',
        label: 'Veiksmai',
        content: application =>
            <span>
                {application.status === 'Neaktualus' || application.status === 'Patvirtintas' ? <span>-</span> : <button onClick={() => this.props.onDeactivate(application)} id="btnDeactivateApplication" className="btn btn-outline-danger btn-sm btn-block">Deaktyvuoti</button>}
            </span>
    }




    render() {
        const { applications, isLocked } = this.props;

        let columns=[];

        if(isLocked){
            columns=this.columns;
        } else {
            columns=[...this.columns, this.additionalColumn]
        }

        return (
            <Table
                columns={columns}
                data={applications}
                isLocked={isLocked}

            />
        );
    }
}

export default QueueProcessedTable


