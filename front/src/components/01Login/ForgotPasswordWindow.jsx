import swal from "sweetalert";

export default function ForgotPasswordWindow() {
    swal({
        title: "Slaptažodžio atstatymas",
        text: "Įveskite paskyros el. paštą",
        content: "input",
        button: "Atstatyti",
    }).then((userEmail) => {
        const regexPattern = /\S+@\S+\.\S+/
        if(regexPattern.test(userEmail)) {
            swal({
                title: "Slaptažodžio atstatymas",
                text: "Staptažodžio atstatymo prašymas buvo sėkmingai išsiųstas administratoriui.",
                button: "Baigti",
                icon: "success",
            })
        }
        else if(userEmail){
            swal({
                title: "Įvyko klaida",
                text: "Neteisingas el. paštas",
                button: "Gerai",
                icon: "error"
            })
        }
    })
}