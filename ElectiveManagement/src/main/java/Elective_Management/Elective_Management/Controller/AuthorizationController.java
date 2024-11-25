package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Service.JwtUserDetailsService;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import Elective_Management.Elective_Management.model.JwtRequest;
import Elective_Management.Elective_Management.model.JwtResponse;
import Elective_Management.Elective_Management.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// contains all the various end points relating to authentication,
// managed using appropriate configuration methods

@RestController
@CrossOrigin(origins = "*")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    // authenticate / login a user, returns the jwt token if login is successful
    // else returns invalid username or password error message
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        final String token = tokenGenerator(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        User user = this.userDetailsService.getUserByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, user));
    }

    // register a new student and their details can be registered at a separate endpoint (in the student controller file)
    // if the username is unique, returns response ok
    @RequestMapping(value = "/register_student", method = RequestMethod.POST)
    public ResponseEntity<?> saveStudent(@RequestBody JwtRequest user) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        User user1 = this.userDetailsService.saveStudent(dto);
        final String token = tokenGenerator(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(new JwtResponse(token, user1));
    }

    // register a new admin, if the username is unique, returns response ok
    @RequestMapping(value = "/register_admin", method = RequestMethod.POST)
    public ResponseEntity<?> saveAdmin(@RequestBody JwtRequest user) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        User user1 = this.userDetailsService.saveAdmin(dto);
        final String token = tokenGenerator(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(new JwtResponse(token, user1));
    }

    // register a new instructor and their details can be registered at a separate endpoint (in the instructor controller file)
    // if the username is unique, returns response ok
    @RequestMapping(value = "/register_instructor", method = RequestMethod.POST)
    public ResponseEntity<?> saveInstructor(@RequestBody JwtRequest user) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        User user1 = this.userDetailsService.saveInstructor(dto);
        final String token = tokenGenerator(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(new JwtResponse(token, user1));
    }

    // dummy endpoints created only for testing purposes
    // ----------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/dummy_student", method = RequestMethod.GET)
    public String studentPage(){
        return "Hello Student";
    }

    @RequestMapping(value = "/dummy_admin", method = RequestMethod.GET)
    public String adminPage(){
        return "Hello Admin";
    }

    @RequestMapping(value = "/dummy_instructor", method = RequestMethod.GET)
    public String instructorPage(){
        return "Hello Instructor";
    }
    // ----------------------------------------------------------------------------------------------------------

    // perform the actual task of authentication and token generation
    // ----------------------------------------------------------------------------------------------------------
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private String tokenGenerator(String username, String password) throws Exception{
        authenticate(username, password);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return jwtTokenUtil.generateToken(userDetails);
    }
    // ----------------------------------------------------------------------------------------------------------
}
