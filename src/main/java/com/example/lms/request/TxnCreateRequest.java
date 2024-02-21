package com.example.lms.request;

import com.example.lms.models.Student;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnCreateRequest {
      private String studentContact;

      private String bookNo;

       private Integer amount;
}
