package com.example.lms.services;

import com.example.lms.models.Operator;
import com.example.lms.models.Student;
import com.example.lms.models.StudentFilterType;
import com.example.lms.repository.StudentRepository;
import com.example.lms.request.StudentCreateRequest;
import com.example.lms.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Value("${student.authority}")
    private String studentAuthority;

    public ResponseEntity<GenericResponse<Student>> createStudent(StudentCreateRequest studentCreateRequest) {

        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB = null;

        if (studentList == null || studentList.isEmpty()) {
            studentCreateRequest.setAuthority(studentAuthority);
            System.out.println("Student Authority "+studentAuthority);
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            GenericResponse<Student> response = GenericResponse.<Student>builder().data(studentFromDB).code(200).message("Success").build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        GenericResponse<Student> response = GenericResponse.<Student>builder().data(null).code(400).message("phoneNo should be unique.").error("phoneNo is already exist").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public List<Student> filter(StudentFilterType filterBy, Operator operator, String value) {
        if (Objects.requireNonNull(operator) == Operator.EQUALS) {
            return switch (filterBy) {
                case CONTACT -> studentRepository.findByPhoneNo(value);
                case NAME -> studentRepository.findByName(value);
                case EMAIL -> studentRepository.findByEmail(value);
            };
        }
        return new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        if(!studentRepository.findByPhoneNo(phoneNo).isEmpty()){
            return studentRepository.findByPhoneNo(phoneNo).get(0);
        }
        return null;
    }
}
