import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { getCurrentUser, logout } from './services/auth_services';
import { useEffect, useState } from 'react';
import LoginForm from './components/loginForm';
import Home from './components/home';
import SignupInstructor from "./components/SignupInstructor";
import SignupAdmin from "./components/SignupAdmin";
import SignupStudent from "./components/SignupStudent";
import SubjectDetails from "./components/SubjectDetails";
import SubjectsList from "./components/SubjectsList";
import SubjectSaveForm from "./components/SubjectSaveForm";
import SubjectUpdateForm from "./components/UpdateSubjectForm";
import SubjectRequestForm from "./components/SubjectRequestForm";
import UpdateStudent from "./components/UpdateStudentForm";
import UpdateInstructor from "./components/UpdateInstructorForm";
import StudentSubjectList from "./components/StudentSubjectList";
import SubjectStudentDetails from "./components/StudentSubjectDetails";
import StudentList from "./components/StudentList";
import StudentDetails from "./components/StudentDetails";
import RequestDetails from "./components/RequestDetails";
import RequestList from "./components/RequestList";
import InstructorDetails from "./components/InstructorDetails";
import InstructorList from "./components/InstructorList";
import SubjectByInstructorAssign from "./components/SubjectInstructorAssign";
import SubjectAssignInstructorConfirmation from "./components/SubjectInstructorAssignConfirm";
import PersonalInstructorDetails from "./components/personalInstructorDetails";
import PersonalStudentDetails from "./components/PersonalStudentDetails";
import { removeInstructorFromStorage, removePersonalInstructorFromStorage, removePersonalStudentFromStorage, removeRequestFromStorage, removeStudentFromStorage, removeSubjectFromStorage, removeSubjectStudentFromStorage } from "./services/localStorage_services";

function App() {
  // isAdmin variable used to see if the current user logged in is Admin.
  const [isAdmin, setIsAdmin] = useState(() => {
    const user = getCurrentUser();
    if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "ADMIN"){
      return true;
    }else{
      return false;
    }
  });
  // isStudent variable used to see if the current user logged in is Student.
  const [isStudent, setIsStudent] = useState(() => {
    const user = getCurrentUser();
    if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "STUDENT"){
      return true;
    }else{
      return false;
    }
  });
  // isInstructor variable used to see if the current user logged in is Instructor.
  const [isInstructor, setIsInstructor] = useState(() => {
    const user = getCurrentUser();
    if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "INSTRUCTOR"){
      return true;
    }else{
      return false;
    }
  });
  // currentUser variable that will store the details of current logged in user.
  const [currentUser, setCurrentUser] = useState(() => {
    const temp = getCurrentUser();
    return temp;
  });
  // this fn will be called when we login, this shall intitialise the required 
  // variables.
  const resolveLogin = () => {
    const user = getCurrentUser();
    if (user) {
      setCurrentUser(user);
      setIsAdmin(user.user.roles[0].name === "ADMIN");
      setIsInstructor(user.user.roles[0].name === "INSTRUCTOR");
      setIsStudent(user.user.roles[0].name === "STUDENT");
    }
  };
  // app logout function that shall
  // remove all the data from the local storage and
  // it will also set the variables to its default values.
  const Applogout = () => {
    logout();
    setCurrentUser(null);
    setIsAdmin(false);
    setIsInstructor(false);
    setIsStudent(false);
    removeInstructorFromStorage();
    removePersonalInstructorFromStorage();
    removePersonalStudentFromStorage();
    removeStudentFromStorage();
    removeRequestFromStorage();
    removeSubjectFromStorage();
    removeSubjectStudentFromStorage();
  }


  useEffect(() => {
    resolveLogin();
  }, []);
  
  return (
      <div>
        <nav className="navbar navbar-expand navbar-dark nav">
          <Link to={"/"} className="navbar-brand">
            {/* Link to Home button*/}
            Elective Management
          </Link>
          <div className="navbar-nav mr-auto">
            {currentUser && (
              <>

              <li className="nav-item">
                <Link to={"/subjects"} className="nav-link">
                  {/* Link to list of subjects. */}
                  Subjects
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/instructors"} className="nav-link">
                  {/* Link to set of instructors. */}
                  Instructors
                </Link>
              </li>

            {isAdmin &&
            <>
              <li className="nav-item">
                <Link to={"/addSubject"} className="nav-link">
                  {/* Option to add a subject.*/}
                  Add a Subject
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/requests"} className="nav-link">
                  {/* Option to see all the requests. */}
                  All Requests
                </Link>
              </li>
            </>
            }

              {(isAdmin || isInstructor) && 
              <>
                <li className="nav-item">
                  <Link to={"/students"} className="nav-link">
                    {/* Option to see all the students. */}
                    All students
                  </Link>
                </li>

                <li className="nav-item">
                  <Link to={"/studentSubjects"} className="nav-link">
                    {/* Option to see all the students who have taken a course.*/}
                    All student enrollments
                  </Link>
                </li>
              </>
              }

            </>
            )}
          </div>
            
            
          {currentUser ? (
            <div className="navbar-nav ml-auto">

              {isAdmin && (
                <li>
                  <Link to= {"/registerAdmin"} className="nav-link">
                    {/* Register a new admin.*/}
                    Register New Admin
                  </Link>
                </li>
              )}

              {isAdmin && (
                <li>
                  <Link to={"/registerInstructor"} className="nav-link">
                    {/* Register a new instructor.*/}
                    Register New Instructor
                  </Link>
                </li>
              )}

              {isAdmin && (
                <li>
                  <Link to= {"/registerStudent"} className="nav-link">
                    {/* option to register a new student.*/}
                    Register New Student
                  </Link>
                </li>
              )}

              {isStudent && (
                <>
                  <li className="nav-item">
                    <Link to={"/requestforPersonalStudent"} className="nav-link">
                      {/* option to see self's pending requests. */}
                      Pending Requests
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/studentSubjectByStudent"} className="nav-link">
                      {/* option to see all the subjects the student has enrolled in. */}
                      Subjects Enrolled
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/personalStudentDetail"} className="nav-link">
                      {/* Option to see his/her profile. */}
                      Profile
                    </Link>
                  </li>
                </>
              )}

              {isInstructor && (
                <>
                  <li className="nav-item">
                    <Link to={"/requestforPersonalInstructor"} className="nav-link">
                      {/* Option to see the pending requests for the instructor. */}
                      Pending Requests
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/studentSubjectByPersonalInstructor"} className="nav-link">
                      {/* Option to see his/her students. */}
                      My students
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/subjectByPersonalInstructor"} className="nav-link">
                      {/* Option to see the subjects taught by self. */}
                      My Subjects
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/personalInstructorDetail"} className="nav-link">
                      {/* Option to see his/her profile. */}
                      Profile
                    </Link>
                  </li>
                </>
              )}

              <li className="nav-item">
                <Link to={"/login"} className="nav-link" onClick={Applogout}>
                  {/* option to logout. */}
                  Log out
                </Link>
              </li>
                  
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  {/* option to login. */}
                  Login
                </Link>
              </li>
            </div>
          )}
        </nav>

        <div className="container mt-3">
          <Routes>
            <Route path="/" element={<Home currentUser={currentUser}/>} />
            <Route path="/home" element={<Home currentUser={currentUser}/>} />
            <Route path="/login" element={<LoginForm setCurrentUser = {setCurrentUser} setIsAdmin = {setIsAdmin} setIsStudent = {setIsStudent} setIsInstructor={setIsInstructor}/>} />
            <Route path="/registerStudent" element={<SignupStudent />} />
            <Route path="/registerAdmin" element={<SignupAdmin />} />
            <Route path="/registerInstructor" element={<SignupInstructor/>} />
            <Route path="/subjects" element={<SubjectsList choice={1}/>} />
            <Route path="/subjectsByInstructor" element={<SubjectsList choice={2}/>} />
            <Route path="/moreInfo" element={<SubjectDetails isAdmin={isAdmin} isInstructor={isInstructor} isStudent={isStudent}/>} />
            <Route path="/addSubject" element={<SubjectSaveForm/>} />
            <Route path="/subjectUpdate" element={<SubjectUpdateForm/>}/>
            <Route path="/subjectRequest" element={<SubjectRequestForm/>} />
            <Route path="/instructors" element={<InstructorList/>}/>
            <Route path="/instructorUpdate" element={<UpdateInstructor/>} />
            <Route path="/instructorDetail" element={<InstructorDetails isInstructor={isInstructor} isAdmin={isAdmin}/>}/>
            <Route path="/personalInstructorDetail" element={<PersonalInstructorDetails isInstructor={isInstructor} isAdmin={isAdmin}/>} />
            <Route path="/students" element={<StudentList/>}/>
            <Route path="/updateStudent" element={<UpdateStudent/>} />
            <Route path="/studentDetail" element={<StudentDetails isStudent={isStudent} isAdmin={isAdmin} isInstructor={isInstructor}/>}/>
            <Route path="/personalStudentDetail" element={<PersonalStudentDetails isStudent={isStudent} isAdmin={isAdmin} isInstructor={isInstructor}/>}/>
            <Route path="/requests" element={<RequestList choice={1}/>} />
            <Route path="/requestDetails" element={<RequestDetails isStudent={isStudent} isAdmin={isAdmin} isInstructor={isInstructor}/>} />
            <Route path="/requestsForSubject" element={<RequestList choice={2}/>} />
            <Route path="/requestsForStudent" element={<RequestList choice={3}/>} />
            <Route path="/requestsForInstructor" element={<RequestList choice={4}/>} />
            <Route path="/studentSubjectDetail" element={<SubjectStudentDetails isAdmin={isAdmin} isInstructor={isInstructor} isStudent={isStudent}/>}/>
            <Route path="/studentSubjects" element={<StudentSubjectList choice={1}/>}/>
            <Route path="/studentSubjectBySubject" element={<StudentSubjectList choice={2}/>} />
            <Route path="/studentSubjectByStudent" element={<StudentSubjectList choice={3}/>} />
            <Route path="/studentSubjectByInstructor" element={<StudentSubjectList choice={4}/>} />
            <Route path="/studentSubjectByStudentAndInstructor" element={<StudentSubjectList choice={5}/>} />
            <Route path="/assignInstructor" element={<SubjectByInstructorAssign/>}/>
            <Route path="/assignInstructorConfirmation" element={<SubjectAssignInstructorConfirmation />}/>
            <Route path="/requestforPersonalInstructor" element={<RequestList choice={5}/>}/>
            <Route path="/requestforPersonalStudent" element={<RequestList choice={6}/>}/>            
            <Route path="/studentSubjectByPersonalInstructor" element={<StudentSubjectList choice={6}/>}/>
            <Route path="/studentSubjectByPersonalStudent" element={<StudentSubjectList choice={7}/>}/>
            <Route path="/subjectByPersonalInstructor" element={<SubjectsList choice={3}/>}/>
          </Routes>
        </div>
      </div>
  );
}

export default App;