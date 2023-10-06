package com.lotfresh.orderservice.domain.order.entity;

public enum OrderDetailStatus {
    CREATED, CANCELED, CONFIRMED;

    public boolean isNotModifiable(){
        return CREATED != this;
    }

}
