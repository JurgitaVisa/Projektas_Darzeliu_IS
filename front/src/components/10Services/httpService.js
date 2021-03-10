
import axios from 'axios';

import '../../App.css';

//default response when an unexpected error occurs
//hadle expected client errors separately



//http service object with crud methods (current axios)
export default {
    get: axios.get,
    post: axios.post,
    put: axios.put,
    delete: axios.delete,
    request: axios.request
};
