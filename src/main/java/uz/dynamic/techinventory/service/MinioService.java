//package uz.dynamic.techinventory.service;
//
//import io.minio.GetPresignedObjectUrlArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.RemoveObjectArgs;
//import io.minio.http.Method;
//import io.minio.messages.Bucket;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import uz.dynamic.techinventory.service.dto.ThumbnailDTO;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Service
//public class MinioService {
//
//    private final Logger log = LoggerFactory.getLogger(MinioService.class);
//    private final MinioClient minioClient;
//    @Value("${minio.bucket}")
//    private String bucket;
//
//    @SneakyThrows
//    public void upload(ThumbnailDTO thumbnailDTO) {
////        log.warn("UPLOAD METHOD ======== ThumbnailDTO: getObjectName {}, getOldThumbnailObject {} getInputStream {} getContentType {}",
////                thumbnailDTO.getObjectName(), thumbnailDTO.getOldThumbnailObject(), thumbnailDTO.getInputStream().available(),
////                thumbnailDTO.getContentType());
//        PutObjectArgs.Builder putObjectArgs = makePutObjectArgs(thumbnailDTO);
//        for (Bucket listBucket : minioClient.listBuckets()) {
//            System.out.println(listBucket.name() + "--------------------------------<<<<<<<<<<<<");
//        }
//
//        minioClient.putObject(putObjectArgs.build());
//    }
//
//    private PutObjectArgs.Builder makePutObjectArgs(ThumbnailDTO thumbnailDTO) throws IOException {
//
//        String objectName = thumbnailDTO.getObjectName();
//        InputStream inputStream = thumbnailDTO.getInputStream();
//        String contentType = thumbnailDTO.getContentType();
//
//        log.info("Request to make PutObjectArgs builder \n for Object-name: {} \n Content-type: {} \n InputStream-size: {}",
//                objectName, contentType, inputStream.available());
//        PutObjectArgs.Builder putObjectArgs = PutObjectArgs.builder()
//                .bucket(bucket)
//                .object(objectName)
//                .stream(inputStream, inputStream.available(), -1)
//                .contentType(contentType);
////        inputStream.close();
//        return putObjectArgs;
//    }
//
//    @SneakyThrows
//    public String getUrl(String objectName) {
//        log.info("Request to get thumbnail URL for 7 days. Object-name: {}", objectName);
//        GetPresignedObjectUrlArgs.Builder getPresignedObjectUrl = makeGetPresignedObjectUrlArgs(objectName);
//        return minioClient.getPresignedObjectUrl(getPresignedObjectUrl.build());
//    }
//
//    private GetPresignedObjectUrlArgs.Builder makeGetPresignedObjectUrlArgs(String objectName) {
//        return GetPresignedObjectUrlArgs.builder()
//                .method(Method.GET)
//                .bucket(bucket)
//                .object(objectName)
//                .expiry(7, TimeUnit.DAYS);
//    }
//
//    public String updateThumbnailUrl(ThumbnailDTO thumbnailDTO) {
//        if (thumbnailDTO.getOldThumbnailObject() != null) {
//            remove(thumbnailDTO.getOldThumbnailObject());
//        }
//        upload(thumbnailDTO);
//        return getUrl(thumbnailDTO.getObjectName());
//    }
//
//    @SneakyThrows
//    public void remove(String objectName) {
//        minioClient.removeObject(
//                RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
//    }
//}
