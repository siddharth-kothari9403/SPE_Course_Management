import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { deleteInstructor } from "../services/user_services"
import { useNavigate } from "react-router-dom";
import { getInstructorFromStorage, removeInstructorFromStorage, getPersonalInstructorFromStorage }from "../services/localStorage_services";

// this will be used to show all the details regarding the instructor.
const InstructorDetails = ({isInstructor,isAdmin}) => {
    // open variable that will be used in working of dialog box.
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    // instructor variable that will be used in storing the selected instructor.
    const [instructor, setInstructor] = useState(() => {
        const temp = getInstructorFromStorage();
        return temp;
    })

    // instructor variable that will be used in storing the currenty logged in instructor.
    const [currInstructor, setCurrInstructor] = useState(() => {
        const temp = getPersonalInstructorFromStorage();
        return temp;
    })

    // this fn will be called when we want to delete the instructor.
    const handleToClose = () => {
        deleteInstructor(instructor.id);
        setOpen(false);
        removeInstructorFromStorage();
        navigate("/instructors")
    };

    // this fn will be called when we want to cancel the operation.
    const handleCancel = ()=>{
        setOpen(false);
    }

    // this fn will be called when we want to update the instructor.
    const navFunc1 = () => {
        navigate("/instructorUpdate");
    } 

    // this fn will be called when we want to see the requests for that instructor.
    const navFunc2 = () => {
        navigate("/requestsForInstructor");
    }

    // this fn will be called to see the list of subjects taught by that instructor.
    const navFunc3 = () => {
        navigate("/subjectsByInstructor");
    }

    // this fn will be called when we want to see the list of students enrolled for 
    // courses taught by that professor.
    const navFunc4 = () => {
        navigate("/studentSubjectByInstructor")
    }

    return (
        <div className="container">
        <div className="card">
            <div className="card-body">
                <h1 className="card-title">
                    Instructor Name - {instructor.instructor_name}
                </h1>
                <div className="card-text">
                    <p>Instructor Email - {instructor.email}</p>
                    <p>Instructor Phone - {instructor.phone}</p>
                </div>

                    {((isInstructor && currInstructor.id === instructor.id) || isAdmin) && (
                        <>
                        <button onClick={navFunc1} className="btn btn-warning" type="submit">
                            {/* option to update the details of the instructor. */}
                        Update Info
                        </button>

                        <button onClick={navFunc2} className="btn btn-success" type="submit">
                            {/*option to see all the requests for that instructor.*/}
                        See all subject requests
                        </button>
                        </>
                    )}

                    <button onClick={navFunc3} className="btn btn-success" type="submit">
                        {/* option to see all the subjects he/she is teaching. */}
                        See all subjects taught
                    </button>

                    {(isAdmin || isInstructor) && 
                    <button onClick={navFunc4} className="btn btn-info" type="submit">
                       {/* option to see all the children enrolled in courses taught by this instructor. */}
                        See all children enrolled in courses taught by this instructor
                    </button>
                    }
                    

                    {isAdmin && (
                        <button onClick={()=>{setOpen(true)}} className="btn btn-danger" type="submit">
                            {/* option to delete the instructor. */}
                            Delete Instructor
                        </button>
                    )}
                    </div>
                </div>

        {/* Dialog box that will be used in confirming the delete of the instructor. */}
        <Dialog open={open} onClose={handleToClose}>
            <DialogTitle>{"Delete Instructor"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete {instructor.instructor_name}?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                {/* option to cancel the operation. */}
                <button onClick={handleCancel} color="light" autoFocus>
                    Cancel
                </button>
                <button onClick={handleToClose}
                // option to delete the instructor.
                    color="warning" autoFocus>
                    Delete
                </button>
                
            </DialogActions>
        </Dialog>
        </div>
    )
}

export default InstructorDetails;