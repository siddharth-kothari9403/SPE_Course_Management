import { useState } from "react";
import { confirmPasswordValidator, stringValidator, emailValidator, phoneValidator } from "./validators";

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

export const useStudentSignupFormValidator = form => {
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
        },
        confirmPassword: {
            dirty: false,
            error: false,
            message: ""
        },
        studentName: {
            dirty: false,
            error: false,
            message: ""
        },
        email: {
            dirty: false,
            error: false,
            message: ""
        },
        phone: {
            dirty: false,
            error: false,
            message: ""
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

        const { username, password, confirmPassword, studentName, email, phone } = form;

        // checking the validity of data entered in various fields of the signup student form

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

        if (nextErrors.confirmPassword.dirty && (field ? field === "confirmPassword" : true)) {
            const confirmPasswordMessage = confirmPasswordValidator(confirmPassword, form);
            nextErrors.confirmPassword.error = !!confirmPasswordMessage;
            nextErrors.confirmPassword.message = confirmPasswordMessage;
            if (!!confirmPasswordMessage) isValid = false;
        }

        if (nextErrors.studentName.dirty && (field ? field === "studentName" : true)) {
            const studentNameMessage = stringValidator(studentName, form);
            nextErrors.studentName.error = !!studentNameMessage;
            nextErrors.studentName.message = studentNameMessage;
            if (!!studentNameMessage) isValid = false;
        }

        if (nextErrors.email.dirty && (field ? field === "email" : true)) {
            const emailMessage = emailValidator(email, form);
            nextErrors.email.error = !!emailMessage;
            nextErrors.email.message = emailMessage;
            if (!!emailMessage) isValid = false;
        }

        if (nextErrors.phone.dirty && (field ? field === "phone" : true)) {
            const phoneMessage = phoneValidator(phone, form);
            nextErrors.phone.error = !!phoneMessage;
            nextErrors.phone.message = phoneMessage;
            if (!!phoneMessage) isValid = false;
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