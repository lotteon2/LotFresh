package shop.lotfresh.storageservice.domain.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesStorageProductRedisRepository {

    private final RedisTemplate<String, SalesStorageProductRedis> redisTemplate;

    @Autowired
    public SalesStorageProductRedisRepository(RedisTemplate<String, SalesStorageProductRedis> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Long storageId, SalesStorageProductRedis data) {
        redisTemplate.opsForValue().set(String.valueOf(storageId), data);
    }


    public void saveList(Long storageId, List<SalesStorageProductRedis> dataList) {
        String key = String.valueOf(storageId);
        redisTemplate.delete(key);
        redisTemplate.opsForList().rightPushAll(key, dataList);
    }
}
