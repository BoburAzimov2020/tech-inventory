//package uz.dynamic.techinventory.config;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.SetBucketTagsArgs;
//import io.minio.errors.*;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.ConstructorBinding;
//import org.springframework.context.annotation.Bean;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Map;
//
///**
// * Properties specific to MinIO.
// * <p>
// * Properties are configured in the {@code application-dev.yml} file.
// */
//@ConfigurationProperties(prefix = "minio")
//@ConstructorBinding
//@Data
//public class MinioConfig {
//
//    private final String endpoint;
//    private final int port;
//    private final String bucket;
//    private final String accessKey;
//    private final String secretKey;
//    private final boolean secure;
//
//    @Bean
//    public MinioClient getMinioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        MinioClient minioClient = MinioClient.builder()
//                .endpoint(endpoint)
//                .credentials(accessKey, secretKey)
//                .build();
//        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
////        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(makeBucketPolicyConfig()).build());
//        minioClient.setBucketTags(SetBucketTagsArgs.builder().bucket(bucket).tags(makeBucketTags()).build());
//        return minioClient;
//    }
//
//    /**
//     * Make bucket config which give access to get objects by any client front users.
//     * @return config of bucket policy.
//     */
//    private String makeBucketPolicyConfig() {
//        return "{\n" +
//                "     \"Version\": \"2012-10-17\",\n" +
//                "     \"Statement\": [\n" +
//                "         {\n" +
//                "             \"Action\": \"s3:GetObject\",\n" +
//                "             \"Effect\": \"Allow\",\n" +
//                "             \"Principal\": \"*\",\n" +
//                "             \"Resource\": \"arn:aws:s3:::*\"\n" +
//                "         }\n" +
//                "     ]\n" +
//                " }";
//    }
//
//    /**
//     * Make bucket tags for just information about Author and Project. It's Optional.
//     * @return config of bucket policy.
//     */
//    private Map<String, String> makeBucketTags() {
//        return Map.of("Project", "Store thumbnails of cameras", "Author", "Bobur Azimov");
//    }
//}
