package com.lotfresh.orderservice.domain.productOrder;

public enum ProductOrderStatus {
    CREATED, CANCELED, CONFIRMED;

    public boolean isNotModifiable(){
        return CREATED != this;
    }

}
