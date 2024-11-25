import React from "react";
import { setRequestInStorage } from "../services/localStorage_services";
import dateFormat from "dateformat";
import {Link} from 'react-router-dom';

//clickable list item component to show requests 
const RequestListItem = ({request}) => {

    //when clicked, set the request in storage, and show the rdetails for the given request
    const handleClick = () => {
        setRequestInStorage(request);
    }

    //component
    return (
        <Link to={"/requestDetails"}>
            <div className="card1" onClick={handleClick}>
                <div className="card-body">
                    <h3>Subject- {request.subject.subjectName}</h3>
                    <h3>Student- {request.student.studentName}</h3>
                    <br/>
                    <p>
                        {/* Date format - Sunday, 9 July, 2023 */}
                        From - {dateFormat(request.startDate, "fullDate")}
                    </p>
                    <p>
                        To - {dateFormat(request.endDate, "fullDate")}
                    </p>
                </div>
            </div>
        </Link>
    )
}

export default RequestListItem;