import React, { Component } from 'react';

import Table from '../08CommonComponents/Table';
//import Modal from '../08CommonComponents/Modal';

class KindergartenListTable extends Component {

    columns = [
        {                     
            key: 'id',
            path: 'id',
            label: 'Įstaigos kodas',
            content: darzelis => <span>{darzelis.id}</span>
        },

        {            
            key: 'name',
            path: 'name',
            label: 'Pavadinimas',
            content: darzelis => <span>{darzelis.name }</span>
        },

        {            
            key: 'address',
            path: 'address',
            label: 'Adresas',
            content: darzelis => <span>{darzelis.address }</span>
        },

        {            
            key: 'elderate',
            path: 'elderate',
            label: 'Seniūnija',
            content: darzelis => <span>{darzelis.elderate }</span>
        },

        {            
            key: 'capacityAgeGroup2to3',
            path: 'capacityAgeGroup2to3',
            label: 'Vietų grupėje nuo 2 iki 3 metų',
            content: darzelis => <div className="text-center">{darzelis.capacityAgeGroup2to3 }</div>
        },

        {            
            key: 'capacityAgeGroup3to6',
            path: 'capacityAgeGroup3to6',
            label: 'Vietų grupėje nuo 3 iki 6 metų',
            content: darzelis => <div className="text-center">{darzelis.capacityAgeGroup3to6 }</div>
        },

        {            
            key: 'update',
            label: 'Redaguoti duomenis',
            content: darzelis => <button onClick={() => this.props.onEditData(darzelis)} id="btnEditKindergarten" className="btn btn-outline-primary btn-sm btn-block">Redaguoti</button>
        },
       
        {            
            key: 'delete',
            label: 'Ištrinti darželį',
            content: darzelis => <button onClick={() => this.props.onDelete(darzelis)} id="btnDeleteKindergarten" className="btn btn-outline-danger btn-sm btn-block">Ištrinti</button>
           
        }
        
    ]


    render() {
        const { darzeliai } = this.props;

        return (
            <Table
                columns={this.columns}
                data={darzeliai}

            />
        );
    }
}


export default KindergartenListTable;