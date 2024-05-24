package com.example.lms.services;

import com.example.lms.models.Operator;
import com.example.lms.models.Student;
import com.example.lms.models.StudentFilterType;
import com.example.lms.repository.StudentRepository;
import com.example.lms.request.StudentCreateRequest;
import com.example.lms.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity<GenericResponse<Student>> createStudent(StudentCreateRequest studentCreateRequest) {

        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB = null;

        if (studentList == null || studentList.isEmpty()) {
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            GenericResponse<Student> response = GenericResponse.<Student>builder().data(studentFromDB).code(200).message("Success").build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        GenericResponse<Student> response = GenericResponse.<Student>builder().data(null).code(200).message("phoneNo should be unique.").error("phoneNo is already exist").build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<Student> filter(StudentFilterType filterBy, Operator operator, String value) {
        switch (operator) {
            case EQUALS:
                return switch (filterBy) {
                    case CONTACT -> studentRepository.findByPhoneNo(value);
                    case NAME -> studentRepository.findByName(value);
                    case EMAIL -> studentRepository.findByEmail(value);
                };
            default:
                return new ArrayList<>();
        }
    }
}
