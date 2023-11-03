package shop.lotfresh.storageservice.domain.storageproduct.scheduler;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class StorageProductScheduler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StorageProductService.class);
    private final StorageProductService storageProductService;

    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    public void clearCache() {
        try {
            storageProductService.deleteProduct();
            storageProductService.findNearExpiryProductsByStorageId();
        } catch(Exception e){
            log.info("스케줄러 오류");
            log.info(String.valueOf(e));
        }
    }
}