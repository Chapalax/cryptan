package ru.hse.bot.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.hse.bot.dto.ApiErrorResponse;
import ru.hse.bot.exceptions.AddedWalletExistsException;
import ru.hse.bot.exceptions.ChatNotFoundException;
import ru.hse.bot.exceptions.RegisteredUserExistsException;
import ru.hse.bot.exceptions.WalletNotFoundException;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class PullerExceptionsController {
    private ApiErrorResponse createError(@NotNull Throwable exception, String description, HttpStatus httpStatus) {
        ArrayList<String> stacktrace = new ArrayList<>(exception.getStackTrace().length);
        for (StackTraceElement line : exception.getStackTrace()) {
            stacktrace.add(line.toString());
        }
        return new ApiErrorResponse(
                description,
                Integer.toString(httpStatus.value()),
                httpStatus.getReasonPhrase(),
                exception.getMessage(),
                stacktrace
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> argumentTypeMismatch(@NotNull MethodArgumentTypeMismatchException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect argument type.", BAD_REQUEST));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> mediaTypeNotSupported(@NotNull HttpMediaTypeNotSupportedException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect media type.", BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> argumentNotValid(@NotNull MethodArgumentNotValidException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect wallet number", BAD_REQUEST));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiErrorResponse> missingRequestHeader(@NotNull MissingRequestHeaderException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect header.", BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> httpMessageNotReadable(@NotNull HttpMessageNotReadableException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect request body.", BAD_REQUEST));
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFound(@NotNull ChatNotFoundException error) {
        return ResponseEntity.status(NOT_FOUND)
                .body(createError(error, error.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> linkNotFound(@NotNull WalletNotFoundException error) {
        return ResponseEntity.status(NOT_FOUND)
                .body(createError(error, error.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(AddedWalletExistsException.class)
    public ResponseEntity<ApiErrorResponse> addedLinkExists(@NotNull AddedWalletExistsException error) {
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
                .body(createError(error, error.getMessage(), METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(RegisteredUserExistsException.class)
    public ResponseEntity<ApiErrorResponse> registeredUserExists(@NotNull RegisteredUserExistsException error) {
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
                .body(createError(error, error.getMessage(), METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> otherException(@NotNull Exception error) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(createError(error, "Server is not available.", INTERNAL_SERVER_ERROR));
    }
}
