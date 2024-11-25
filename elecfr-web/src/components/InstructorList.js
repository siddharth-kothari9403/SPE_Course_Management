import React, {useState, useEffect} from 'react'
import { getInstructors } from '../services/user_services';
import InstructorListItem from "../common/InstructorListItem";

//component to show the list if instrcutors 
function InstructorList(){

  const [instructors, setInstructors] = useState([]);

  //get instructors from backend using use state
  const getInstructorComp = async () => {
    const response = await getInstructors();
    setInstructors(response);
  }

  useEffect(() => {
    getInstructorComp();
  },[])
  
  return (
    <>
    {/* If list is empty, output banner saying nothing to show, else show the list */}
    { (instructors.length === 0) ? <div className='container banner'>
            <header className='jumbotron banner'> 
              <h5>Nothing to show</h5>
            </header>
        </div>
            : null
    }
    <div className='container'>
      <div className='row'>
        {instructors.map((data) => (
          <div key= {data.id} className='col-lg-4 col-sm-12 col-md-6'><InstructorListItem instructor={data}/></div>
        ))}
      </div>
    </div>
    </>
  );
}

export default InstructorList;