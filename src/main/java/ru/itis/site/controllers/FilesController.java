package ru.itis.site.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.site.dto.FileUrlDto;
import ru.itis.site.services.FilesService;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

/**
 * 31.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Controller
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PermitAll
    @GetMapping("/files/upload")
    public String getFileUploadPage() {
        return "file_upload";
    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        filesService.writeFileToResponse(fileName, response);
    }

    @PermitAll
    @PostMapping("/files")
    @ResponseBody
    public FileUrlDto saveFile(@RequestParam("file") MultipartFile file)  {
        return filesService.save(file);
    }
}
