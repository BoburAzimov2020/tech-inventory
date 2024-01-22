package uz.dynamic.techinventory.web.rest.utils;


import uz.dynamic.techinventory.service.dto.ThumbnailDTO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ThumbnailService {
    private final static int THUMBNAIL_WIDTH = 200;
    private final static int THUMBNAIL_HEIGHT = 200;

    public static ThumbnailDTO generateThumbnail(byte[] imageData) {
        BufferedImage originalImage = convertToBufferedImage(imageData);
        BufferedImage resizedImage = resizeImage(originalImage);
        return convertToThumbnailDTO(resizedImage);
    }

    private static BufferedImage convertToBufferedImage(byte[] imageData) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            return ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert byte[] to BufferedImage.", e);
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage) {
        Image scaledImage = originalImage.getScaledInstance(ThumbnailService.THUMBNAIL_WIDTH, ThumbnailService.THUMBNAIL_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(ThumbnailService.THUMBNAIL_WIDTH, ThumbnailService.THUMBNAIL_HEIGHT, originalImage.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(scaledImage, 0, 0, null);
        graphics2D.dispose();
        return resizedImage;
    }

    private static ThumbnailDTO convertToThumbnailDTO(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String imageFormat = "image/jpeg"; // or determine image format dynamically
            ImageIO.write(image, imageFormat, baos);
            ThumbnailDTO thumbnailDTO = new ThumbnailDTO();
            thumbnailDTO.setObjectName(UUID.randomUUID() + ".jpg");
            thumbnailDTO.setInputStream(new ByteArrayInputStream(baos.toByteArray()));
            thumbnailDTO.setContentType(imageFormat);
            return thumbnailDTO;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert BufferedImage to ThumbnailDTO.", e);
        }
    }
}

