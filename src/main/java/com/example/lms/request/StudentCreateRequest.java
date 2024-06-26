package com.example.lms.request;

import com.example.lms.models.Student;
import com.example.lms.models.StudentType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {
    @NotBlank(message = "name must not be blank")
    private String name;

    private String email;
    @NotBlank(message = "Phone No must not be blank")
    private String phoneNo;

    private String address;

    private String password;

    private String authority;


    public Student toStudent() {
        return Student.builder().
                name(this.name).
                email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                password(this.password).
                authority(this.authority).
                status(StudentType.ACTIVE).
                build();
    }
}
