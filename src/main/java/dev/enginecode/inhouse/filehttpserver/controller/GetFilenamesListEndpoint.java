package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.GetResourceRequestHandler;
import dev.enginecode.inhouse.filehttpserver.requests.GetResourceRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GetFilenamesListEndpoint {
    private final GetResourceRequestHandler handler;

    public GetFilenamesListEndpoint(GetResourceRequestHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/**")
    public String getFilesList(Model model, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handler.handle(new GetResourceRequest(requestURI, model));
    }


}
