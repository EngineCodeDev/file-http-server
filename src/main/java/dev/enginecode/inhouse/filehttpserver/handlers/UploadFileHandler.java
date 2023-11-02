package dev.enginecode.inhouse.filehttpserver.handlers;

import dev.enginecode.inhouse.filehttpserver.exception.ApplicationException;
import dev.enginecode.inhouse.filehttpserver.requests.UploadFileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class UploadFileHandler {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(UploadFileHandler.class.getName());

    public void handle(UploadFileRequest request) {
        String uploadedFileName = rootDirectory + request.currentPath() + "/" + request.file().getOriginalFilename();
        logger.info("Started handling POST request for uploading file to: " + uploadedFileName);

        try {
            byte[] fileBytes = request.file().getBytes();
            Files.write(Paths.get(uploadedFileName), fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("IOException caught");
        }
        logger.info("File written to" + request.currentPath());
    }

}
