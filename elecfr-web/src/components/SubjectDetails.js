import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { deleteSubject, removeInstructorFromSubject } from "../services/user_services";
import { useNavigate } from "react-router-dom";
import { getSubjectFromStorage, removeSubjectFromStorage, setSubjectInStorage, getInstructorFromStorage} from "../services/localStorage_services";
import { setInstructorInStorage } from "../services/localStorage_services";

//  This shall take 3 parameters, isStudent, isInstructor and isAdmin that will be used to identify the user
const SubjectDetails = ({isStudent,isAdmin,isInstructor}) => {

    // open and open1 variable that will be used for the diaglog box
    const [open, setOpen] = useState(false);
    const [open1, setOpen1] = useState(false);

    const navigate = useNavigate();

    // subject variable that will be used to hold the selected subject(retrived from local storage).
    const [subject, setSubject] = useState(() => {
        const temp = getSubjectFromStorage();
        return temp;
    })

    // instructor variable that will be used to hold the selected instructor(retreived from local storage).
    const [instructor, setInstructor] = useState(() => {
        const temp = getInstructorFromStorage();
        return temp;
    })

    // handle to close function that will be used when we delete the subject
    const handleToClose = () => {
        deleteSubject(subject.subjectCode);
        removeSubjectFromStorage();
        setOpen(false);
        navigate("/subjects");
    };

    // this function will be called when we cancel any operation.
    const handleCancel = ()=>{
        setOpen(false);
        setOpen1(false);
    }

    // this function will be called when we want to update subject
    const navFunc = () => {
        navigate("/subjectUpdate")
    }

    // this function will be called when we want to request the subject
    const handleRequest = ()=>{
        setSubjectInStorage(subject);
        navigate("/subjectRequest")
    }

    //  this function will be called when we want to see requests for subject
    const seeRequestsForSubject = () => {
        navigate("/requestsForSubject");
    }

    // getting student subject pairing info by subject
    const seeStudentSubjectsForSubject = () => {
        navigate("/studentSubjectBySubject");
    }

    // used for seeing instructor assigned for subject
    const seeInstructor = () => {
        setInstructorInStorage(subject.instructor)
        navigate("/instructorDetail");
    }

    // used for assigning instructor to the subject
    const assignInstructor = ()=>{
        setSubjectInStorage(subject);
        navigate("/assignInstructor")
    }

    // this will be used for popping of first dialog box
    const openDialog = () => {
        setOpen1(true);
    }

    // This will be used for removing the instructor assigned to the subject
    const handleToClose1 = () => {
        removeInstructorFromSubject(subject.subjectCode);
        setOpen1(false);
        navigate("/subjects");
    }

    return (
        <div className="container">
        <div className="card">
            <div className="card-body">
                <h1 className="card-title">
                    {subject.subjectName}
                </h1>
                <div className="card-text">
                    <p>Subject Code - {subject.subjectCode}</p>
                    <p>{subject.subjectDesc}</p>
                    <br />

                    {subject.instructor ?
                        <>
                            <h4>
                                Instructor Details :
                            </h4>
                            <p></p>
                            <p>Instructor Name : {subject.instructor.instructor_name}</p>
                            <p>Email : {subject.instructor.email}</p>
                            <p>Phone : {subject.instructor.phone}</p>
                        </>
                    : null }
                </div>

                {/* option for requesting a subject */}
                {isStudent &&
                    <button onClick={handleRequest} className="btn btn-info">
                        Request Course
                    </button>
                    }

                    {isAdmin &&
                    <>
                    <button onClick={seeRequestsForSubject} className="btn btn-info">
                        {/* option for seeing all request for the subject */}
                        See all requests for this subject
                    </button>

                    <button onClick={assignInstructor} className="btn btn-success">
                        {/* assign instructor */}
                        Assign Instructor for this course
                    </button>

                    <button onClick={navFunc} className="btn btn-warning">
                        {/* option for updating the subject */}
                        Update Subject
                    </button>

                    <button onClick={seeStudentSubjectsForSubject} className="btn btn-info">
                        {/* option for seeing all students who have taken the subject */}
                        See all records of students taking this course
                    </button>

                    </>
                    } 

                    {(isInstructor && subject.instructor && instructor.id === subject.instructor.id) && 

                    <>
                    <button onClick={seeRequestsForSubject} className="btn btn-info">
                        {/* option for seeing all request for this subject */}
                        See all requests for this subject
                    </button>

                    <button onClick={navFunc} className="btn btn-danger">
                        {/* option for updating the subject */}
                        Update Subject
                    </button>

                    <button onClick={seeStudentSubjectsForSubject} className="btn btn-info">
                        {/* option for seeing all students who have taken the subject */}
                        See all records of students taking this course
                    </button>
                    </>
                    } 

                    {(subject.instructor) ? 
                        <>
                        <button onClick={seeInstructor} className="btn btn-info"> 
                        {/* option to see instructor for this subject */}
                            See instructor details for this course
                        </button>
                        {isAdmin &&
                            <button onClick={openDialog} className="btn btn-danger">
                                {/* option to remove instructor for this subject */} 
                                Remove Instructor for this course
                            </button>
                        }
                        </>
                        :
                        <>
                        {isAdmin && 
                            <button onClick={()=>{setOpen(true)}} className="btn btn-danger">
                            {/* option for deleting a subject */}
                            Delete Subject
                            </button>

                        }
                        </>
                    }
            </div>
        </div>

        {/* Dialog box to delete the subject */}
        <Dialog open={open} onClose={handleToClose}>
            <DialogTitle>{"Delete Subject"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete the subject?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                {/* option to cancel the operation. */}
                <button onClick={handleCancel} color="primary" autoFocus>
                    Cancel
                </button>
                {/* option to delete the subject */}
                <button onClick={handleToClose}
                    color="primary" autoFocus>
                    Delete
                </button>
                
            </DialogActions>
        </Dialog>

        {/*Dialog box for removing instructor for the subject */}
        <Dialog open={open1} onClose={handleToClose1}>
            <DialogTitle>{"Remove Instructor for Subject"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to remove the instructor for this subject?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                {/* option to cancel the operation. */}
                <button onClick={handleCancel} color="primary" autoFocus>
                    Cancel
                </button>
                <button 
                    // option to remove the instructor from the subject
                    onClick={handleToClose1}
                    color="primary" autoFocus>
                        Remove
                </button>
                
            </DialogActions>
        </Dialog>
        </div>
    )
}

export default SubjectDetails;