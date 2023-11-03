package dev.enginecode.inhouse.filehttpserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public String handle(ApplicationException e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST);

        return "error";
    }
}
