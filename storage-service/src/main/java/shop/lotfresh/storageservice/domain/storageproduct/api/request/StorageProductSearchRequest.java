package shop.lotfresh.storageservice.domain.storageproduct.api.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class StorageProductSearchRequest {

    private Long product_id;

    private Long storage_id;

    private Long stock;

    private Date expiration_date_start;

    private Date expiration_date_end;

}
