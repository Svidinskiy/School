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
import java.util.List;
import java.util.Optional;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private static final String UPLOAD_DIR = "C:/path/to/upload/directory/";

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }


    public void uploadAvatar(MultipartFile file, Long studentId) throws IOException {
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
    }


    public byte[] getAvatarImageByStudentId(Long studentId) {
        Optional<Avatar> avatarOptional = avatarRepository.findByStudentId(studentId);
        if (avatarOptional.isPresent()) {
            Avatar avatar = avatarOptional.get();
            return avatar.getData();
        } else {
            throw new IllegalArgumentException("Avatar not found");
        }
    }

    public byte[] getAvatarImage(Long avatarId) throws IOException {
        Optional<Avatar> optionalAvatar = avatarRepository.findById(avatarId);
        if (optionalAvatar.isPresent()) {
            return optionalAvatar.get().getData();
        } else {
            throw new IllegalArgumentException("Avatar not found");
        }
    }

    public Page<Avatar> findAllBy(Pageable pageable) {
        return avatarRepository.findAllBy(pageable);
    }
}

