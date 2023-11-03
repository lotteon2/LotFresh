package shop.lotfresh.storageservice.domain.storageproduct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.redis.SalesStorageProductRedis;
import shop.lotfresh.storageservice.domain.redis.SalesStorageProductRedisRepository;
import shop.lotfresh.storageservice.domain.storageproduct.api.request.StorageProductSearchRequest;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StorageProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageStockVo;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageProductService {
    private static final Logger log = LoggerFactory.getLogger(StorageProductService.class);
    private final StorageProductRepository storageProductRepository;
    private final SalesStorageProductRedisRepository salesStorageProductRedisRepository;

    @Transactional
    public void addStock(Long storageProductId, Integer quantityToAdd) {
        StorageProduct product = storageProductRepository.findById(storageProductId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + storageProductId));

        product.setStock(product.getStock() + quantityToAdd);
        findNearExpiryProductsByStorageId();
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

   @Transactional
   public void findNearExpiryProductsByStorageId() {
       for (long i = 1; i <= 10; i++) {
           try {
               List<StorageStockVo> storageProducts = storageProductRepository.findSalesProductsByStorageId(i);

               List<SalesStorageProductRedis> redisDataList = new ArrayList<>();
               for (StorageStockVo storageProduct : storageProducts) {
                   Long productId = storageProduct.getProductId();
                   int stock = storageProduct.getStock();

                   SalesStorageProductRedis redisData = new SalesStorageProductRedis();
                   redisData.setProductId(productId);
                   redisData.setStock(stock);

                   redisDataList.add(redisData);
                   log.info("Processing storage ID: {}", i);
               }
               salesStorageProductRedisRepository.saveList(storageProducts.get(0).getProvince(), redisDataList);
           } catch (Exception e) {
               log.error("An error occurred while processing storage ID: {}", i, e);
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

    public Integer getSalesProductStock(String province, Long productId) {
        return storageProductRepository.getSalesProductStock(province, productId);
    }
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
        findNearExpiryProductsByStorageId();
        return subtractedProducts;
    }

    @Transactional
    public List<StorageProductOrder> productsalesOrder(String province, Long productId, Integer stock) {
        List<StorageProduct> products = storageProductRepository.getSalesProductOrderList(province, productId);

        long totalStock = storageProductRepository.getSalesProductStock(province, productId);

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
        findNearExpiryProductsByStorageId();
        return subtractedProducts;
    }

    @Transactional
    public void deleteProduct(){
        storageProductRepository.deleteProduct();
    }
}
