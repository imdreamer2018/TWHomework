package com.thoughtworks.homework.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getMysqlHostKey() {
        return mysqlHostKey;
    }

    public void setMysqlHostKey(String mysqlHostKey) {
        this.mysqlHostKey = mysqlHostKey;
    }

    public String getMysqlUserKey() {
        return mysqlUserKey;
    }

    public void setMysqlUserKey(String mysqlUserKey) {
        this.mysqlUserKey = mysqlUserKey;
    }

    public String getMysqlPasswordKey() {
        return mysqlPasswordKey;
    }

    public void setMysqlPasswordKey(String mysqlPasswordKey) {
        this.mysqlPasswordKey = mysqlPasswordKey;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getServiceBusConnectionString() {
        return serviceBusConnectionString;
    }

    public void setServiceBusConnectionString(String serviceBusConnectionString) {
        this.serviceBusConnectionString = serviceBusConnectionString;
    }

    public String getBlobConnectionStringKey() {
        return blobConnectionStringKey;
    }

    public void setBlobConnectionStringKey(String blobConnectionStringKey) {
        this.blobConnectionStringKey = blobConnectionStringKey;
    }
}
