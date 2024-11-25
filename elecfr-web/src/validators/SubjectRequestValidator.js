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

export const RequestSubjectValidator = form => {
    const [errors, setErrors] = useState({
        startDate: {
            dirty: false,
            error: false,
            message: "",
        },
        endDate: {
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

        const { startDate, endDate } = form;

        // checking the validity of data entered in various fields of the request subject form

        if (nextErrors.startDate.dirty && (field ? field === "startDate" : true)) {
            const message = stringValidator(startDate, form);
            nextErrors.startDate.error = !!message;
            nextErrors.startDate.message = message;
            if (!!message) isValid = false;
        }

        if (nextErrors.endDate.dirty && (field ? field === "endDate" : true)) {
            const Message = stringValidator(endDate, form);
            nextErrors.endDate.error = !!Message;
            nextErrors.endDate.message = Message;
            if (!!Message) isValid = false;
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