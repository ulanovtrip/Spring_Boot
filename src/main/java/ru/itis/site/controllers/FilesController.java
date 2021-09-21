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

@Controller
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PermitAll // разрешено всем
    @GetMapping("/files/upload")
    public String getFileUploadPage() {
        return "file_upload";
    }

    // запрос файла по конкретному названию файла, ".+" - допускает использование точки в конце
    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        // по названию fileName в такой-то респонс response запишем файл, считанный с диска
        filesService.writeFileToResponse(fileName, response);
    }

    @PermitAll
    @PostMapping("/files")
    @ResponseBody
    // @RequestParam("file") MultipartFile file - это позволит принять на вход Content-Type: multipart/form-data;
    public FileUrlDto saveFile(@RequestParam("file") MultipartFile file)  {
        // это сохраняет на диск сервера
        return filesService.save(file);
    }
}
