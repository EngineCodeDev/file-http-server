package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.GetFileHandler;
import dev.enginecode.inhouse.filehttpserver.requests.GetResourceRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GetFileEndpoint {
    private final GetFileHandler handler;

    public GetFileEndpoint(GetFileHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/download/**")
    public String downloadFile(Model model, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();

        return handler.handle(
                new GetResourceRequest(requestURI.substring("/download".length()), model),
                response
        );
    }


}
