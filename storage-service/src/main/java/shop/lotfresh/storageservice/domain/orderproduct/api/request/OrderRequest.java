package shop.lotfresh.storageservice.domain.orderproduct.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long storage_product_id;

    private long order_detail_id;

    private long quantity;

    private int is_deleted;

}
