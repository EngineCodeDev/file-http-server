package dev.enginecode.inhouse.filehttpserver.service;

import dev.enginecode.inhouse.filehttpserver.exception.ApplicationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class GetResourcesListService {
    private final Logger logger = Logger.getLogger(GetResourcesListService.class.getName());

    public List<String> getAll(String path) {
        logger.info("directoryPath: " + path);
        File dataDirectory = new File(path);

        if (dataDirectory.isDirectory()) {
            File[] fileList = dataDirectory.listFiles();
            assert fileList != null;
            return Arrays.stream(fileList)
                    .map(File::getName)
                    .toList();
        }

        throw new ApplicationException(String.format("%s is not a valid directory", path));
    }


}
