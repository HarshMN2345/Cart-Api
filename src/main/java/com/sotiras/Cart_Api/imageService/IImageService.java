package com.sotiras.Cart_Api.imageService;

import com.sotiras.Cart_Api.dto.ImageDto;
import com.sotiras.Cart_Api.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,Long productId);
}
