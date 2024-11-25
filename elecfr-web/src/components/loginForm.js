import { useState } from "react";
import { useLoginFormValidator } from "../validators/loginFormValidator";
import { getCurrentUser, login } from "../services/auth_services";
import { useNavigate } from "react-router-dom";
import { getInstructorByUserId, getStudentByUserId } from "../services/user_services";
import { setInstructorInStorage, setPersonalInstructorInStorage, setPersonalStudentInStorage, setStudentInStorage } from "../services/localStorage_services";
import image1 from "../assets/image1.png";

// Login form
const LoginForm = ({setCurrentUser, setIsAdmin, setIsStudent,setIsInstructor}) => {
  const [form, setForm] = useState({
    username: "",
    password: ""
  });

  const navigate = useNavigate();

  const [message, setMessage] = useState("");

  const {errors, validateForm} = useLoginFormValidator(form);

  // when any form field is updated, check validity of the field
  const onUpdateField = e => {
    const nextFormState = {
      ...form,
      [e.target.name]: e.target.value,
    };
    setForm(nextFormState);
  };

  // in case instructor is logged in, set in local storage
  const setCurrentInstructor = async() => {
    const temp = await getInstructorByUserId();
    setPersonalInstructorInStorage(temp);
    setInstructorInStorage(temp);
    return temp;
  }

  // in case student is logged in, set in local storage
  const setCurrentStudent = async() => {
    const temp = await getStudentByUserId();
    setPersonalStudentInStorage(temp);
    setStudentInStorage(temp);
    return temp;
  }

  // when Login button is clicked, perform all validation checks
  // and if valid, login the user/admin and display the books page, else display the error message 
  const onSubmitForm = e => {
    setMessage("")
    e.preventDefault();
    // checking validity of form fields    
    const { isValid } = validateForm({ form, errors, forceTouchErrors: true });
    // if not valid
    if (!isValid) return;
    // if valid
    login(form.username, form.password).then(
      response => {

        const user = getCurrentUser()
        setCurrentUser(user)

        if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "ADMIN"){
          setIsAdmin(true)
          navigate("/home")
        }

        if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "STUDENT"){
          setIsStudent(true);
          setCurrentStudent();
          navigate("/home")
        }

        if (user && user.user && user.user.roles[0] && user.user.roles[0].name && user.user.roles[0].name === "INSTRUCTOR"){
          setIsInstructor(true)
          setCurrentInstructor();
          navigate("/home")
        }
        
      },
      error => {
        const resMessage =
        "Invalid username or password"
        setMessage(resMessage)
      }
    )
    
  };

  // rendering form components on the screen
  return (

    <div className="col-md-12">
      {/* image of person */}
        <div className="card card-container">
              <img
                src={image1}
                alt="profile-img"
                className="profile-img-card"
              />

              {/* The actual form */}
            <form onSubmit={onSubmitForm}>
              {/* username input field */}
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                    className="form-control"
                    type="text"
                    aria-label="Username"
                    name="username"
                    value={form.username}
                    onChange={onUpdateField}
                    />

                    {errors.username.dirty && errors.username.error ? (
                            <div className="alert alert-danger" role="alert">{errors.username.message}</div>
                            ) : null}
                </div>

              {/* password input field */}
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                    className="form-control"
                    type="password"
                    aria-label="Password field"
                    name="password"
                    value={form.password}
                    onChange={onUpdateField}
                    />

                    {errors.password.dirty && errors.password.error ? (
                            <div className="alert alert-danger" role="alert">{errors.password.message}</div>
                            ) : null}
                </div>

                {/* submit button */}
                <div className="form-group">
                    <button className="btn-block form-button1" type="submit">
                    Login
                    </button>
                </div>

                {/* display error message if any */}
                {message ? 
                  <div className="alert alert-danger" role="alert">{message}</div>
                : null}
            </form>
        </div>
    </div>
  );
};

export default LoginForm;