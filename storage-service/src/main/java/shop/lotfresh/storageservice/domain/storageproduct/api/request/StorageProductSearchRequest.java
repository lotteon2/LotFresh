package shop.lotfresh.storageservice.domain.storageproduct.api.request;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class StorageProductSearchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long product_id;

    private Long storage_id;

    private Long stock;

    private Date expiration_date_start;

    private Date expiration_date_end;
}
