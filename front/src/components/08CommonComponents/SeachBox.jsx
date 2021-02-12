import React from 'react';

const SearchBox = ({ value, onSearch }) => {
    return (
        <input
            type="text"
            className="form-control my-3"
            placeholder="Ieškoti pagal vardą..."
            value={value}
            onChange={onSearch}
        />
    );
}

export default SearchBox;
