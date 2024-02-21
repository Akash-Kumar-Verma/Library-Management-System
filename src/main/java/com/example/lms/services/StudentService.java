package com.example.lms.services;

import com.example.lms.models.Operator;
import com.example.lms.models.Student;
import com.example.lms.models.StudentFilterType;
import com.example.lms.models.StudentType;
import com.example.lms.repository.StudentRepository;
import com.example.lms.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
       List<Student> studentList=studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDB=null;

        if(studentList == null || studentList.isEmpty()){
            studentFromDB = studentRepository.save(studentCreateRequest.toStudent());
            return studentFromDB;
        }
        studentFromDB = studentList.get(0);
        return studentFromDB;
    }

    public List<Student> filter(StudentFilterType filterBy, Operator operator, String value) {
        switch (operator) {
            case EQUALS:
                switch (filterBy) {
                    case CONTACT :
                        return studentRepository.findByPhoneNo(value);
                }

            default:
                return new ArrayList<>();
        }
    }
}
