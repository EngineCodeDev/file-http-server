package dev.enginecode.inhouse.filehttpserver.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ImageController {

    @GetMapping("/img/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Resource resource = new ClassPathResource("static/img/" + imageName);

        if (resource.exists() && resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
