import React from "react";
import { setStudentInStorage } from "../services/localStorage_services";
import {Link} from 'react-router-dom';

//clickable list item to show student details
const StudentListItem = ({student}) => {

    //when clicked, set the student in storage, and show details of the student
    const handleClick = () => {
        setStudentInStorage(student);
    }

    //component
    return (
        <Link to={"/studentDetail"}>
            <div className="card1" onClick={handleClick}>
                <div className="card-body">
                    <h1>Name - {student.studentName}</h1>
                    <br />
                    <h6>Email- {student.email}</h6>
                    <h6>Phone- {student.phone}</h6>
                </div>
            </div>
        </Link>
    )
}

export default StudentListItem;