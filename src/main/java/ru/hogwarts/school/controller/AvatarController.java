package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private final AvatarService avatarService;

    @Autowired

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Object> loading(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return org.springframework.http.ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/from-db")
    public ResponseEntity<byte[]> loadingBase(@PathVariable Long studentId, jakarta.servlet.http.HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);
        response.setStatus(200);
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int) avatar.getFileSize());
        try (java.io.InputStream is = new java.io.ByteArrayInputStream(avatar.getData());
             java.io.OutputStream os = response.getOutputStream()) {
            is.transferTo(os);
        }
        return org.springframework.http.ResponseEntity.ok().build();
    }

        @GetMapping(value = "/{studentId}/from-file")
        public ResponseEntity<byte[]> loadingDisk (@PathVariable Long studentId) throws IOException {
            Avatar avatar = avatarService.findAvatar(studentId);
            java.nio.file.Path path = java.nio.file.Path.of(avatar.getFilePath());
            byte[] data = java.nio.file.Files.readAllBytes(path);
            return org.springframework.http.ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType(avatar.getMediaType()))
                    .contentLength(avatar.getFileSize())
                    .body(data);

        }
    @GetMapping("/page")
    public org.springframework.http.ResponseEntity<java.util.List<ru.hogwarts.school.model.Avatar>> getAllAvatars(
            @RequestParam int page,
            @RequestParam int size) {
        return org.springframework.http.ResponseEntity.ok(avatarService.getAllAvatars(page, size));
    }

}
