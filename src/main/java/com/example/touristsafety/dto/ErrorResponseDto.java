package com.example.touristsafety.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private List<String> errors;

    public ErrorResponseDto() {}

    public ErrorResponseDto(LocalDateTime timestamp, int status, String message, String path, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}
