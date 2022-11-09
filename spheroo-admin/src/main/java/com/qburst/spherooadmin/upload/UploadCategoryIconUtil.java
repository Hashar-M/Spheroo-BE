package com.qburst.spherooadmin.upload;

import net.bytebuddy.utility.RandomString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadCategoryIconUtil {
    /**
     * Uploads the icon for category
     * @param fileName Name of the icon being uploaded
     * @param multipartFile the file being uploaded
     * @return Generated fileCode(A random 10 character alphanumeric string) for the image
     */
    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get("File-Uploads/categoryIcons");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = RandomString.make(10);    // A random string generated to encode along with filename
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
        return fileCode;
    }
}
