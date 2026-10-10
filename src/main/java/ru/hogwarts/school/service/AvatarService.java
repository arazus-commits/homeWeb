package ru.hogwarts.school.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, org.springframework.web.multipart.MultipartFile avatarFile) throws java.io.IOException {
        logger.info("Was invoked method for upload avatar");
        studentService.findStudent(studentId);
        ru.hogwarts.school.model.Student student = studentService.findStudent(studentId);
        if (student == null) {
            return;
        }
        String avatarsDir = "C:\\photo\\avatars";
        java.nio.file.Path filePath = java.nio.file.Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        java.nio.file.Files.createDirectories(filePath.getParent());
        java.nio.file.Files.deleteIfExists(filePath);
        try (java.io.InputStream is = avatarFile.getInputStream();
             java.io.OutputStream os = java.nio.file.Files.newOutputStream(filePath, java.nio.file.StandardOpenOption.CREATE_NEW);
             java.io.BufferedInputStream bis = new java.io.BufferedInputStream(is, 1024);
             java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatarFile.getBytes();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find avatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }


}



