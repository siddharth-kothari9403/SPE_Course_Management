import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import {deleteStudent} from "../services/user_services"
import { useNavigate } from "react-router-dom";
import { getPersonalStudentFromStorage, getStudentFromStorage, removeStudentFromStorage } from "../services/localStorage_services";

// used in showing the details of the student.
const StudentDetails = ({isStudent, isAdmin, isInstructor}) => {
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    // student that shall store the selected the student.
    const [student, setStudent] = useState(() => {
        const temp = getStudentFromStorage();
        return temp;
    });

    // currStudent that shall store the currently logged in student.
    const [currStudent, setCurrStudent] = useState(() => {
        const temp = getPersonalStudentFromStorage();
        return temp;
    })

    // fn that shall aidin deleting the student.
    const handleToClose = () => {
        deleteStudent(student.id);
        setOpen(false);
        removeStudentFromStorage();
        navigate("/students")
    };
    // fn that shall aid in cancelling the operation.
    const handleCancel = ()=>{
        setOpen(false);
    }

    // fn that shall aid in updating the student.
    const navFunc1 = () => {
        navigate("/updateStudent");
    } 

    // fn that shall aid in getting the request for the student.
    const navFunc2 = () => {
        navigate("/requestsForStudent");
    }

    // fn that shall aid in getting the details of subjects enrolled by the student.
    const navFunc3 = () => {
        navigate("/studentSubjectByStudent")
    }

    // fn that shall aid in getting the details of subjects enrolled by the student and 
    // according to a specific instructor.
    const navFunc4 = () => {
        navigate("/studentSubjectByStudentAndInstructor")
    }

    return (

        <div className="container">
        <div className="card">
            <div className="card-body">
                <h1 className="card-title">
                    Student Name - {student.studentName}
                </h1>
                <div className="card-text">
                    <p>Student Email - {student.email}</p>
                    <p>Student Phone - {student.phone}</p>
                </div>

                {((isStudent && currStudent.id === student.id) || isAdmin) && (
                    <>
                        <button onClick={navFunc1} className="btn btn-warning" type="submit">
                            {/* option to update the details*/}
                            Update Info
                        </button>
                        <button onClick={navFunc2} className="btn btn-success" type="submit">
                            {/*option to see all the requests for the student*/}
                            See all Subject Requests
                        </button>
                    </>
                )}

                <button onClick={navFunc3} className="btn btn-info" type="submit">
                    {/* option to see all subjects the student has enrolled in */}
                    See all enrolled subjects
                </button>
                
                {
                    (isInstructor) && (
                        <button onClick={navFunc4} className="btn btn-info" type="submit">
                            {/* Option for instructor logged in to see which all courses of his the student has taken */}
                            See all subjects of yours that the student has taken
                        </button>
                    )
                }

                {isAdmin && (
                    <button onClick={()=>{setOpen(true)}} className="btn btn-danger" type="submit">
                        {/* option to delete the student. */}
                        Delete Student
                    </button>
                )}
            </div>
        </div>

        {/* Dialog box that will be used in confirming the delete of the student. */}
        <Dialog open={open} onClose={handleToClose}>
            <DialogTitle>{"Delete Student"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete {student.studentName}?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                {/* option to cancel the operation. */}
                <button onClick={handleCancel} color="primary" autoFocus>
                    Cancel
                </button>
                <button onClick={handleToClose}
                // option to delete the student
                    color="primary" autoFocus>
                    Delete
                </button>
            </DialogActions>
        </Dialog>
        </div>
    )
}

export default StudentDetails;