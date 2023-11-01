package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.requests.GetResourceRequest;
import dev.enginecode.inhouse.filehttpserver.service.GetResourcesListService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

@Component
public class GetResourcesListHandler {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(GetResourcesListHandler.class.getName());
    private final GetResourcesListService filesListService;

    public GetResourcesListHandler(GetResourcesListService filesListService) {
        this.filesListService = filesListService;
    }

    public String handle(GetResourceRequest request) {
        logger.info("Started handling GET request with request URI: " + request.requestURI());

        String requestURI = getProcessedURI(request.requestURI());

        File file = new File(rootDirectory + requestURI);
        if (file.exists() && file.isFile()) {
            logger.info(String.format("Redirecting to /download/%s%s", rootDirectory, requestURI));
            return String.format("redirect:/download/%s%s", rootDirectory, requestURI);
        }

        List<String> filePaths = filesListService.getAll(rootDirectory + requestURI)
                .stream()
                .map(filename -> requestURI + "/" + filename).toList();

        request.model().addAttribute("currentPath", requestURI);
        request.model().addAttribute("files", filePaths);

        return "files-list";
    }



    private static String getProcessedURI(String requestURI) {
        String output = requestURI.replaceAll("/+$", "");
        return URLDecoder.decode(output, StandardCharsets.UTF_8);
    }
}
