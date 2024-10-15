package com.proyecto.convenios.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.proyecto.convenios.dto.AwsSecrets;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Generated
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class SecretConfig {

    @Value("${aws.region}")
    private String awsRegion;

    private final Gson gson = new Gson();

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder().region(Region.of(awsRegion)).build();
    }

    public AwsSecrets getSecret(SecretsManagerClient client, String secretName) {
        try {
            GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(secretName).build();

            GetSecretValueResponse response = client.getSecretValue(request);
            String secret = response.secretString();

            if (secret == null || secret.isEmpty()) {
                throw new IllegalStateException("Retrieved secret is null or empty");
            }

            return gson.fromJson(secret, AwsSecrets.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve secret", e);
        }
    }

}
