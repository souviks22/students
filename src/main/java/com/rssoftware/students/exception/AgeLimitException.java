package com.rssoftware.students.exception;

public class AgeLimitException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Student should have an age between 10 and 40";
    }
}
