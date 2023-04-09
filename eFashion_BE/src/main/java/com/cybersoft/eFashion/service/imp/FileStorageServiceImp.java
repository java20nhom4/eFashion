package com.cybersoft.eFashion.service.imp;

import com.cybersoft.eFashion.service.FolderType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    boolean saveFiles(MultipartFile file, String newFileName, FolderType folderType);
    Resource load(String fileName, FolderType folderType);
    boolean removeFile(String fileName, FolderType folderType);
}
