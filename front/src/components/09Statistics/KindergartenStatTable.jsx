import React, { Component } from 'react';

import Table from '../08CommonComponents/Table';

class KindergartenStatTable extends Component {

    columns = [
        {                     
            key: 'name',
            path: 'name',
            label: 'Darželio pavadinimas',
            content: darzelis => <span>{darzelis.name}</span>
        },
       
        {           
            key: 'numberOfApplications',
            path: 'numberOfApplications',
            label: 'Pateikta prašymų',
            content: darzelis => <span>{darzelis.numberOfApplications} </span>
        },
        {           
            key: 'availablePlaces',
            path: 'availablePlaces',
            label: 'Laisvų vietų skaičius',
            content: darzelis => <span>{darzelis.availablePlaces} </span>
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


export default KindergartenStatTable;