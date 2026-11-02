package com.phdev.library.handler;

import java.time.Instant;

public class StandardError {

    private Instant moment;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(Instant moment, Integer status, String error, String message, String path) {
        this.moment = moment;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getMoment() {
        return moment;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
