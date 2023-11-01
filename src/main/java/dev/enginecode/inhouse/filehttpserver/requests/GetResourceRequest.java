package dev.enginecode.inhouse.filehttpserver.requests;

import org.springframework.ui.Model;

public record GetResourceRequest(
        String requestURI,
        Model model
) {}
