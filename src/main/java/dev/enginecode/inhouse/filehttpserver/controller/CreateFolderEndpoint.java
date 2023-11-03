package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.CreateFolderHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class CreateFolderEndpoint {
    private final CreateFolderHandler handler;

    public CreateFolderEndpoint(CreateFolderHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/folder")
    public RedirectView createFolder(
            @RequestParam("folderName") String folderName,
            @RequestParam("currentPath") String currentPath) {
        handler.handle(folderName, currentPath);
        return new RedirectView(currentPath);
    }

}
