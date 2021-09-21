package ru.itis.site.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.site.models.FileInfo;

public interface FileInfosRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByStorageFileName(String fileName);
}
