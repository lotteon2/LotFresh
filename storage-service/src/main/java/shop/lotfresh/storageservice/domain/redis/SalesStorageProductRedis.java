package shop.lotfresh.storageservice.domain.redis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesStorageProductRedis {
    private Long productId;
    private Integer stock;
}
