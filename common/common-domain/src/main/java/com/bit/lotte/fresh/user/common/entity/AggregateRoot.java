package com.bit.lotte.fresh.user.common.entity;


import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {

   protected AggregateRoot() {

    }

}
