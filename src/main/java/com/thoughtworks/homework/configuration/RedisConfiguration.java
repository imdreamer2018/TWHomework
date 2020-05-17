package com.thoughtworks.homework.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnProperty(value = "keyvault.enabled", havingValue = "false")
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(),redisProperties.getPort());
        if (redisProperties.getPassword() != null) {
            redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        }
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

//    @Bean
//    @ConditionalOnProperty(value = "keyvault.enabled", havingValue = "true")
//    public RedisConnectionFactory redisConnectionFactoryForKeyVault(KeyVaultConfiguration configuration, KeyVaultClient keyVaultClient) {
//        String[] result = keyVaultClient.getSecret(configuration.getRedisKey()).split(",");
//        String[] host = result[0].split(":");
//        String password = result[1].substring("password=".length());
//        log.info("redis connection info from key vault, {}", host[0]);
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host[0]);
//        redisStandaloneConfiguration.setPort(Integer.valueOf(host[1]));
//        redisStandaloneConfiguration.setPassword(password);
//
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
//        jedisClientConfiguration.useSsl();
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration.build());
//    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

}
