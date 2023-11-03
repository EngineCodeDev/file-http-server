package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class UploadFileHandler {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(UploadFileHandler.class.getName());

    public void handle(String currentPath, MultipartFile file) {
        String uploadedFileName = rootDirectory + currentPath + "/" + file.getOriginalFilename();
        logger.info("Started handling POST request for uploading file to: " + uploadedFileName);

        try {
            byte[] fileBytes = file.getBytes();
            Files.write(Paths.get(uploadedFileName), fileBytes);
        } catch (IOException e) {
            logger.info(String.format("IOException caught during uploading file to %s", uploadedFileName));
            logger.info(String.format("IOException message: %s", e.getMessage()));
            e.printStackTrace();
            throw new ApplicationException("Cannot upload a file");
        }
        logger.info("File written to" + currentPath);
    }

}
