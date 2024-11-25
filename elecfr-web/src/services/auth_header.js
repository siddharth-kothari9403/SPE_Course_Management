//function to get the token from the local storage
export default function authHeader() {
    const user = JSON.parse(localStorage.getItem('user'));
    
    if (user && user.token) {
      return user.token;
    } else {
      return {};
    }
}