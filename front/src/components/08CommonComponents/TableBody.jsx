import React, { Component } from 'react';

class TableBody extends Component {

    render() {

        const { data, columns } = this.props;

        return (

            <tbody >
                {data.map(item =>
                    <tr key={item.name}>
                        {columns.map(column =>
                            <td key={item.name + column.key}>{column.content(item)}</td>
                        )}
                    </tr>
                )}

            </tbody>


        );


    }
}

export default TableBody;