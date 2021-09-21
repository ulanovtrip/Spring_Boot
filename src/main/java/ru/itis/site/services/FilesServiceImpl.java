package ru.itis.site.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.site.dto.FileUrlDto;
import ru.itis.site.models.FileInfo;
import ru.itis.site.repositories.FileInfosRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FileInfosRepository filesRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public FileUrlDto save(MultipartFile file) {

        // создаем название файлу
        // FilenameUtils.getExtension(file.getOriginalFilename() - добавляет расширение к файлу
        String storageName =
                UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        // собираем модель для сохранения в БД
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(file.getOriginalFilename())
                .storageFileName(storageName)
                .size(file.getSize())
                .type(file.getContentType())
                .build();
        try {
            // позволяет взять файл и записать его в другое место
            Files.copy(file.getInputStream(), Paths.get(storagePath, storageName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        filesRepository.save(fileInfo);

        // это вернёт полный URL к файлу, ссылка сейчас видно в response в браузере
        return FileUrlDto
                .builder()
                .fileUrl(serverUrl + "/files/" + storageName)
                .build();
    }

    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {

        // сначала вытащим файл из репозитория
        FileInfo fileInfo = filesRepository.findByStorageFileName(fileName);

        // важно, чтобы браузер понял, какого типа данные мы ему шлем
        // для этого необходимо указать ContentType файла
        response.setContentType(fileInfo.getType());

        try {

            // входной поток файла, который хранится на сервере на диске
            InputStream inputStream = new FileInputStream(storagePath + "/" + fileName);

            // поток HTTP-ответа, куда мы запишем файл
            OutputStream outputStream = response.getOutputStream();

            // копируем из inputStream в outputStream информацию
            IOUtils.copy(inputStream, outputStream);
            // сбросим буфер, чтобы ответ записался
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
