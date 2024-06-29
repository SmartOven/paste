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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class ObjectStorageService {
    private static final Logger log = LogManager.getLogger(ObjectStorageService.class);

    private final String bucketName;
    private final AmazonS3 objectStorageClient;
    private final int retryCount;

    public ObjectStorageService(
            @Value("${s3.access-key-id}") String accessKeyId,
            @Value("${s3.secret-access-key}") String secretAccessKey,
            @Value("${s3.service-endpoint}") String serviceEndpoint,
            @Value("${s3.signing-region}") String signingRegion,
            @Value("${s3.bucket-name}") String bucketName,
            @Value("${s3.retry-count}") int retryCount
    ) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(serviceEndpoint, signingRegion);
        this.bucketName = bucketName;
        this.objectStorageClient = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
        this.retryCount = retryCount;
    }

    /**
     * Fetching and decompressing object from ObjectStorage
     */
    public Object fetchObject(String objectKey) {
        for (int retryIndex = 1; retryIndex <= retryCount; retryIndex++) {
            try (S3Object s3Object = objectStorageClient.getObject(bucketName, objectKey);
                 GZIPInputStream gzipInputStream = new GZIPInputStream(s3Object.getObjectContent());
                 ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream)) {
                return objectInputStream.readObject();
            } catch (SdkClientException e) {
                // not found
                return null;
            } catch (IOException e) {
                log.warn("Something went wrong while fetching object from ObjectStorage, retry time is {}", retryIndex);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        throw new RuntimeException("Fetching object from ObjectStorage failed after " + retryCount + " retries");
    }

    /**
     * Compressing and saving object in ObjectStorage
     */
    public <T extends Serializable> void compressAndSaveObject(String objectKey, T object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            log.error("Writing object with keu={} to byte array failed", objectKey);
            throw new RuntimeException(e);
        }

        byte[] compressedObject = byteArrayOutputStream.toByteArray();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedObject);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(compressedObject.length);
            objectStorageClient.putObject(bucketName, objectKey, byteArrayInputStream, objectMetadata);
        } catch (SdkClientException e) {
            log.error("Saving object with key={} to object storage failed", objectKey);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes object from ObjectStorage
     */
    public void deleteObject(String objectKey) {
        try {
            objectStorageClient.deleteObject(bucketName, objectKey);
        } catch (SdkClientException ignored) {
            // no content anyway
        }
    }
}
