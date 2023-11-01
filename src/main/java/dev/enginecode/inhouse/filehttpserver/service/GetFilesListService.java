package dev.enginecode.inhouse.filehttpserver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class GetFilesListService {
    @Value("${root.directory}")
    String rootDirectory;
    private final Logger logger = Logger.getLogger(GetFilesListService.class.getName());

    public List<String> getAll(String path) {
        if (path.startsWith("/")){
            path = path.substring(1);
        }
        logger.info("directoryPath: " + path);
        File dataDirectory = new File(rootDirectory + "/" + path);

        if (dataDirectory.isDirectory()) {
            File[] fileList = dataDirectory.listFiles();
            assert fileList != null;
            return Arrays.stream(fileList)
                    .map(File::getName)
                    .toList();
        }

        throw new RuntimeException(String.format("%s is not a valid directory", path));
    }


}
