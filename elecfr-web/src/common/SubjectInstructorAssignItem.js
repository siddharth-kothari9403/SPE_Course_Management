import React from 'react'
import { setInstructorInStorage } from '../services/localStorage_services'
import {Link} from 'react-router-dom';

//list item to show the instructor records when assigning to a particular subject
const SubjectInstructorAssignItem = ({instructor,subject}) => {

    //clicking sets the instructor in storage, and takes to confirmation page
    const handleClick = () => {
        setInstructorInStorage(instructor)
    }

    //component
    return (
        <Link to={"/assignInstructorConfirmation"}>
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

export default SubjectInstructorAssignItem


