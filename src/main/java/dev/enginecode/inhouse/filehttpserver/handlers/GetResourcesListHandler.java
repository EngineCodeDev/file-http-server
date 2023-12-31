package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.controller.GetResourcesNamesListEndpoint.RequestPathContent;
import dev.enginecode.inhouse.filehttpserver.service.GetResourcesListService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class GetResourcesListHandler {
    @Value("${root.directory}")
    private String rootDirectory;
    private final Logger logger = Logger.getLogger(GetResourcesListHandler.class.getName());
    private final GetResourcesListService filesListService;

    public GetResourcesListHandler(GetResourcesListService filesListService) {
        this.filesListService = filesListService;
    }

    public RequestPathContent handle(String requestURI) {
        String pathFromRoot = getProcessedURI(requestURI);
        logger.info("Started handling GET request with request URI: " + requestURI);

        if (isFile((rootDirectory + pathFromRoot))) {
            logger.info(String.format("Resource under path /%s is file, returning without content", pathFromRoot));
            return RequestPathContent.asFile(pathFromRoot);
        }

        List<String> fileNames = new ArrayList<>();
        List<String> folderNames = new ArrayList<>();
        filesListService.getAll(rootDirectory + pathFromRoot)
                .stream()
                .map(item -> isDirectory(rootDirectory + pathFromRoot + "/" + item) ? folderNames.add(item) : fileNames.add(item))
                .forEach(x -> {});

        return RequestPathContent.of(pathFromRoot, folderNames, fileNames);

    }


    private static boolean isFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    private static boolean isDirectory(String itemName) {
        try {
            Path path = Paths.get(itemName);
            return Files.isDirectory(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getProcessedURI(String requestURI) {
        String output = requestURI.replaceAll("/+$", "");
        return URLDecoder.decode(output, StandardCharsets.UTF_8);
    }
}
