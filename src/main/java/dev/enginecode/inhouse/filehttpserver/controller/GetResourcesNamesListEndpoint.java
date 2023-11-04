package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.handlers.GetResourcesListHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class GetResourcesNamesListEndpoint {
    @Value("${root.directory}")
    private String rootDirectory;
    private final GetResourcesListHandler handler;

    public GetResourcesNamesListEndpoint(GetResourcesListHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/**")
    public String getFilesList(Model model, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        RequestPathContent content = handler.handle(requestURI);

        if (content.isFile) {
            return String.format("redirect:/download/%s%s", rootDirectory, content.pathFromRoot);
        }

        model.addAttribute("currentPath", content.pathFromRoot);
        model.addAttribute("folders", content.folderNames);
        model.addAttribute("files", content.fileNames);
        return "files-list";
    }

    public record RequestPathContent(boolean isFile, String pathFromRoot, List<String> folderNames, List<String> fileNames) {
        public static RequestPathContent asFile(String pathFromRoot) {
            return new RequestPathContent(true, pathFromRoot, null, null);
        }

        public static RequestPathContent of(String pathFromRoot, List<String> folderNames, List<String> fileNames) {
            return new RequestPathContent(false, pathFromRoot, folderNames, fileNames);
        }
    }

}
