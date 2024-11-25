import React, { useState } from "react";
import { registerAdmin } from "../services/auth_services";
import { useAdminSignupFormValidator } from "../validators/signupAdminValidator";
import { useNavigate } from "react-router-dom";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import image1 from "../assets/image1.png";

// Admin signup form
const SignupAdmin = () => {

    const [open, setOpen] = React.useState(false);

    const [form, setForm] = useState({
        username: "",
        password: "",
        confirmPassword: ""
    });

    const navigate = useNavigate();
    const [message, setMessage] = useState("");

    const {errors, validateForm} = useAdminSignupFormValidator(form);

    // opens dialog box
    const handleClickToOpen = () => {
        setOpen(true);
    };


    // close dialog box
    const handleToClose = () => {
        setOpen(false);
        navigate("/home")
    };

    // when any form field is updated, check validity of the field
    const onUpdateField = e => {
        const nextFormState = {
          ...form,
          [e.target.name]: e.target.value,
        };
        setForm(nextFormState);
    };

    // when Register button is clicked, perform all validation checks ( including backend checks such as unique username )
    // and if valid, display dialog box signifying completion, else show error message 
    const onSubmitForm = e => {
        setMessage("")
        e.preventDefault();
        // checks validity of form fields
        const { isValid } = validateForm({ form, errors, forceTouchErrors: true });
        // if invalid
        if (!isValid) return;
        // if valid
        registerAdmin(form.username, form.password).then(
            //if successful, open dialog box
            response => {
                handleClickToOpen()
            },
            error => {
                const resMessage = (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                  error.message ||
                  error.toString();
                setMessage(resMessage)
            }
        )
    };

    // rendering form components in screen
    return (

        <div className="col-md-12">
            {/* image of person */}
            <div className="card card-container">
            <img
                src={image1}
                alt="profile-img"
                className="profile-img-card"
            />

            {/* actual form */}
            <form onSubmit={onSubmitForm}>
                {/* username input field */}
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        className="form-control"
                        name="username"
                        aria-label="Username"
                        value={form.username}
                        onChange={onUpdateField}
                    />

                    {errors.username.dirty && errors.username.error ? (
                            <div className="alert alert-danger" role="alert">{errors.username.message}</div>
                            ) : null}
                    </div>

                    {/* password input field */}
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            name="password"
                            aria-label="Password field"
                            value={form.password}
                            onChange={onUpdateField}
                        />

                        {errors.password.dirty && errors.password.error ? (
                            <div className="alert alert-danger" role="alert">{errors.password.message}</div>
                            ) : null}
                    </div>

                    {/* confirm password field */}
                    <div className="form-group">
                        <label htmlFor="password">Confirm Password</label>
                        <input
                            type="password"
                            className="form-control"
                            name="confirmPassword"
                            aria-label="Confirm Password field"
                            value={form.confirmPassword}
                            onChange={onUpdateField}
                        />

                        {errors.confirmPassword.dirty && errors.confirmPassword.error ? (
                            <div className="alert alert-danger" role="alert">{errors.confirmPassword.message}</div>
                            ) : null}
                    </div>

                    {/* register button */}
                    <div className="form-group">
                        <button className="btn-block form-button1" type="submit">
                            Register Admin
                        </button>
                    </div>

                    {/* display error message if any */}
                    {message ? 
                        <div className="alert alert-danger" role="alert">{message}</div>
                        : null}
                </form>
            </div>

            {/* Display dialog box when admin has been successfully registered */}
            <Dialog open={open} onClose={handleToClose}>
                <DialogTitle>{"Signup successful"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        New Admin User set up successfully, kindly close the dialog box
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleToClose}
                        color="primary" autoFocus>
                        Close
                    </button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

export default SignupAdmin;