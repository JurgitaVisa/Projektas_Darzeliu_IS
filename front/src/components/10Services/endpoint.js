//prieš darant yarn build užkomentuoti localhost ir atkomentuoti šitą endpoint:
// const apiEndpoint = process.env.PUBLIC_URL;

//dirbant lokaliai per yarn start paleidus react app per localhost:3000
//pakeisti localhost:8080 į vartus ant kurių paleidžiat aplikaciją:
const apiEndpoint = "http://akademijait.vtmc.lt:8181/darzelis";

export default apiEndpoint;