import React from "react";
import { setInstructorInStorage } from "../services/localStorage_services";
import {Link} from 'react-router-dom';
//component to show the instructor list item
const InstructorListItem = ({ instructor }) => {

    //when clicked, sets the current instructor in local storage
    const handleClick = () => {
        setInstructorInStorage(instructor);
    }

    //component
    return ( 
        <Link to ={"/instructorDetail"}>
            <div className="card1" onClick={handleClick}>
                <div className="card-body">
                    <h1>Name - {instructor.instructor_name}</h1>
                    <br />
                    <h6>Email- {instructor.email}</h6>
                    <h6>Phone- {instructor.phone}</h6>
                </div>
            </div>
        </Link>
    )
}

export default InstructorListItem;