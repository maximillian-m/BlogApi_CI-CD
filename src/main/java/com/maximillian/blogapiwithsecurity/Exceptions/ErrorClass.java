package com.maximillian.blogapiwithsecurity.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class ErrorClass {
    private LocalDateTime timeStamp;
    private int Status;
    private String message;
}
