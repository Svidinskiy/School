package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarController(AvatarService avatarService, AvatarRepository avatarRepository) {
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }


    @PostMapping("/upload")
    public void uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam Long studentId) throws IOException {
        avatarService.uploadAvatar(file, studentId);
    }

    @Transactional
    @GetMapping(value = "/bd/{studentId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatarImageByStudentId(@PathVariable Long studentId) {
        byte[] avatarData = avatarService.getAvatarImageByStudentId(studentId);
        return ResponseEntity.ok().body(avatarData);
    }

    @GetMapping(value = "/image/{avatarId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatarImage(@PathVariable Long avatarId) throws IOException {
        byte[] avatarData = avatarService.getAvatarImage(avatarId);
        return ResponseEntity.ok().body(avatarData);
    }

    @GetMapping("/paged")
    public Page<Avatar> getAvatarsPaged(Pageable pageable) {
        return avatarRepository.findAllBy(pageable);
    }

}

