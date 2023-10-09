package com.lotfresh.productservice.common.paging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import static org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class PageRequest {
  private static final int DEFAULT_PAGE = 0;
  private static final int PAGE_SIZE = 16;

  private String keyword;
  private Pageable pageable;

  public PageRequest(String order, String keyword, Integer page) {
    this.keyword = keyword;
    this.pageable = toPageable(page, toOrderCondition(order));
  }

  private OrderCondition toOrderCondition(String order) {
    return (order == null || order.isBlank())
        ? OrderCondition.RECENT
        : OrderCondition.valueOf(order);
  }

  private Pageable toPageable(Integer pageNum, OrderCondition orderCondition) {
    Integer page = pageNum == null ? DEFAULT_PAGE : pageNum-1;
    return org.springframework.data.domain.PageRequest.of(
        page, PAGE_SIZE, Direction.DESC, orderCondition.getSort());
  }
}
