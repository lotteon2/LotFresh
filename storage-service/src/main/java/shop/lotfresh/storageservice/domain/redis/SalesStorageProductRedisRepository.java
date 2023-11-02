package shop.lotfresh.storageservice.domain.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesStorageProductRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SalesStorageProductRedisRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void saveList(String province, List<SalesStorageProductRedis> dataList){
        redisTemplate.delete(province);
        try {
            redisTemplate.opsForValue().set(province, objectMapper.writeValueAsString(dataList));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
