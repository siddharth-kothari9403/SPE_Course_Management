import React, { useState} from "react";
import { useNavigate } from "react-router-dom";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { RequestSubjectValidator } from "../validators/SubjectRequestValidator";
import { getSubjectFromStorage, getPersonalStudentFromStorage, } from "../services/localStorage_services";
import { registerRequest } from "../services/user_services";
import image from "../assets/image1.png";

//Request subject form
const SubjectRequestForm = () => {

    const [open, setOpen] = React.useState(false);

    //form state variable 
    const [form, setForm] = useState({
        startDate:"",
        endDate:""
    });

    //subject and student data is taken from local storage
    const [subject, setSubject] = useState(() => {
        const temp = getSubjectFromStorage();
        return temp;
    })

    const [student, setStudent] = useState(() => {
        const temp = getPersonalStudentFromStorage();
        return temp;
    })

    const navigate = useNavigate();
    const [message, setMessage] = useState("");

    const {errors, validateForm} = RequestSubjectValidator(form);

    //functions to handle opening and closing of dialog boxes
    const handleClickToOpen = () => {
        setOpen(true);
    };
 
    const handleToClose = () => {
        setOpen(false);
        navigate("/subjects")
    };

    //function to handle changes to the form state variable
    const onUpdateField = e => {
        const nextFormState = {
          ...form,
          [e.target.name]: e.target.value,
        };
        setForm(nextFormState);
    };

    //function to be called when the submit button is clicked
    const onSubmitForm = e => {
        setMessage("")
        e.preventDefault();    
        const { isValid } = validateForm({ form, errors, forceTouchErrors: true });
        if (!isValid) return;
        //if valid parameters, register the request
        registerRequest(student,subject,form.startDate,form.endDate).then(
            response => {
                //show dialog box on success
                handleClickToOpen()
            },
            error => {
                //else set reqd error message
                const resMessage = (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                  error.message ||
                  error.toString();
                setMessage(resMessage)
            }
        )
    };

    return (

        <div className="col-md-12">
            <div className="card card-container">
            <img
                src={image}
                alt="profile-img"
                className="profile-img-card"
            />

            <form onSubmit={onSubmitForm}>
                
                <div className="form-group">
                    {/* Start date input */}
                    <label htmlFor="startDate">startDate</label>
                    <input
                        type="date"
                        className="form-control"
                        name="startDate"
                        aria-label="startDate"
                        value={form.startDate}
                        onChange={onUpdateField}
                    />

                    {errors.startDate.dirty && errors.startDate.error ? (
                            <div className="alert alert-danger" role="alert">{errors.username.message}</div>
                            ) : null}
                    </div>

                    {/* End date input */}
                    <div className="form-group">
                        <label htmlFor="endDate">End Date</label>
                        <input
                            type="date"
                            className="form-control"
                            name="endDate"
                            aria-label="endDate"
                            value={form.endDate}
                            onChange={onUpdateField}
                        />

                        {errors.endDate.dirty && errors.endDate.error ? (
                            <div className="alert alert-danger" role="alert">{errors.password.message}</div>
                            ) : null}
                    </div>

                    <div className="form-group">
                        <button className="btn btn-primary btn-block form-button">Request Subject</button>
                    </div>

                    {/* Conditionally show the error message */}
                    {message ? 
                        <div className="alert alert-danger" role="alert">{message}</div>
                        : null}
                </form>
            </div>

            {/* If successful, dialog box is shown confirming the same */}
            <Dialog open={open} onClose={handleToClose}>
                <DialogTitle>{"Request Subject"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        We have received your request, kindly wait till the Instructor approves it.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleToClose}
                        color="primary" autoFocus>
                        Go to Subjects list
                    </button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

export default SubjectRequestForm;