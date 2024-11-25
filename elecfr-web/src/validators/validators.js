// file contains all the different types of validators being used

// checks whether the given email is not null and of a valid format or not
export const emailValidator = email => {
    if (!email) {
        return "Email is required";
    } else if (!new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])").test(email)) {
        return "Incorrect email format";
    }
    return "";
};

// checks for any string to be non-empty  
export const stringValidator = string => {
    if (!string) {
        return "Required field";
    }
    return "";
};

// checks whether the given non-empty string is a valid phone number or not
export const phoneValidator = phone => {
    if (!phone) {
        return "Phone is required"
    } else if (!new RegExp("^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$").test(phone)) {
        return "Invalid phone number format"
    }
    return "";
}

// checks whether the password and confirm password fields are not empty and have the same value
export const confirmPasswordValidator = (confirmPassword, form) => {
    if (!confirmPassword) {
        return "Confirm password is required";
    } else if (confirmPassword !== form.password) {
        return "Passwords do not match";
    }
    return "";
};