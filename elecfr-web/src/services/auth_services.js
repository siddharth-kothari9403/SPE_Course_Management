import axios from "axios";
import authHeader from './auth_header';

//the requests to the backend are sent to this port
const API_URL = "http://localhost:8080/";

//login function, takes username and password and returns the user object along with the token to the user
//which is then stored in local storage
export const login = async(username, password) => {
    const response = await axios
        .post(API_URL + "authenticate", {
            username,
            password
        });
    if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
};

//logout function removes the token and user from local storage
export const logout = () => {
    localStorage.removeItem("user");
};

//function to register admin, takes username and password, and stores it
export const registerAdmin = async(username, password) => {
    await axios.post(API_URL + "register_admin", {
        username,
        password
    }, { headers: { Authorization: "Bearer " + authHeader() } });

    return "Admin added successfully";
};

//function to register student, creates the user record as well as the student record associated with it
export const registerStudent = async(username, password, studentName, email, phone) => {
    const response = await axios.post(API_URL + "register_student", {
        username,
        password
    }, { headers: { Authorization: "Bearer " + authHeader() } });

    //temporarily gets token from backend, uses it to store the student info
    var token = "";

    if (response.data.token) {
        token = response.data.token;
    }

    await axios.post(API_URL + "student/save", {
        studentName,
        email,
        phone
    }, { headers: { Authorization: "Bearer " + token } });

    return "Signup successful";
}

//register procedure similar to student
export const registerInstructor = async(username, password, instructor_name, email, phone) => {
    const response = await axios.post(API_URL + "register_instructor", {
        username,
        password
    }, { headers: { Authorization: "Bearer " + authHeader() } });

    var token = "";

    if (response.data.token) {
        token = response.data.token;
        localStorage.setItem("user", JSON.stringify(response.data));
    }

    await axios.post(API_URL + "instructor/save", {
        instructor_name,
        email,
        phone
    }, { headers: { Authorization: "Bearer " + token } });

    return "Signup successful";
}

//function to get user from local storage
export const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem('user'));
}