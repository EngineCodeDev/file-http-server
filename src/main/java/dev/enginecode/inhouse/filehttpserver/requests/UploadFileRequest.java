package dev.enginecode.inhouse.filehttpserver.requests;

import org.springframework.web.multipart.MultipartFile;

public record UploadFileRequest(String currentPath, MultipartFile file) {}
