import React from 'react';

import '../../App.css';
import Table from '../08CommonComponents/Table';


function EventJournalTable(props) {

    const { entries } = props;

    const columns = [
        {                              
            key: 'entry_id',
            label: 'Įvykio nr.',
            content: entry => <span>{entry.entryID}</span>
        },
        {                              
            key: 'event_time',
            label: 'Laikas',
            content: entry => <span>{entry.eventTime.replace("T", " ").substr(0, 19)}</span>
            // konvertuoja išmeta T žymę ir numeta sekundės dalis
        },
        {                              
            key: 'user_name',
            label: 'Naudotojo prisijungimo vardas',
            content: entry => <span>{entry.userName}</span>
        },
        {                              
            key: 'user_id',
            label: 'Naudotojo ID',
            content: entry => <span>{entry.userID}</span>
        },
        {                              
            key: 'entry_message',
            label: 'Įvykis',
            content: entry => <span>{entry.entryMessage}</span>
        },
        {                              
            key: 'object_type',
            label: 'Objekto tipas',
            content: entry => <span>{entry.objectType}</span>
        },
        {                              
            key: 'object_id',
            label: 'Objekto ID',
            content: entry => <span>{entry.objectID}</span>
        },
        {                              
            key: 'operation_type',
            label: 'Operacijos tipas',
            content: entry => <span>{entry.operationType}</span>
        },
    ]

    return (
            <Table
                columns={columns}
                data={entries}
            />
    )
}

export default EventJournalTable
