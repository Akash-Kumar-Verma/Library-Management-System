package com.example.lms.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnReturnRequest {

     private String studentContact;

     private String bookNo;

     private String txnId;

}
