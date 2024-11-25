import React, {useState} from "react";
import { useEffect } from "react";
import { SubjectStudentListItem } from "../common/SubjectStudentListItem";
import { getSubjectFromStorage, getStudentFromStorage, getInstructorFromStorage, getPersonalInstructorFromStorage, getPersonalStudentFromStorage} from "../services/localStorage_services";
import {getSubjectStudents, getSubjectStudentsByStudentId, getSubjectStudentByInstructorId, getSubjectStudentsBySubjectCode, getForInstructorAndStudent} from "../services/user_services"

//component to show the student subject list, based on the choice prop
const StudentSubjectList = ({choice}) => {
    const [studentSubjects, setStudentSubjects] = useState([]);
    var subject = null;
    var student = null;
    var instructor = null;

    const getStudentSubjects = async() => {
        if (choice === 1){
            //if choice = 1, get all records
            const list = await getSubjectStudents();
            setStudentSubjects(list);
        }else if (choice === 2){
            //else if choice = 2, we get the records for the subject set in storage
            subject = getSubjectFromStorage();
            const list = await getSubjectStudentsBySubjectCode(subject.subjectCode);
            setStudentSubjects(list);
        }else if (choice === 3){
            //else if choice = 3, we get the records for the student set in storage
            student = getStudentFromStorage();
            const list = await getSubjectStudentsByStudentId(student.id);
            setStudentSubjects(list);
        }else if (choice === 4){
            //else if choice = 4, we get the records for the instructor set in storage
            instructor = getInstructorFromStorage();
            const list = await getSubjectStudentByInstructorId(instructor.id);
            setStudentSubjects(list);
        }else if (choice === 5){
            //else if choice = 5, we get the records for the instructor and student set in storage
            instructor = getPersonalInstructorFromStorage();
            student = getStudentFromStorage();
            const list = await getForInstructorAndStudent(student);
            setStudentSubjects(list)
        }
        else if (choice===6)
        {
            instructor = getPersonalInstructorFromStorage();
            const list = await getSubjectStudentByInstructorId(instructor.id);
            setStudentSubjects(list);
        }
        else if(choice===7)
        {
            student = getPersonalStudentFromStorage();
            const list = await getSubjectStudentsByStudentId(student.id);
            setStudentSubjects(list);
        }
    }

    useEffect(() => {
        getStudentSubjects();
    }, [choice])

    return (

    <>
    {/* If list is empty, output the banner saying nothing to show, else show the list */}
    { (studentSubjects.length === 0) ? <div className='container banner'>
            <header className='jumbotron banner'> 
                <h5>Nothing to show</h5>
            </header>
        </div>
        : null
    }
    <div className='container'>
        <div className='row'>
        {studentSubjects.map((data) => (
            <div id="space" key= {data.slno} className="col-lg-4 col-sm-12 col-md-6"><SubjectStudentListItem subjectStudent={data}/></div>
        ))}
        </div>
    </div>
    </>
    )
}

export default StudentSubjectList;