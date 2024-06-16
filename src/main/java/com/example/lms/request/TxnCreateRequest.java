package com.example.lms.request;

import com.example.lms.models.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnCreateRequest {

    @NotBlank(message = "Book No should not be blank")
    private String bookNo;

    @Positive(message = "Amount should be positive")
    private Integer amount;

}
