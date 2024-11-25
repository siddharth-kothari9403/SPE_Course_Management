import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import {deleteStudent} from "../services/user_services"
import { useNavigate } from "react-router-dom";
import { removeStudentFromStorage, getPersonalStudentFromStorage, setStudentInStorage } from "../services/localStorage_services";
// will be used in showing details of personal student.
const PersonalStudentDetails = ({isStudent, isAdmin, isInstructor}) => {
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    // student variable that will be used in storing the student details.
    const [student, setStudent] = useState(() => {
        const temp = getPersonalStudentFromStorage();
        setStudentInStorage(temp);
        return temp;
    });

    // this fn will be called when we want to delete the student.
    const handleToClose = () => {
        deleteStudent(student.id);
        setOpen(false);
        removeStudentFromStorage();
        navigate("/students")
    };

    // fn will be called when we want to cancel the operation.
    const handleCancel = ()=>{
        setOpen(false);
    }

    // fn will be called when we want to update the student.
    const navFunc1 = () => {
        navigate("/updateStudent");
    } 

    // fn to see all the request made by that student.
    const navFunc2 = () => {
        navigate("/requestsForStudent");
    }

    // fn to see the student -subject records the student has enrolled in(subjects).
    const navFunc3 = () => {
        navigate("/studentSubjectByStudent")
    }

    // fn to see the student-subjects records by student and instructor.
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

                {(isStudent || isAdmin) && (
                    <>
                        <button onClick={navFunc1} className="btn btn-warning" type="submit">
                            {/* option to update the info */}
                            Update Info
                        </button>
                        <button onClick={navFunc2} className="btn btn-success" type="submit">
                            {/* option to see all the requests. */}
                            See all Subject Requests
                        </button>
                    </>
                )}

                <button onClick={navFunc3} className="btn btn-info" type="submit">
                    {/* option to see all the subjects enrolled in */}
                    See all enrolled subjects
                </button>

                {isAdmin && (
                    <button onClick={()=>{setOpen(true)}} className="btn btn-danger" type="submit">
                        {/* option to delete the student. */}
                        Delete Student
                    </button>
                )}
            </div>
        </div>

        {/* Dialog box that will be used in confirming the deleting of the student. */}
        <Dialog open={open} onClose={handleToClose}>
            <DialogTitle>{"Delete Student"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete {student.studentName}?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <button onClick={handleCancel} color="primary" autoFocus>
                    {/* option to cancel the operation. */}
                    Cancel
                </button>
                <button onClick={handleToClose}
                    color="primary" autoFocus>
                        {/* option to delete the student */}
                    Delete
                </button>
            </DialogActions>
        </Dialog>
        </div>
    )
}

export default PersonalStudentDetails;