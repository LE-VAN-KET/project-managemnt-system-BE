package com.dut.team92.userservice.repository;

import com.dut.team92.userservice.domain.entity.TokenPair;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class RedisTokenRepository {
    @Autowired
    private RedissonClient redissonClient;

    @Value("${spring.redis.token-prefix}")
    private String tokenPrefix;

    @Value("${spring.redis.token-time-to-live}")
    private long tokenTimeToLive;

    public void save(String name, TokenPair tokenPair) {
        saveOrUpdate(name, tokenPair);
    }

    public void update(String name, TokenPair tokenPair) {
        saveOrUpdate(name, tokenPair);
    }

    public TokenPair read(String name) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));
        return bucket.get();
    }

    public void delete(String name) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));

        if (Objects.isNull(bucket)) {
            log.warn("{} bucket not found", bucketName(name));
            return;
        }

        bucket.delete();
    }

    private void saveOrUpdate(String name, TokenPair tokenPair) {
        RBucket<TokenPair> bucket = redissonClient.getBucket(bucketName(name));
        bucket.set(tokenPair, tokenTimeToLive, TimeUnit.SECONDS);
    }

    private String bucketName(String name) {
        return String.join(":", tokenPrefix, name);
    }
}
