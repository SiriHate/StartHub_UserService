package org.siri_hate.user_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadAvatar(String username, MultipartFile file);

    String getAvatarUrl(String storedAvatarPath);
}
