function InputValidator(event) {
    const target = event.target;
    
        if (target.validity.valueMissing && target.name !== "birthdate" && target.name !== "phone") {
            target.setCustomValidity(target.placeholder + " yra privalomas laukelis")
        }
        else {
            if (target.name === "email") {
                if (target.validity.patternMismatch) {
                    target.setCustomValidity("Neteisingas el. pašto formatas")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            // else if(target.id==="txtBirthdate") {
            //     if(target.validity.valueMissing) {
            //         target.setCustomValidity("Gimimo data yra privalomas laukelis")
            //     }
            //     else if(target.validity.patternMismatch) {
            //         target.setCustomValidity("Neteisingas gimimo datos formatas")
            //     }
            //     else if(target.validity.rangeOverflow) {
            //         target.setCustomValidity("Gimimo data negali būti ateityje")
            //     }
            //     else if(target.validity.rangeUnderflow) {
            //         target.setCustomValidity("Gimimo data negali būti ankstesnė nei 01.01.1900")
            //     }
            //     else {
            //         target.setCustomValidity("")
            //     }
            // }
            else if (target.name === "personalCode" || target.name === "childPersonalCode") {
                if (target.validity.patternMismatch) {
                    target.setCustomValidity("Asmens koda sudaro 11 skaičių, įvesta skaičių: " + target.value.length)
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if (target.name === "name" || target.name === "childName") {
                if (target.validity.patternMismatch) {
                    target.setCustomValidity("Netinkamo formato vardas")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if (target.name === "surname" || target.name === "childSurname") {
                if (target.validity.patternMismatch) {
                    target.setCustomValidity("Netinkamo formato pavardė")
                }
                else {
                    target.setCustomValidity("")
                }
            }
            else if (target.name === "address") {
                target.setCustomValidity("");
            }
            else if (target.name === "phone") {
                if(target.validity.valueMissing) {
                    target.setCustomValidity("Telefono numeris yra privalomas laukelis")
                }
                else {
                    if(target.value.includes('+')) {
                        if (target.validity.patternMismatch) {
                            target.setCustomValidity("Telefono numerį sudaro nuo 4 iki 19 skaičiai, įvesta skaičių: " + (0 + target.value.length - 1))
                        }
                        else {
                            target.setCustomValidity("");
                        }
                    }
                    else {
                        target.setCustomValidity('Formatas: +37000000000')
                    }
                }
            }
            else if (target.id === "txtNewPassword" || target.id === "txtNewPasswordRepeat") {
                if(target.validity.patternMismatch) {
                    target.setCustomValidity("Slaptažodis turi būti ne mažiau 8 simbolių ilgio, turėti bent vieną didžiąją ir mažąją raides ir bent vieną skaičių")
                }
                else {
                    target.setCustomValidity("");
                }
            }
            else if(target.id === "txtOldPassword") {
                target.setCustomValidity("");
            }
        }
}

export default InputValidator;