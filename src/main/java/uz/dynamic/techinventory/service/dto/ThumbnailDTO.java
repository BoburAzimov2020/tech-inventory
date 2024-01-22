package uz.dynamic.techinventory.service.dto;

import lombok.Data;

import java.io.InputStream;

@Data
public class ThumbnailDTO {

    private String oldThumbnailObject;
    private String objectName;
    private InputStream inputStream;
    private String contentType;
}
