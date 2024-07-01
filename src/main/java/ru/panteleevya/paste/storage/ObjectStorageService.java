package ru.panteleevya.paste.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class ObjectStorageService {
    private static final Logger log = LogManager.getLogger(ObjectStorageService.class);

    private final String bucketName;
    private final AmazonS3 s3Client;
    private final int retryCount;

    public ObjectStorageService(
            @Value("${s3.bucket-name}") String bucketName,
            @Autowired AmazonS3 s3Client,
            @Value("${s3.retry-count}") int retryCount
    ) {
        this.bucketName = bucketName;
        this.s3Client = s3Client;
        this.retryCount = retryCount;
    }

    /**
     * Fetching and decompressing object from ObjectStorage
     */
    public Object fetchObject(String objectKey) {
        log.info("Fetching object with objectKey={} from bucket={}", objectKey, bucketName);
        for (int retryIndex = 1; retryIndex <= retryCount; retryIndex++) {
            try (S3Object s3Object = s3Client.getObject(bucketName, objectKey);
                 GZIPInputStream gzipInputStream = new GZIPInputStream(s3Object.getObjectContent());
                 ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream)) {
                log.info("Successfully fetched object with objectKey={} from bucket={}", objectKey, bucketName);
                return objectInputStream.readObject();
            } catch (IOException e) {
                log.warn("Something went wrong while fetching object from ObjectStorage, retry time is {}", retryIndex);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            } catch (Exception e) {
                log.error("Something went wrong while fetching object with objectKey={} from bucket={}. Error={}", objectKey, bucketName, e);
            }
        }
        throw new RuntimeException("Fetching object from ObjectStorage failed after " + retryCount + " retries");
    }

    /**
     * Compressing and saving object in ObjectStorage
     */
    public <T extends Serializable> void compressAndSaveObject(String objectKey, T object) {
        log.info("Compressing and saving object with objectKey={} to bucket={}", objectKey, bucketName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            log.error("Writing object with key={} to byte array failed. Error={}", objectKey, e);
            return;
        }

        byte[] compressedObject = byteArrayOutputStream.toByteArray();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedObject);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(compressedObject.length);
            s3Client.putObject(bucketName, objectKey, byteArrayInputStream, objectMetadata);
        } catch (Exception e) {
            log.error("Saving object with key={} to bucket={} failed. Error={}", objectKey, bucketName, e);
            return;
        }
        log.info("Successfully compressed and saved object with objectKey={} to bucket={}", objectKey, bucketName);
    }

    /**
     * Deletes object from ObjectStorage
     */
    public void deleteObject(String objectKey) {
        log.info("Deleting object with objectKey={} from bucket={}", objectKey, bucketName);
        try {
            s3Client.deleteObject(bucketName, objectKey);
        } catch (Exception e) {
            log.error("Failed to delete object with objectKey={} from bucket={}", objectKey, bucketName);
            return;
        }
        log.info("Successfully deleted object with objectKey={} from bucket={}", objectKey, bucketName);
    }
}
