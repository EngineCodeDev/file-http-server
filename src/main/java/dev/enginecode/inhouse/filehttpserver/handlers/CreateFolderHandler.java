package dev.enginecode.inhouse.filehttpserver.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Component
public class CreateFolderHandler {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(CreateFolderHandler.class.getName());

    public void handle(String folderName, String currentPath) {
        String fullPath = rootDirectory + currentPath + "/" + folderName;
        logger.info("Started handling POST request for creating new folder: " + fullPath);

        File newFolder = new File(fullPath);
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                logger.info("Created folder: " + fullPath);
            } else {
                logger.info("Failed to create folder: " + fullPath);
            }
        }
    }


}
