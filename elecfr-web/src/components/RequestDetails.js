import React, {useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import { deleteRequest , accept } from "../services/user_services";
import { useNavigate } from "react-router-dom";
import { getPersonalInstructorFromStorage, getRequestFromStorage } from "../services/localStorage_services";
import dateFormat from "dateformat";

// used in showing details regarding requests.
const RequestDetails = ({isStudent,isAdmin,isInstructor}) => {

    const [open, setOpen] = useState(false);
    const [acceptOpen, setAcceptOpen] = useState(false);
    const navigate = useNavigate();
    
    // Request variable that shall store the selected request.
    const [request, setRequest] = useState(() => {
        const temp = getRequestFromStorage();
        return temp;
    })

    // instructor variable that shall store the selected instructor.
    const [instructor, setInstructor] = useState(() => {
        const temp = getPersonalInstructorFromStorage();
        return temp;
    })

    // fn that shall delete the request.
    const handleToClose = () => {
        deleteRequest(request.slno);
        setOpen(false);
        if(isAdmin){navigate("/allRequests")}
        else if(isStudent){navigate("/requestsForStudent")}
        else if(isInstructor){navigate("/requestsforInstructor")}
    };

    // fn that shall be used in cancelling the operation.
    const handleCancel = ()=>{
        setOpen(false);
    }

    // fn that shall be used in cancelling the accept request operation.
    const handleCancelAccept = () => {
        setAcceptOpen(false);
    }

    // fn that shall accept the request.
    const handleAccept = async() => {
        accept(request).then(
            response => {
                setAcceptOpen(false);
                navigate("/home")
            }
        )
    }

    return (

        <div className="container">
        <div className="card">
            <div className="card-body">
                <h1 className="card-title">
                {request.slno}. {request.subject.subjectName} - {request.student.studentName}
                </h1>
                <div className="card-text">
                    
                    <h4>
                        Subject Details :
                    </h4>
                    <p></p>
                    <p>Description : {request.subject.subjectDesc}</p>

                    {request.subject.instructor ?
                        <>
                            <h4>
                                Instructor Details :
                            </h4>
                            <p></p>
                            <p>Instructor Name : {request.subject.instructor.instructor_name}</p>
                            <p>Email : {request.subject.instructor.email}</p>
                            <p>Phone : {request.subject.instructor.phone}</p>
                        </>
                    : null }
                    
                    <h4>
                        Student Details :
                    </h4>
                    <p></p>
                    <p>Email : {request.student.email}</p>
                    <p>Phone : {request.student.phone}</p>

                    <p>
                        Start Date : {dateFormat(request.startDate,"fullDate")}
                    </p>
                    
                    <p>
                        End Date : {dateFormat(request.endDate,"fullDate")}
                    </p>
            </div>

            {(isAdmin || (isInstructor && instructor && instructor.id === request.subject.instructor.id)) &&
                <button onClick={()=>{setAcceptOpen(true)}} className="btn btn-primary" type="submit">
                    {/* option that shall accept the request. */}
                    Accept
                </button>
            }

            {(isAdmin || (isInstructor && instructor && instructor.id === request.subject.instructor.id) || isStudent) &&
                <button onClick={()=>{setOpen(true)}} className="btn btn-primary" type="submit" >
                    {/* option that shall delete the request. */}
                    Delete
                </button>
            }
            </div>
        </div>

            <Dialog open={acceptOpen} onClose={handleAccept}>
                <DialogTitle>{"Accept Request"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleCancelAccept} color="light" autoFocus>
                        {/* option to cancel the operation. */}
                        Cancel
                    </button>
                    <button onClick={handleAccept}
                        color="success" autoFocus>
                            {/* option to accept eh request. */}
                        Accept
                    </button>
                    
                </DialogActions>
            </Dialog>
            <Dialog open={open} onClose={handleToClose}>
                <DialogTitle>{"Delete Request"}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Are you sure you want to delete the request?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleCancel} color="primary" autoFocus>
                        {/* option to cancel the delete operation. */}
                        Cancel
                    </button>
                    <button onClick={handleToClose}
                        color="warning" autoFocus>
                            {/* option to delete the request. */}
                        Delete Request
                    </button>   
                </DialogActions>
            </Dialog>
        </div>
    )
}

export default RequestDetails;