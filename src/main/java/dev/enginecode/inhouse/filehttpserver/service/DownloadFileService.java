package dev.enginecode.inhouse.filehttpserver.service;

import dev.enginecode.inhouse.filehttpserver.exception.ApplicationException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Service
public class DownloadFileService {
    private final Logger logger = Logger.getLogger(DownloadFileService.class.getName());

    public void download(String path, HttpServletResponse response) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            logger.info(String.format("Cannot download resource with path %s", path));
            throw new ApplicationException(String.format("Cannot download resource %s", path));
        }

        logger.info(String.format("Downloading file %s", path));
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            InputStream inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (IOException e) {
            logger.info(String.format("Error during downloading file %s", path));
            throw new ApplicationException("IOException caught: " + e.getMessage());
        }
    }


}
