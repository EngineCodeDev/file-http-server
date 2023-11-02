package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.requests.GetResourceRequest;
import dev.enginecode.inhouse.filehttpserver.service.DownloadFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Component
public class GetFileHandler {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(GetFileHandler.class.getName());
    private final DownloadFileService downloadFileService;

    public GetFileHandler(DownloadFileService downloadFileService) {
        this.downloadFileService = downloadFileService;
    }

    public void handle(GetResourceRequest request, HttpServletResponse response) {
        logger.info("Started handling GET request for download file: " + request.requestURI());

        String requestURI = getProcessedURI(request.requestURI());
        downloadFileService.download(requestURI.replaceAll("^/+", ""), response);

    }



    private static String getProcessedURI(String requestURI) {
        String output = requestURI.replaceAll("/+$", "");
        return URLDecoder.decode(output, StandardCharsets.UTF_8);
    }
}
