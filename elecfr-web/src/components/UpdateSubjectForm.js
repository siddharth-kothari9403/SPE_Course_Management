import React, { useState } from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { useNavigate } from "react-router-dom";
import {useSubjectSaveValidator} from "../validators/SubjectSaveValidator";
import {updateSubject} from '../services/user_services';
import { getSubjectFromStorage, removeSubjectFromStorage } from "../services/localStorage_services";
import image from "../assets/image.png";

//component to update the details of a book
const SubjectUpdateForm = () => {

  //state variable to show the dialog box
  const [open,setOpen] = React.useState(false);

  //subject is taken from local storage, to display on screen
  const [subject, setSubject] = useState(() => {
    const temp = getSubjectFromStorage();
    return temp;
  })
    
  //form component, initialised to the subject variables
  const [form, setForm] = useState({
    subjectName:subject.subjectName,
    subjectDesc:subject.subjectDesc
  });

  const navigate = useNavigate();

  //functions to handle dialog box
  const handleClickToOpen = () => {
    setOpen(true);
  };

  const handleToClose = () => {
    setOpen(false);
    navigate("/subjects")
    removeSubjectFromStorage();
  };

  const [message, setMessage] = useState("");

  const {errors, validateForm} = useSubjectSaveValidator(form)

  //to take care of form state variable changes
  const onUpdateField = e => {
    const nextFormState = {
      ...form,
      [e.target.name]: e.target.value,
    };
    setForm(nextFormState);
  };

  //called when the submit button is pressed
  const onSubmitForm = e => {
    setMessage("")
    e.preventDefault(); 
    //check if valid, if not return   
    const { isValid } = validateForm({ form, errors, forceTouchErrors: true });
    if (!isValid) return;
    //otherwise update the subject in the db
    updateSubject(subject.subjectCode, form.subjectName, form.subjectDesc).then(
      //show dialog box for success
        response => {
            handleClickToOpen()
        },
        // else set error message 
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
                    <p>
                        Subject Code : {subject.subjectCode}
                    </p>
               </div>

              {/* subject name input box */}
                <div className="form-group">
                    <label htmlFor="subjectName">Subject Name</label>
                    <input
                    className="form-control"
                    type="text"
                    aria-label="subjectName"
                    name="subjectName"
                    placeholder="subject name"
                    value={form.subjectName}
                    onChange={onUpdateField}
                    />

                    {errors.subjectName.dirty && errors.subjectName.error ? (
                            <div className="alert alert-danger" role="alert">{errors.subjectName.message}</div>
                            ) : null}
                </div>

                {/* subject description input */}
                <div className="form-group">
                    <label htmlFor="subjectDesc">Subject Desc</label>
                    <textarea
                    className="form-control"
                    type="subjectDesc"
                    aria-label="subjectDesc"
                    name="subjectDesc"
                    placeholder="subject description"
                    value={form.subjectDesc}
                    onChange={onUpdateField}
                    />

                    {errors.subjectDesc.dirty && errors.subjectDesc.error ? (
                            <div className="alert alert-danger" role="alert">{errors.subjectDesc.message}</div>
                            ) : null}
                </div>

                <div className="form-group">
                    <button className="btn btn-primary btn-block">Update details</button>
                </div>

                {/* show error message if unsuccessful */}
                {message ? 
                  <div className="alert alert-danger" role="alert">{message}</div>
                : null}
            </form>
        </div>

        {/* dialog box for success notification */}
        <Dialog open={open} onClose={handleToClose}>
                <DialogTitle>{"Subject Saved successfully"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Subject details have been updated successfully
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
  );
};

export default SubjectUpdateForm;