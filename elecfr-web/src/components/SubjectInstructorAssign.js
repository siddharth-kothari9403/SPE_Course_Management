import React, { useEffect, useState } from 'react'
import { getInstructors } from '../services/user_services'
import { getSubjectFromStorage } from '../services/localStorage_services'
import SubjectInstructorAssignItem from '../common/SubjectInstructorAssignItem'

//component to assign an instructor for the given subject
const SubjectInstructorAssign = () => {

    const [subject,setSubject] = useState(()=>{
      const response = getSubjectFromStorage();
      return response;
    })
    
    //the list of instructors is obtained from backend, and subject from storage
    const [instructors,setInstructors] = useState([]);

    const getInstructorsOnStart=async()=>{
        const response = await getInstructors();
        setInstructors(response);
    }
    useEffect(()=>{
        getInstructorsOnStart();
    },[])

    
    return (
        <>
        {/* User prompt */}
        <div className='container'>
            <header className='jumbotron'>
                <h3>
                    Pick Instructor to assign to this course
                </h3>
            </header>
        </div>

        {/* List of instructors to choose from */}
        <div className='container'>
          <div className='row'>

          {instructors.map((data) => (
            <div id="space" key= {data.id} className='col-lg-4 col-sm-12 col-md-6'><SubjectInstructorAssignItem instructor={data} subject={subject}/></div>
          ))}
          </div>
        </div>
      </>
  )
}

export default SubjectInstructorAssign;