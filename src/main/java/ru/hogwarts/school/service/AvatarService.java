package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private static final String UPLOAD_DIR = "C:/path/to/upload/directory/";
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }


    public void uploadAvatar(MultipartFile file, Long studentId) throws IOException {
        logger.info("Вызван метод для загрузки аватара для студента с ID");
        Optional<Avatar> optionalAvatar = avatarRepository.findByStudentId(studentId);
        Student student = new Student();
        student.setId(studentId);

        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(file.getOriginalFilename());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);

        String uploadPath = UPLOAD_DIR + studentId + "/";
        Path path = Paths.get(uploadPath);
        Files.createDirectories(path);

        String fileName = file.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, file.getBytes());
        logger.debug("Загружен аватар для студента с ID: " + studentId);
    }


    public byte[] getAvatarImageByStudentId(Long studentId) {
        Optional<Avatar> avatarOptional = avatarRepository.findByStudentId(studentId);
        if (avatarOptional.isPresent()) {
            Avatar avatar = avatarOptional.get();
            logger.info("Вызван метод для получения аватара студента с ID: " + studentId);
            return avatar.getData();
        } else {
            logger.warn("Аватар не найден для студента с ID: " + studentId);
            throw new IllegalArgumentException("Аватар не найден");
        }
    }

    public byte[] getAvatarImage(Long avatarId) throws IOException {
        Optional<Avatar> optionalAvatar = avatarRepository.findById(avatarId);
        if (optionalAvatar.isPresent()) {
            logger.info("Вызван метод для получения аватара с ID: " + avatarId);
            return optionalAvatar.get().getData();
        } else {
            logger.warn("Аватар не найден с ID: " + avatarId);
            throw new IllegalArgumentException("Аватар не найден");
        }
    }

    public Page<Avatar> findAllBy(Pageable pageable) {
        logger.info("Вызван метод для получения страницы аватаров");
        return avatarRepository.findAllBy(pageable);
    }
}

