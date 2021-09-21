package ru.itis.site.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.site.dto.FileUrlDto;

import javax.servlet.http.HttpServletResponse;

public interface FilesService {

    FileUrlDto save(MultipartFile file);

    void writeFileToResponse(String fileName, HttpServletResponse response);
}
