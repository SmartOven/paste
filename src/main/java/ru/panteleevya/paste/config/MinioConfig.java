package ru.panteleevya.paste.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    public AmazonS3 s3Client(
            @Value("${s3.access-key-id}") String accessKeyId,
            @Value("${s3.secret-access-key}") String secretAccessKey,
            @Value("${s3.service-host}") String serviceHost,
            @Value("${s3.service-port}") String servicePort,
            @Value("${signing-region}") String signingRegion
    ) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        String serviceEndpoint = serviceHost + ":" + servicePort;
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(serviceEndpoint, signingRegion);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
