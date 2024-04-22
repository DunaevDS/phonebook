package ru.OIStest.phonebook.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static ru.OIStest.phonebook.constant.Constant.TIME_FORMAT;


public record ApiError(HttpStatus status, String reason, String message, List<String> errors,
                       @JsonFormat(pattern = TIME_FORMAT) LocalDateTime timestamp) {
}
