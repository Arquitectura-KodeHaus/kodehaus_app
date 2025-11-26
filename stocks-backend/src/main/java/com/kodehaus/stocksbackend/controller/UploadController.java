package com.kodehaus.stocksbackend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
@CrossOrigin(origins = "*")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> handleUpload(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No file provided"));
            }

            Path dir = Path.of(uploadDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            String timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now()).replaceAll(":", "-");
            String original = Path.of(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename()).getFileName().toString();
            String targetName = timestamp + "__" + original;
            Path target = dir.resolve(targetName);

            try (var in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            Map<String, Object> resp = new HashMap<>();
            resp.put("filename", targetName);
            resp.put("originalName", original);
            resp.put("size", file.getSize());
            resp.put("uploadedAt", Instant.now().toString());
            resp.put("message", "File saved on server");
            resp.put("id", id);
            resp.put("uploaderName", name);
            resp.put("uploaderEmail", email);

            log.info("File uploaded: {} (id={} from={} email={}) saved to {}", original, id, name, email, target.toAbsolutePath());

            return ResponseEntity.ok(resp);

        } catch (IOException ex) {
            log.error("Error saving uploaded file", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unable to save file"));
        }
    }
}
