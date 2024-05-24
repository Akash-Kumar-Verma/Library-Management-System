package com.example.lms.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class GenericResponse <T> {
    private T data;
    private String error;
    private String message; //success/failure
    private Integer code;

}
