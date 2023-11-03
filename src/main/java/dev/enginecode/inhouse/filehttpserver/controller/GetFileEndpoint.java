package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.GetFileHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GetFileEndpoint {
    private final GetFileHandler handler;

    public GetFileEndpoint(GetFileHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/download/**")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();

        handler.handle(requestURI.substring("/download".length()), response);
    }


}
