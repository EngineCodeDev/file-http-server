package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.requests.GetResourceRequest;
import dev.enginecode.inhouse.filehttpserver.service.GetFilesListService;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

@Component
public class GetResourceRequestHandler {
    private final Logger logger = Logger.getLogger(GetResourceRequestHandler.class.getName());
    private final GetFilesListService filesListService;

    public GetResourceRequestHandler(GetFilesListService filesListService) {
        this.filesListService = filesListService;
    }

    public String handle(GetResourceRequest request) {
        logger.info("Started handling GET request with request URI: " + request.requestURI());

        String requestURI = getProcessedURI(request.requestURI());
        List<String> filePaths = filesListService.getAll(requestURI)
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
