import axios from "axios";
import authHeader from "./auth_header";

const API_URL = "http://localhost:8080/";

//function to save a new subject
export const saveSubject = async(subjectName, subjectDesc) => {
    var token = authHeader();
    await axios.post(API_URL + "subject/save", {
        subjectName,
        subjectDesc
    }, { headers: { Authorization: "Bearer " + token } });

    return "Subject Successfully Saved";
}

//function to delete a subject
export const deleteSubject = async(subjectCode) => {
    var token = authHeader();
    await axios.delete(API_URL + `subject/delete/${subjectCode}`, { headers: { Authorization: "Bearer " + token } })
    return "Subject Successfully Deleted!";
}

//function to update a subject
export const updateSubject = async(subjectCode, subjectName, subjectDesc) => {
    var token = authHeader();

    await axios.put(API_URL + "subject/update", {
        subjectCode,
        subjectName,
        subjectDesc
    }, { headers: { Authorization: "Bearer " + token } });

    return "Subject Data updated successfully";
}

//function to delete a student record
export const deleteStudent = async(studentId) => {
    await axios.delete(API_URL + `student/delete/${studentId}`, { headers: { Authorization: "Bearer " + authHeader() } });
    return "Student Deleted Successfully";
}

//function to update a student record
export const updateStudent = async(id, studentName, email, phone) => {
    var token = authHeader();

    await axios.put(API_URL + "student/update", {
        id,
        studentName,
        email,
        phone
    }, { headers: { Authorization: "Bearer " + token } });

    return "Data saved successfully";
}

//function to delete an instructor 
export const deleteInstructor = async(id) => {
    await axios.delete(API_URL + `instructor/delete/${id}`, { headers: { Authorization: "Bearer " + authHeader() } });
    return "Instructor Deleted Successfully";
}

//function to update an instructor record
export const updateInstructor = async(id, instructor_name, email, phone) => {
    var token = authHeader();

    await axios.put(API_URL + "instructor/update", {
        id,
        instructor_name,
        email,
        phone
    }, { headers: { Authorization: "Bearer " + token } });

    return "Data saved successfully";
}

//function to get all requests 
export const getAllRequests = async() => {
    var token = authHeader();
    const responseList = await axios.get(API_URL + "request/getAll", { headers: { Authorization: "Bearer " + token } });
    return responseList.data;
}

//to get all requests for a given student
export const getRequestByStudentId = async(id) => {
    var token = authHeader();
    const responseList = await axios.get(API_URL + `request/getbyStudent/${id}`, { headers: { Authorization: "Bearer " + token } });
    return responseList.data;
}

//to get all requests for the subjects taught by a given instructor
export const getRequestByInstructorId = async(id) => {
    var token = authHeader();
    const responseList = await axios.get(API_URL + `request/getbyInstructorId/${id}`, { headers: { Authorization: "Bearer " + token } });
    return responseList.data;
}

//to get requests by the given subject id
export const getRequestBySubjectCode = async(id) => {
    var token = authHeader();
    const responseList = await axios.get(API_URL + `request/getbySubjectId/${id}`, { headers: { Authorization: "Bearer " + token } });
    return responseList.data;
}

//to get request by request id
export const getRequestById = async(request_id) => {
    const response = await axios.get(API_URL + `request/getbyID/${request_id}`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to register a request
export const registerRequest = async(student, subject, startDate, endDate) => {
    await axios.post(API_URL + `request/save`, {
        student,
        subject,
        startDate,
        endDate
    }, { headers: { Authorization: "Bearer " + authHeader() } });

    return "Request Added Successfully";
}

//to delete a request 
export const deleteRequest = async(slno) => {
    const response = await axios.delete(API_URL + `request/delete/${slno}`, { headers: { Authorization: "Bearer " + authHeader() } });
}

//to accept the request, and make a student subject record from it
export const accept = async(request) => {
    const response = await axios.post(API_URL + `studentSubject/accept`, {
        slno: request.slno,
        subject: request.subject,
        student: request.student,
        startDate: request.startDate,
        endDate: request.endDate
    }, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get all the subjects being taught
export const getSubjects = async() => {
    const subjects = await axios.get(API_URL + "subject/allSubjects", { headers: { Authorization: "Bearer " + authHeader() } });
    return subjects.data;
}

//to get a particular subject by the subject code
export const getSubjectByCode = async(subject_code) => {
    const response = await axios.get(API_URL + "subject/" + subject_code, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get subjects taught by a particular instructor
export const getSubjectByInstructorId = async(id) => {
    const response = await axios.get(API_URL + "subject/getByInstructorId/" + id, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get student record using USER ID of the student
export const getStudentByUserId = async() => {
    var token = authHeader();
    const response = await axios.get(API_URL + "student/user/getStudent", { headers: { Authorization: "Bearer " + token } });
    return response.data;
}

//to get instructor record using USER ID of the instructor
export const getInstructorByUserId = async() => {
    var token = authHeader();
    const response = await axios.get(API_URL + "instructor/user/getInstructor", { headers: { Authorization: "Bearer " + token } });
    return response.data;
}

//to get all students in the institute
export const getStudents = async() => {
    const response = await axios.get(API_URL + `student/getAll`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get list of all instructors
export const getInstructors = async() => {
    const response = await axios.get(API_URL + `instructor/getAll`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get all subject student records 
export const getSubjectStudents = async() => {
    const response = await axios.get(API_URL + `studentSubject/getAll`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

// to get student subject records for a particular student and instructor
export const getForInstructorAndStudent = async(student) => {
    const response = await axios.get(API_URL + `studentSubject/getForStudentAndInstructor/${student.id}`, {headers:{Authorization:"Bearer "+authHeader()}});
    return response.data;
}

//to get student subject records for a particular subject
export const getSubjectStudentsBySubjectCode = async(id) => {
    const response = await axios.get(API_URL + `studentSubject/getBySubject/${id}`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to get student subject records by student id
export const getSubjectStudentsByStudentId = async(id) => {
    var token = authHeader();
    const response = await axios.get(API_URL + `studentSubject/getByStudent/${id}`, { headers: { Authorization: "Bearer " + token } });
    return response.data;
}

//to get student subject records by instructor id of the subjects
export const getSubjectStudentByInstructorId = async(id) => {
    var token = authHeader();
    const response = await axios.get(API_URL + `studentSubject/getByInstructor/${id}`, { headers: { Authorization: "Bearer " + token } });
    return response.data;
}

//to get student subject record by id
export const getSubjectStudentsById = async(id) => {
    const response = await axios.get(API_URL + `studentSubject/getbyID/${id}`, { headers: { Authorization: "Bearer " + authHeader() } });
    return response.data;
}

//to delete a student subject record
export const deleteSubjectStudent = async(id) => {
    await axios.delete(API_URL + `studentSubject/delete/${id}`, { headers: { Authorization: "Bearer " + authHeader() } });
}

//to assign an instructor to a given subject
export const assignInstructortoSubject = async(id,instructor)=>{
    await axios.put(API_URL+"subject/assignInstructor/"+id,{
        id: instructor.id,
        instructor_name: instructor.instructor_name,
        email: instructor.email,
        phone: instructor.phone
    }, { headers: {Authorization:"Bearer "+authHeader()}});
}

//to remove the instructor assigned for the given subject
export const removeInstructorFromSubject = async(id) => {
    const response = await axios.put(API_URL + `subject/removeInstructor/${id}`, {}, {headers: {Authorization: "Bearer "+authHeader()}});
    return response.data;
}