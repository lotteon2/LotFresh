package shop.lotfresh.storageservice.domain.storageproduct.service;

import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.redis.SalesStorageProductRedis;
import shop.lotfresh.storageservice.domain.redis.SalesStorageProductRedisRepository;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StorageProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageProductService {
    private final StorageProductRepository storageProductRepository;
    private final SalesStorageProductRedisRepository salesStorageProductRedisRepository;

    @Transactional
    public void addStock(Long storageProductId, Integer quantityToAdd) {
        StorageProduct product = storageProductRepository.findById(storageProductId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + storageProductId));

        product.setStock(product.getStock() + quantityToAdd);
    }


    public StorageProductService(StorageProductRepository storageProductRepository, SalesStorageProductRedisRepository salesStorageProductRedisRepository) {
        this.storageProductRepository = storageProductRepository;
        this.salesStorageProductRedisRepository = salesStorageProductRedisRepository;
    }

    @Transactional
    public List<StorageProduct> findProductsByStorageId(String province) {
        return storageProductRepository.findProductsByStorageId(province);
    }

    @PostConstruct
    public void initialize() {
        findNearExpiryProductsByStorageId();
    }

   /* @Transactional
    public void findNearExpiryProductsByStorageId() {

        for(long i=1; i<=14; i++ ) {
            try {
                List<StorageProduct> storageProducts = storageProductRepository.findNearExpiryProductsByStorageId(i);
                for (StorageProduct storageProduct : storageProducts) {
                    SalesStorageProductRedis redisData = convertToRedisData(storageProduct);
                    salesStorageProductRedisRepository.save(i, redisData);
                }
            } finally {

            }
        }
    }*/
   @Transactional
   public void findNearExpiryProductsByStorageId() {
       for (long i = 1; i <= 14; i++) {
           try {
               List<StorageProduct> storageProducts = storageProductRepository.findNearExpiryProductsByStorageId(i);

               List<SalesStorageProductRedis> redisDataList = new ArrayList<>();
               for (StorageProduct storageProduct : storageProducts) {
                   Long productId = storageProduct.getProductId();
                   int stock = storageProduct.getStock();

                   SalesStorageProductRedis redisData = new SalesStorageProductRedis();
                   redisData.setProductId(productId);
                   redisData.setStock(stock);

                   redisDataList.add(redisData);
               }

               // Redis에 리스트 객체로 저장
               salesStorageProductRedisRepository.saveList(i, redisDataList);
           } catch (Exception e) {
               // 예외 처리 로직 추가
           }
       }
   }


    private SalesStorageProductRedis convertToRedisData(StorageProduct storageProduct) {
        SalesStorageProductRedis redisData = new SalesStorageProductRedis();
        redisData.setProductId(storageProduct.getProductId());
        redisData.setStock(storageProduct.getStock());
        return redisData;
    }

    public Integer getProductStock(String province, Long productId) {
        return storageProductRepository.getProductStock(province, productId);
    }
    @Transactional
    public List<StorageProduct> getProductOrderList(String province, Long productId) {
        return storageProductRepository.getProductOrderList(province, productId);
    }

    //TODO 음수, 0, 이상한값 들어오는거 처리해야함/ 예외처리랑 같이 작업하기
    //TODO 상품주문에 넘겨주는 로직 짜기 << 단순히 리턴값으로 재고 차단한 객체 ID 전달보다 좋은 방법 있는지?
  /*  @Transactional
    public List<StorageProduct> productOrder(Long storageId, Long productId, Integer stock) {
        List<StorageProduct> products = storageProductRepository.getProductOrderList(storageId, productId);

        long totalStock = storageProductRepository.getProductStock(storageId, productId);

        if (totalStock < stock) {
            throw new IllegalArgumentException("주문을 위한 재고가 부족합니다..");
        }

        for (StorageProduct product : products) {
            if (product.getStock() >= stock) {
                product.setStock(product.getStock() - stock);
                break;
            } else {
                stock -= product.getStock();
                product.setStock(0L);
            }
        }

        return products;
    }*/

    @Transactional
    public List<StorageProductOrder> productOrder(String province, Long productId, Integer stock) {
        List<StorageProduct> products = storageProductRepository.getProductOrderList(province, productId);

        long totalStock = storageProductRepository.getProductStock(province, productId);

        if (totalStock < stock) {
            throw new IllegalArgumentException("주문을 위한 재고가 부족합니다..");
        }

        List<StorageProductOrder> subtractedProducts = new ArrayList<>();

        for (StorageProduct product : products) {
            if (product.getStock() >= stock) {
                subtractedProducts.add(new StorageProductOrder(product.getId(), stock));
                product.setStock(product.getStock() - stock);
                break;
            } else {
                subtractedProducts.add(new StorageProductOrder(product.getId(), product.getStock()));
                stock -= product.getStock();
                product.setStock(0);
            }
        }

        return subtractedProducts;
    }

}
