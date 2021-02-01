import React from 'react';

const UserContext = React.createContext({
    userName: "",
    userRole: "",
    updateUserName: () => {},
    updateUserRole: () => {}
});

export default UserContext;