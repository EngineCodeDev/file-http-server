package dev.enginecode.inhouse.filehttpserver.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("TEST")
class CreateFolderEndpointTest {
    @Value("${root.directory}")
    private String rootDirectory;

    @LocalServerPort
    private Integer port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    void shouldCreateFolder() throws URISyntaxException {
        String folderName = "folder_name";
        File folder = new File(rootDirectory + "/" + folderName);
        assertFalse(folder.exists());

        restTemplate.exchange(
                new RequestEntity<>(HttpMethod.POST, new URI(String.format("http://localhost:%s/folder?folderName=%s&currentPath=/", port, folderName))),
                void.class
        );

        assertTrue(folder.exists());
    }

    @AfterEach
    public void cleanup() {
        File folder = new File(rootDirectory);
        if (folder.exists() && folder.isDirectory()) {
            deleteRecursive(folder);
        }
    }

    private void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                deleteRecursive(child);
            }
        }
        file.delete();
    }
}