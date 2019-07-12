package com.thoughtworks.homework.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "keyvault")
public class KeyVaultConfiguration {

    private boolean enabled = false;

    private String name;

    private String clientId;

    private String clientSecret;

    private String mysqlHostKey;

    private String mysqlUserKey;

    private String mysqlPasswordKey;

    private String redisKey;

    private String serviceBusConnectionString;

    private String blobConnectionStringKey;
}
