package com.xu.redis.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @Author: xu.dm
 * @Date: 2018/10/14 22:36
 * @Description:
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Value("${spring.cache.time-to-live}")
    private long timeToLive=3600;
    
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;


    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    // 缓存管理器
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(this.timeToLive));


        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(config)
                .transactionAware();

        return builder.build();
    }


    /**
     * RedisTemplate配置
     * GenericJackson2JsonRedisSerializer 不能反序列化没有无参数构造函数的类，不能反序列化 接口的动态代理类，原因相同
     * Jackson2JsonRedisSerializer 不能直接序列化Map和list（貌似）然后GenericJackson2JsonRedisSerializer不能的它也不能
     * fashjson（GenericFastJsonRedisSerializer和FastJson2JsonRedisSerializer）不能反序列化动态代理类 ，不能反序列化没有无参数构造函数的类
     * kyro-serializers 可以序列化无缺省构造函数的类，序列化后占用空间小，但是不能反序列化动态代理类
     * JdkSerializationRedisSerializer 不能（反）序列化动态代理类，序列化后可读性差，占用空间大，序列化的对象必须实现Serializable
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());// value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new RedisObjectSerializer();
    }

}
