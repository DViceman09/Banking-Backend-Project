package com.sandesh.banking_app.Exceptions;

public record ErrorDetails(java.time.LocalTime timestamp, String message, String details, String errorCode) {
}
