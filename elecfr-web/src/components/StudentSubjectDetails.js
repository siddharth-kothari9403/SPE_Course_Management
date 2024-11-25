import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { deleteSubjectStudent } from "../services/user_services";
import { useNavigate } from "react-router-dom";
import { getSubjectStudentFromStorage } from "../services/localStorage_services";
import dateFormat from "dateformat";

//Component to show the student subject details
const SubjectStudentDetails = ({isAdmin}) => {

    //get from local storage
    const [subjectStudent, setSubjectStudent] = useState(() => {
        const temp = getSubjectStudentFromStorage();
        return temp;
    })

    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    //functions to handle the dialog box
    const handleToClose = () => {
        deleteSubjectStudent(subjectStudent.slno);
        setOpen(false);
        navigate("/studentSubjects");
    };

    const handleCancel = ()=>{
        setOpen(false);
    }

    return (
        // Show the details of the subject student record
        <div className="container">
        <div className="card">
            <div className="card-body">
                <h1 className="card-title">
                {subjectStudent.slno}. {subjectStudent.subject.subjectName} - {subjectStudent.student.studentName}
                </h1>
                <div className="card-text">
                    
                    <h4>
                        Subject Details :
                    </h4>
                    <p></p>
                    <p>Description : {subjectStudent.subject.subjectDesc}</p>

                    {subjectStudent.subject.instructor ?
                        <>
                            <h4>
                                Instructor Details :
                            </h4>
                            <p></p>
                            <p>Instructor Name : {subjectStudent.subject.instructor.instructor_name}</p>
                            <p>Email : {subjectStudent.subject.instructor.email}</p>
                            <p>Phone : {subjectStudent.subject.instructor.phone}</p>
                        </>
                    : null }

                    <h4>
                        Student Details :
                    </h4>
                    <p></p>
                    <p>Email : {subjectStudent.student.email}</p>
                    
                    <p>Phone : {subjectStudent.student.phone}</p>

                    <p>
                        Start Date : {dateFormat(subjectStudent.startDate,"fullDate")}
                    </p>
                    
                    <p>
                        End Date :{dateFormat(subjectStudent.endDate,"fullDate")}
                    </p>
            </div>
            
            {/* Button to Delete the record if admin */}
                {isAdmin && 
                    <button onClick={()=>{setOpen(true)}} className="btn btn-danger">
                        Delete Record
                    </button>
                }
            </div>
        </div> 

        {/* Dialog box to handle the deleting of the record */}
            <Dialog open={open} onClose={handleToClose}>
                <DialogTitle>{"Delete Record"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure you want to delete this record?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    {/* Cancel */}
                    <button onClick={handleCancel} color="light" autoFocus>
                        Cancel
                    </button>
                    {/* Delete */}
                    <button onClick={handleToClose}
                        color="warning" autoFocus>
                        Delete Record
                    </button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

export default SubjectStudentDetails;