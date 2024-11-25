import { useState } from "react";
import { stringValidator } from "./validators";

// checks for errors in the form fields
const touchErrors = errors => {
    return Object.entries(errors).reduce((acc, [field, fieldError]) => {
        acc[field] = {
            ...fieldError,
            dirty: true,
        };
        return acc;
    }, {});
};

export const useLoginFormValidator = form => {
    const [errors, setErrors] = useState({
        username: {
            dirty: false,
            error: false,
            message: "",
        },
        password: {
            dirty: false,
            error: false,
            message: "",
        }
    });

    // calling the validators from validators.js
    // and checking whether they can constitute a valid form entry

    const validateForm = ({ form, field, errors, forceTouchErrors = false }) => {
        let isValid = true;

        // Create a deep copy of the errors
        var nextErrors = JSON.parse(JSON.stringify(errors));

        // Force validate all the fields
        if (forceTouchErrors) {
            nextErrors = touchErrors(errors);
        }

        const { username, password } = form;

        // checking the validity of data entered in various fields of the login form

        if (nextErrors.username.dirty && (field ? field === "username" : true)) {
            const message = stringValidator(username, form);
            nextErrors.username.error = !!message;
            nextErrors.username.message = message;
            if (!!message) isValid = false;
        }

        if (nextErrors.password.dirty && (field ? field === "password" : true)) {
            const passwordMessage = stringValidator(password, form);
            nextErrors.password.error = !!passwordMessage;
            nextErrors.password.message = passwordMessage;
            if (!!passwordMessage) isValid = false;
        }

        // in case there are errors, return the error messages
        setErrors(nextErrors);

        return {
            isValid,
            errors: nextErrors,
        };
    };

    return {
        validateForm,
        errors,
    };
};