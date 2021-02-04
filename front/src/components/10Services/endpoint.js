//prieš darant yarn build užkomentuoti localhost ir atkomentuoti šitą endpoint:
const apiEndpoint = process.env.PUBLIC_URL;

//dirbant lokaliai per yarn start paleidus react app per localhost:3000
//pakeisti localhost:8080 į vartus ant kurių paleidžiat aplikaciją:

//const apiEndpoint = "http://localhost:8080";

//const apiEndpoint = "http://localhost:8080/darzelis";

//Daivos endpoint
const apiEndpoint = "http://localhost:8081/darzelis";

//Jurgitos endpoint :)
//const apiEndpoint = "http://localhost:8089/darzelis";

export default apiEndpoint;
