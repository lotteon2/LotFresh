package com.lotfresh.orderservice.aggregate.productOrder.domain;

public enum ProductOrderStatus {
    CREATED, CANCELED, CONFIRMED;

    public boolean isNotModifiable(){
        return CREATED != this;
    }

}
