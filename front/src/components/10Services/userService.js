export class userService {

    initStatus = {
        username: "",
        role: "",
        isAuthenticated: null
    }

    constructor(){
        this.userStatus = initStatus;
    }

    setLoggedUser(username, role){
        this.userStatus = {
            username: username,
            role: role,
            isAuthenticated: true
        }
    }

    getLoggedUser(){
        return this.userStatus;
    }

    logoutUser(){
        this.userStatus = initStatus;
    }
}

export default userService
