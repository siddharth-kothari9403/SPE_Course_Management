package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

// all the handlers handle specific exceptions and return appropriate error messages

@ControllerAdvice
@CrossOrigin(origins = "*")
public class ExceptionController {
    @ExceptionHandler(value = StudentSubjectNotFoundException.class)
    public ResponseEntity<Object> customerCabNotFound(StudentSubjectNotFoundException studentSubjectNotFoundException){
        return new ResponseEntity<>("Student Elective pair does not exist with the given id.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InstructorNotFoundException.class)
    public ResponseEntity<Object> instructorNotFound(InstructorNotFoundException instructorNotFoundException){
        return new ResponseEntity<>("Instructor does not exist with given id.",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<Object> studentNotFound(StudentNotFoundException studentNotFoundException) {
        return new ResponseEntity<>("No Student exists with the given ID",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RequestNotFoundException.class)
    public ResponseEntity<Object> requestNotFound(RequestNotFoundException requestNotFoundException) {
        return new ResponseEntity<>("No request exists with the given ID",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SubjectNotFoundException.class)
    public ResponseEntity<Object> subjectNotFound(SubjectNotFoundException subjectNotFoundException) {
        return new ResponseEntity<>("No subject exists with the given ID",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> duplicateValueError(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
    }
}
