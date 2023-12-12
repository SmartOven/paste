package ru.panteleevya.paste.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Service
public class ObjectStorageService {
    private static final Logger log = LogManager.getLogger(ObjectStorageService.class);

    private final String bucketName;
    private final AmazonS3 objectStorageClient;

    public ObjectStorageService(
            @Value("${yandex.cloud.object-storage.access-key-id}") String accessKeyId,
            @Value("${yandex.cloud.object-storage.secret-access-key}") String secretAccessKey,
            @Value("${yandex.cloud.object-storage.service-endpoint}") String serviceEndpoint,
            @Value("${yandex.cloud.object-storage.signing-region}") String signingRegion,
            @Value("${yandex.cloud.object-storage.bucket-name}") String bucketName
    ) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(serviceEndpoint, signingRegion);
        this.bucketName = bucketName;
        this.objectStorageClient = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    /**
     * @return list of object keys in bucket
     */
    private List<String> listObjects() {
        return objectStorageClient.listObjects(bucketName)
                .getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .toList();
    }

    /**
     * Compressing and saving object in ObjectStorage
     *
     * @return was object saved or not
     */
    private <T> boolean compressAndSaveObject(String objectKey, T object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            log.error(String.format("Writing object with keu=%s to byte array failed", objectKey), e);
            return false;
        }

        byte[] compressedObject = byteArrayOutputStream.toByteArray();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedObject);
            objectStorageClient.putObject(bucketName, objectKey, byteArrayInputStream, new ObjectMetadata());
        } catch (SdkClientException e) {
            log.error(String.format("Saving object with key=%s to object storage failed", objectKey), e);
            return false;
        }

        return true;
    }
}
