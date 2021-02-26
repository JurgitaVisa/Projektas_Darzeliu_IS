import React from 'react';
import TableBody from './TableBody';
import TableHeader from './TableHeader';


const Table = (props) => {
    const { columns, data } = props;

    return (
        <div className="table-responsive-md">

            <table className="table">

                <TableHeader
                    columns={columns}
                />

                <TableBody
                    columns={columns}
                    data={data} 
                />

            </table>
        </div>

    );
}

export default Table;