import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';

import '../../App.css';

const Pagination = (props) => {
    const { itemsCount, pageSize, currentPage, onPageChange } = props;
    const pagesCount = Math.ceil(itemsCount / pageSize);

    if (pagesCount === 1) return null;

    const pages = _.range(1, pagesCount + 1);
    return (
        <div className="d-flex justify-content-center">
            <nav>
                <ul className="pagination">
                    {pages.map(page =>
                        <li key={page} className={page === currentPage ? "page-item active" : "page-item"}>
                            <a href="#0" onClick={() => onPageChange(page)} className="page-link">{page}</a>
                        </li>
                    )}
                </ul>
            </nav>
        </div>

    );
}

Pagination.propTypes = {
    itemsCount: PropTypes.number.isRequired,
    pageSize: PropTypes.number.isRequired,
    currentPage: PropTypes.number.isRequired,
    onPageChange: PropTypes.func.isRequired
};

export default Pagination;