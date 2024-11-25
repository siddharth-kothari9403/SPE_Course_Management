import { useState } from "react";
import { stringValidator, emailValidator, phoneValidator } from "./validators";

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

export const useInstructorUpdateFormValidator = form => {
    const [errors, setErrors] = useState({
        instructor_name: {
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

        const { instructor_name, email, phone } = form;

        // checking the validity of data entered in various fields of the update instructor info form

        if (nextErrors.instructor_name.dirty && (field ? field === "instructor_name" : true)) {
            const instructor_nameMessage = stringValidator(instructor_name, form);
            nextErrors.instructor_name.error = !!instructor_nameMessage;
            nextErrors.instructor_name.message = instructor_nameMessage;
            if (!!instructor_nameMessage) isValid = false;
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

        // in case of errors, return error message
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