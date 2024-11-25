import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { deleteInstructor } from "../services/user_services"
import { useNavigate } from "react-router-dom";
import { setInstructorInStorage, removeInstructorFromStorage, getPersonalInstructorFromStorage }from "../services/localStorage_services";

//used in displaying to see the personal instructor details
const PersonalInstructorDetails = ({isInstructor, isStudent, isAdmin}) => {

    // open variable that will be used in working of dialog box. 
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    // instructor variable that will be used in storing the instructor.
    const [instructor, setInstructor] = useState(() => {
        const temp = getPersonalInstructorFromStorage();
        setInstructorInStorage(temp);
        return temp;
    })

    // fn that will be used to when we want to delete the instructor.
    const handleToClose = () => {
        deleteInstructor(instructor.id);
        setOpen(false);
        removeInstructorFromStorage();
        navigate("/instructors")
    };

    // fn that will be used when we want to cancel the operation.
    const handleCancel = ()=>{
        setOpen(false);
    }

    // fn that will be used when we want to update the instructor.
    const navFunc1 = () => {
        navigate("/instructorUpdate");
    } 

    // fn that will be used when we want to see the requests for the instructor.
    const navFunc2 = () => {
        navigate("/requestsForInstructor");
    }

    // fn that will be used when we want to see the subjects taught by this instructor.
    const navFunc3 = () => {
        navigate("/subjectsByInstructor");
    }

    // fn that will be used when we want to see the students enrolled in courses taught by that instructor.
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

                    {(isInstructor || isAdmin) && (
                        <>
                        <button onClick={navFunc1} className="btn btn-warning" type="submit">
                            {/* option to update the  instructor */}
                        Update Info
                        </button>

                        <button onClick={navFunc2} className="btn btn-success" type="submit">
                            {/* option to see all the the requests for him */}
                        See all subject requests
                        </button>
                        </>
                    )}

                    <button onClick={navFunc3} className="btn btn-success" type="submit">
                        {/* option to see all the subjects he has taught. */}
                        See all subjects taught
                    </button>

                    {(isAdmin || isInstructor) && 
                    <button onClick={navFunc4} className="btn btn-info" type="submit">
                        {/* option to see all the children who have enrolled in courses taught by him */}
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


        {/*Dialog box that will be used when we want to delete the instructor.  */}
        <Dialog open={open} onClose={handleToClose}>
            <DialogTitle>{"Delete Instructor"}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete {instructor.instructor_name}?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <button onClick={handleCancel} color="light" autoFocus>
                    {/* option to cancel the operation. */}
                    Cancel
                </button>
                <button onClick={handleToClose}
                    color="warning" autoFocus>
                    Delete
                    {/* option to delete the instructor. */}
                </button>
                
            </DialogActions>
        </Dialog>
        </div>
    )
}

export default PersonalInstructorDetails;