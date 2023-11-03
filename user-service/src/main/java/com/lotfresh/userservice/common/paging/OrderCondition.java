package com.lotfresh.userservice.common.paging;

public enum OrderCondition {
  RECENT("id");

  private final String sort;

  OrderCondition(String sort) {
    this.sort = sort;
  }

  public String getSort() {
    return sort;
  }
}