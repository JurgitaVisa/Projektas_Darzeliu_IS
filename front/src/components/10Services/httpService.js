
import axios from 'axios';
// import { toast } from 'react-toastify';

//default response when an unexpected error occurs
//hadle expected client errors separately
axios.interceptors.response.use(null, error => {
    const expectedError = error.response && error.response.status >= 400 && error.response.status < 500;

    if (!expectedError) {
        console.log("Logging unexpected error", error);
        alert('Įvyko klaida, puslapis nurodytu adresu nepasiekiamas');
    }

    return Promise.reject(error);
});

//http service object with crud methods (current axios)
export default {
    get: axios.get,
    post: axios.post,
    put: axios.put,
    delete: axios.delete
};
