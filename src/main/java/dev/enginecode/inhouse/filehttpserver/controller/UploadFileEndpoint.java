package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.UploadFileHandler;
import dev.enginecode.inhouse.filehttpserver.requests.UploadFileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class UploadFileEndpoint {
    @Value("${root.directory}")
    String rootDirectory;
    private final UploadFileHandler handler;

    public UploadFileEndpoint(UploadFileHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/upload/**")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("currentPath") String currentPath) {
        handler.handle(new UploadFileRequest(currentPath, file));
        return new RedirectView(currentPath);
    }

}
