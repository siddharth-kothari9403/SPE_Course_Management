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

export const useSubjectSaveValidator = form => {
    const [errors, setErrors] = useState({
        subjectName: {
            dirty: false,
            error: false,
            message: "",
        },
        subjectDesc: {
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

        const { subjectName, subjectDesc } = form;

        // checking the validity of data entered in various fields of the add a new subject form

        if (nextErrors.subjectName.dirty && (field ? field === "subjectName" : true)) {
            const message = stringValidator(subjectName, form);
            nextErrors.subjectName.error = !!message;
            nextErrors.subjectName.message = message;
            if (!!message) isValid = false;
        }

        if (nextErrors.subjectDesc.dirty && (field ? field === "subjectDesc" : true)) {
            const subjectDescMessage = stringValidator(subjectDesc, form);
            nextErrors.subjectDesc.error = !!subjectDescMessage;
            nextErrors.subjectDesc.message = subjectDescMessage;
            if (!!subjectDescMessage) isValid = false;
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