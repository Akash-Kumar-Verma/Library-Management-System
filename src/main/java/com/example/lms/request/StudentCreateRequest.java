package com.example.lms.request;

import com.example.lms.models.Student;
import com.example.lms.models.StudentType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    private String name;

    private String email;

    private String phoneNo;

    private String address;

    public Student toStudent() {
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .address(this.address)
                .status(StudentType.ACTIVE)
                .build();
    }
}
