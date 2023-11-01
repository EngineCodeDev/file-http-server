package dev.enginecode.inhouse.filehttpserver.controller;

import dev.enginecode.inhouse.filehttpserver.service.GetFilesListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class GetFilenamesListEndpoint {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(GetFilenamesListEndpoint.class.getName());
    private final GetFilesListService filesListService;

    public GetFilenamesListEndpoint(GetFilesListService filesListService) {
        this.filesListService = filesListService;
    }

    @GetMapping("/**")
    public String getFilesList(Model model, HttpServletRequest request) {
        String requestURI = getProcessedURI(request.getRequestURI());
        logger.info("requestURI: " + requestURI);

        List<String> filePaths = filesListService.getAll(requestURI)
                .stream()
                .map(filename -> requestURI + "/" + filename).toList();


        model.addAttribute("currentPath", requestURI);
        model.addAttribute("files", filePaths);
        return "files-list";
    }

    private static String getProcessedURI(String requestURI) {
        String output = requestURI.replaceAll("/+$", "");
        return URLDecoder.decode(output, StandardCharsets.UTF_8);
    }

}
