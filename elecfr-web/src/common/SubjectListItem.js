import React from "react";
import { setSubjectInStorage } from "../services/localStorage_services"
import "bootstrap/dist/css/bootstrap.min.css";
import {Link} from 'react-router-dom';
//list item to show the subject details
const SubjectListItem = ({subject}) => {

    //when clicked, set the subject in storage
    const handleClick = () => {
        setSubjectInStorage(subject);
    }

    //component
    return (
        <Link to={"/moreInfo"}>
            <div className="card1" onClick={handleClick}>
                <div className="card-body">
                        <h1>Name -{subject.subjectName}</h1>
                        <br />
                        {subject.instructor ? <h4>Instructor: Name -{subject.instructor.instructor_name}</h4>
                        : null}
                        <p>{subject.subjectDesc}</p>
                </div>
            </div>
        </Link>
    )
}
export default SubjectListItem;