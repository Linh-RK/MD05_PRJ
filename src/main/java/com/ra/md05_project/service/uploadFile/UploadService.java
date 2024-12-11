package com.ra.md05_project.service.uploadFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class UploadService
{
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException
    {// Lấy tên file gốc từ MultipartFile (xóa đuôi file nếu cần xử lý thêm)
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains("."))
        {
            originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf(".")); // Bỏ phần đuôi file
        }

        // Thêm tên file vào tham số upload (public_id)
        Map uploadParams = ObjectUtils.asMap(
                "public_id", originalFilename  // Đặt tên file hiển thị đúng tên gốc
        );

        // Upload file lên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
        return uploadResult.get("url").toString(); // Return the URL of the uploaded image
    }
}