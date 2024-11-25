import React, {useState} from "react";
import { useEffect } from "react";
import RequestListItem from "../common/RequestListItem";
import { getSubjectFromStorage, getStudentFromStorage, getInstructorFromStorage, getPersonalInstructorFromStorage, getPersonalStudentFromStorage} from "../services/localStorage_services";
import { getAllRequests, getRequestByStudentId, getRequestByInstructorId, getRequestBySubjectCode } from "../services/user_services";

//component to show the list of requests 
const RequestList = ({choice}) => {
    const [requests, setRequests] = useState([]);
    var subject = null;
    var student = null;
    var instructor = null;

    //choice is passed as prop
    const getRequests = async() => {
        if (choice === 1){
            //if choice is 1, we get all requests 
            const list = await getAllRequests();
            setRequests(list);
        }else if (choice === 2){
            //else if choice is 2, get the requests for the subject set in storage
            subject = getSubjectFromStorage();
            const list = await getRequestBySubjectCode(subject.subjectCode);
            setRequests(list);
        }else if (choice === 3){
            //else if choice = 3, get the requests for student set in storage
            student = getStudentFromStorage();
            const list = await getRequestByStudentId(student.id);
            setRequests(list);
        }else if (choice === 4){
            //else if choice = 4, get requests for instructor set in storage
            instructor = getInstructorFromStorage();
            const list = await getRequestByInstructorId(instructor.id);
            setRequests(list);
        }else if(choice===5)
        {
            instructor = getPersonalInstructorFromStorage();
            const list = await getRequestByInstructorId(instructor.id);
            setRequests(list);
        }
        else if(choice===6)
        {
            student = getPersonalStudentFromStorage();
            const list = await getRequestByStudentId(student.id);
            setRequests(list);
        }
    }

    useEffect(() => {
        getRequests();
    }, [choice])

    return (
        <>
        {/* if list is empty, output the banner saying nothing to show, else show the list */}
            { (requests.length === 0) ? <div className='container banner'>
                    <header className='jumbotron banner'> 
                        <h5>Nothing to show</h5>
                    </header>
                </div>
                : null
            }
            <div className='container'>
                <div className='row'>
                {requests.map((data) => (
                    <div id="space" key= {data.slno} className="col-lg-4 col-sm-12 col-md-6"><RequestListItem request={data}/></div>
                ))}
                </div>
            </div>
        </>
    )
}

export default RequestList;