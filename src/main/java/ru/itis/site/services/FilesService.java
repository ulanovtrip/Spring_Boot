package ru.itis.site.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.site.dto.FileUrlDto;

import javax.servlet.http.HttpServletResponse;

/**
 * 31.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface FilesService {
    FileUrlDto save(MultipartFile file);

    void writeFileToResponse(String fileName, HttpServletResponse response);
}
