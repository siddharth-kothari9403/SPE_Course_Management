import React, {useEffect} from "react";
import { useNavigate } from "react-router-dom";

// first page that shows up, in case user is logged in takes them to the subjects page, if not then to the login page
const Home = ({currentUser}) => {
    const navigate = useNavigate();

    useEffect(() => {
        const func = () => {
            if (currentUser){
                navigate("/subjects")
            }else{
                navigate("/login")
            }
        }
        func();
    }, [])

    return (
        <> 
        </>
    );  
}

export default Home;