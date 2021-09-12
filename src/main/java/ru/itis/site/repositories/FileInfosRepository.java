package ru.itis.site.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.site.models.FileInfo;

/**
 * 31.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface FileInfosRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByStorageFileName(String fileName);
}
