package ru.job4j.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.auth.exception.DuplicateEntityException;
import ru.job4j.auth.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.HashMap;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {NullPointerException.class})
    public void handleNullPointerException(Exception e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Some fields are empty");
            put("details", e.getMessage());
        }}));
        log.error(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound(EntityNotFoundException e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
        }}));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public void handleDuplicate(DuplicateEntityException e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
        }}));
    }
}
