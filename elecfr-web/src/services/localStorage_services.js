//The following variables are stored in local storage, to avoid loss on refresh
//1. Currently logged in student (represented by personalStudent)
//2. Currently logged in instructor (represented by personalInstructor)
//3. Current student
//4. Current instructor
//5. Current subject
//6. Current Request
//7. Current StudentSubject record

//the following are the methods to set, get and remove the same from local storage.

export const setSubjectInStorage = (subject) => {
    localStorage.setItem("subject", JSON.stringify(subject));
}

export const getSubjectFromStorage = () => {
    const subject = JSON.parse(localStorage.getItem("subject"));
    return subject;
}

export const removeSubjectFromStorage = () => {
    localStorage.removeItem("subject");
}

export const setStudentInStorage = (student) => {
    localStorage.setItem("student", JSON.stringify(student));
}

export const getStudentFromStorage = () => {
    const student = JSON.parse(localStorage.getItem("student"));
    return student;
}

export const removeStudentFromStorage = () => {
    localStorage.removeItem("student");
}

export const setPersonalStudentInStorage = (student) => {
    localStorage.setItem("personalStudent", JSON.stringify(student));
}

export const getPersonalStudentFromStorage = () => {
    const student = JSON.parse(localStorage.getItem("personalStudent"));
    return student;
}

export const removePersonalStudentFromStorage = () => {
    localStorage.removeItem("personalStudent");
}

export const setInstructorInStorage = (instructor) => {
    localStorage.setItem("instructor", JSON.stringify(instructor));
}

export const getInstructorFromStorage = () => {
    const student = JSON.parse(localStorage.getItem("instructor"));
    return student;
}

export const removeInstructorFromStorage = () => {
    localStorage.removeItem("instructor");
}

export const setPersonalInstructorInStorage = (instructor) => {
    localStorage.setItem("personalInstructor", JSON.stringify(instructor));
}

export const getPersonalInstructorFromStorage = () => {
    const student = JSON.parse(localStorage.getItem("personalInstructor"));
    return student;
}

export const removePersonalInstructorFromStorage = () => {
    localStorage.removeItem("personalInstructor");
}

export const setRequestInStorage = (request) => {
    localStorage.setItem("request", JSON.stringify(request));
}

export const getRequestFromStorage = () => {
    const request = JSON.parse(localStorage.getItem("request"));
    return request;
}

export const removeRequestFromStorage = () => {
    localStorage.removeItem("request");
}

export const setSubjectStudentInStorage = (subjectStudent) => {
    localStorage.setItem("subjectStudent", JSON.stringify(subjectStudent));
}

export const getSubjectStudentFromStorage = () => {
    const subjectStudent = JSON.parse(localStorage.getItem("subjectStudent"));
    return subjectStudent;
}

export const removeSubjectStudentFromStorage = async() => {
    localStorage.removeItem("subjectStudent");
}