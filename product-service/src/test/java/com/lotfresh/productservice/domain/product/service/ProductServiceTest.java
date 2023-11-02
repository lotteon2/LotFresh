package com.lotfresh.productservice.domain.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.exception.ProductNotFound;
import com.lotfresh.productservice.domain.product.repository.ProductRepository;
import com.lotfresh.productservice.domain.product.service.response.ProductPageResponse;
import com.lotfresh.productservice.domain.product.service.response.ProductResponse;
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import com.lotfresh.productservice.domain.product.vo.SalesProductVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceTest {
  @Autowired EntityManager em;
  @Autowired ProductService productService;
  @Autowired CategoryRepository categoryRepository;
  @Autowired ProductRepository productRepository;
  @Autowired DiscountRepository discountRepository;
  @Autowired RedisTemplate<String, String> redisTemplate;
  @Autowired ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
    discountRepository.deleteAllInBatch();
    categoryRepository.deleteAllInBatch();
  }

  @DisplayName("상품 정보를 입력 받아 상품을 등록한다.")
  @Test
  void createProduct() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    categoryRepository.saveAll(List.of(category1, category2));
    Long categoryId = category2.getId();
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");

    // when
    Long productId = productService.createProduct(request);

    // then
    assertThat(productId).isNotNull();
  }

  @DisplayName("상품 등록 시 입력 받은 카테고리 id에 해당하는 카테고리가 존재 하지 않으면 예외가 발생한다.")
  @Test
  void createProductWithNoExistCategoryId() throws Exception {
    // given
    Long noExistCategoryId = 0L;
    ProductCreateRequest request =
        new ProductCreateRequest(
            noExistCategoryId, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    // when // then
    assertThatThrownBy(() -> productService.createProduct(request))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("상품 수정정보를 입력 받아 상품정보를 수정한다.")
  @Test
  void modifyProduct() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    ProductModifyRequest request =
        new ProductModifyRequest(
            category3.getId(), "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    // when
    productService.modifyProduct(request, product.getId());

    // then
    Product getProduct = productRepository.findById(product.getId()).get();
    assertThat(getProduct)
        .extracting("name", "thumbnail", "detail", "price", "productCode")
        .containsExactlyInAnyOrder("당근", "modified.jpeg", "modifiedDetail", 1000, "P002");
  }

  @DisplayName("존재하지 않는 상품 수정 시 예외가 발생한다.")
  @Test
  void modifyProductWithNoExistProductId() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    ProductModifyRequest request =
        new ProductModifyRequest(
            category3.getId(), "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    Long noExistId = 0L;

    // when // then
    assertThatThrownBy(() -> productService.modifyProduct(request, noExistId))
        .isInstanceOf(ProductNotFound.class)
        .hasMessage("해당 상품이 존재하지 않습니다.");
  }

  @DisplayName("상품 수정 중 존재하지 않는 카테고리 id인 경우 예외가 발생한다.")
  @Test
  void modifyProductWithNoExistCategoryId() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    Long noExistCategoryId = 0L;

    ProductModifyRequest request =
        new ProductModifyRequest(
            noExistCategoryId, "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    // when // then
    assertThatThrownBy(() -> productService.modifyProduct(request, product.getId()))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("상품을 (논리) 삭제한다")
  @Test
  void softDelete() throws Exception {
    // given
    Category category = createCategory(null, "냉장");
    categoryRepository.save(category);
    Product product = createProduct(category, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    // when
    productService.softDelete(product.getId());

    // then
    em.clear();
    Product getProduct = productRepository.findById(product.getId()).get();
    assertThat(getProduct.getIsDeleted()).isTrue();
  }

  @DisplayName("상품을 (논리) 삭제 시 존재하지 않는 상품이면 예외가 발생한다.")
  @Test
  void softDeleteWithNoExistProductId() throws Exception {
    // given
    Long noExistProductId = 0L;
    // when // then
    assertThatThrownBy(() -> productService.softDelete(noExistProductId))
        .isInstanceOf(ProductNotFound.class)
        .hasMessage("해당 상품이 존재하지 않습니다.");
  }

  @DisplayName("상품 id와 상품 재고를 받아 상품상세 정보를 반환 한다. 할인중인 카테고리 상품이 아닌 경우 salesPrice는 null이다.")
  @Test
  void getProductDetail() throws Exception {
    // given
    Integer stock = 10;
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    categoryRepository.saveAll(List.of(category1, category2));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);
    // when
    ProductResponse productDetail = productService.getProductDetail(product.getId(), stock);

    // then
    assertThat(productDetail)
        .extracting(
            "id",
            "name",
            "thumbnail",
            "detail",
            "price",
            "salesPrice",
            "productCode",
            "categoryId",
            "categoryName",
            "parentId",
            "parentName",
            "stock")
        .containsExactlyInAnyOrder(
            product.getId(),
            "충주사과",
            "thumbnail.jpeg",
            "detail",
            5000,
            null,
            "P001",
            category2.getId(),
            "과일",
            category1.getId(),
            "냉장",
            stock);
  }

  @DisplayName("할인중인 상품 id와 상품 재고를 받아 상품상세 정보를 반환 한다.")
  @Test
  void getProductDetailOnSale() throws Exception {
    // given
    Integer stock = 10;
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    categoryRepository.saveAll(List.of(category1, category2));
    Discount discount =
        createDiscount(
            category2, 5d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test1");
    discountRepository.save(discount);

    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 8990, "P001");

    productRepository.save(product);

    Integer salesPrice =
        product.getPrice() - (int) (product.getPrice() * discount.getRate() * 0.01);

    // when
    ProductResponse productDetail = productService.getProductDetail(product.getId(), stock);

    // then
    assertThat(productDetail)
        .extracting(
            "id",
            "name",
            "thumbnail",
            "detail",
            "price",
            "salesPrice",
            "productCode",
            "categoryId",
            "categoryName",
            "parentId",
            "parentName",
            "stock")
        .containsExactlyInAnyOrder(
            product.getId(),
            "충주사과",
            "thumbnail.jpeg",
            "detail",
            8990,
            salesPrice,
            "P001",
            category2.getId(),
            "과일",
            category1.getId(),
            "냉장",
            stock);
  }

  @DisplayName("상품 상세 정보 조회 시 상품 id에 해당하는 상품이 존재 하지 않는 경우 예외가 발생한다.")
  @Test
  void getProductDetailWithNoExistId() throws Exception {
    // given
    Integer stock = 10;
    Long noExistId = 0L;
    // when // then
    assertThatThrownBy(() -> productService.getProductDetail(noExistId, stock))
        .isInstanceOf(ProductNotFound.class)
        .hasMessage("해당 상품이 존재하지 않습니다.");
  }

  @DisplayName("카테고리별로 페이징한 상품 리스트를 조회 한다. 한 페이지 당 보여주는 아이템 개수를 16개 이고 정렬조건이 없다면 기본은 최신순이다.")
  @Test
  void getProductsWithPagingByCategory() throws Exception {
    // given

    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");

    categoryRepository.saveAll(List.of(category1, category2, category3));

    Product product1 = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail1", 1000, "P001");
    Product product2 = createProduct(category2, "블루베리", "thumbnail.jpeg", "detail2", 2000, "P002");
    Product product3 = createProduct(category2, "곶감", "thumbnail.jpeg", "detail3", 3000, "P003");
    Product product4 = createProduct(category2, "단감", "thumbnail.jpeg", "detail4", 4000, "P004");
    Product product5 = createProduct(category2, "딸기", "thumbnail.jpeg", "detail5", 5000, "P005");
    Product product6 = createProduct(category2, "바나나", "thumbnail.jpeg", "detail6", 6000, "P006");
    Product product7 = createProduct(category2, "오렌지", "thumbnail.jpeg", "detail7", 7000, "P007");
    Product product8 = createProduct(category2, "한라봉", "thumbnail.jpeg", "detail8", 8000, "P008");
    Product product9 = createProduct(category2, "멜론", "thumbnail.jpeg", "detail9", 9000, "P009");
    Product product10 =
        createProduct(category2, "수박", "thumbnail.jpeg", "detail10", 10000, "P0010");
    Product product11 =
        createProduct(category2, "참외", "thumbnail.jpeg", "detail11", 11000, "P0011");
    Product product12 =
        createProduct(category2, "포도", "thumbnail.jpeg", "detail12", 12000, "P0012");
    Product product13 =
        createProduct(category2, "샤인머스켓", "thumbnail.jpeg", "detail13", 13000, "P0013");
    Product product14 =
        createProduct(category2, "복숭아", "thumbnail.jpeg", "detail14", 14000, "P0014");
    Product product15 =
        createProduct(category2, "거봉", "thumbnail.jpeg", "detail15", 15000, "P0015");
    Product product16 =
        createProduct(category2, "망고", "thumbnail.jpeg", "detail16", 16000, "P0016");
    Product product17 =
        createProduct(category2, "파인애플", "thumbnail.jpeg", "detail17", 17000, "P0017");

    Product product18 =
        createProduct(category3, "방울토마토", "thumbnail.jpeg", "detail17", 18000, "P0018");

    productRepository.saveAll(
        List.of(
            product1, product2, product3, product4, product5, product6, product7, product8,
            product9, product10, product11, product12, product13, product14, product15, product16,
            product17, product18));

    Discount discount1 =
        createDiscount(
            category2, 5d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test1");
    discountRepository.saveAll(List.of(discount1));

    String order = "";
    String keyword = null;
    Integer page = 1;
    PageRequest pageRequest = new PageRequest(order, keyword, page, null);
    //     when
    ProductPageResponse productPageResponse =
        productService.getProductsByCategory(category1.getId(), pageRequest);
    // then
    assertThat(productPageResponse.getProducts())
        .extracting("name")
        .containsExactlyInAnyOrder(
            "방울토마토", "파인애플", "망고", "거봉", "복숭아", "샤인머스켓", "포도", "참외", "수박", "멜론", "한라봉", "오렌지",
            "바나나", "딸기", "단감", "곶감");
    assertThat(productPageResponse.getTotalPage()).isEqualTo(2);
  }

  @DisplayName("상품이름을 입력 받아 상품이름을 포함하는 상품 리스트를 조회한다.")
  @Test
  void getSearchedProducts() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");

    categoryRepository.saveAll(List.of(category1, category2, category3));

    Product product1 = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail1", 1000, "P001");
    Product product2 = createProduct(category2, "블루베리", "thumbnail.jpeg", "detail2", 2000, "P002");
    Product product3 = createProduct(category2, "곶감", "thumbnail.jpeg", "detail3", 3000, "P003");
    Product product4 = createProduct(category2, "단감", "thumbnail.jpeg", "detail4", 4000, "P004");
    Product product5 = createProduct(category2, "딸기", "thumbnail.jpeg", "detail5", 5000, "P005");
    Product product6 = createProduct(category2, "바나나", "thumbnail.jpeg", "detail6", 6000, "P006");

    productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));
    String order = "";
    String keyword = "바나나";
    Integer page = 1;
    PageRequest pageRequest = new PageRequest(order, keyword, page, null);

    //     when
    ProductPageResponse productPageResponse =
        productService.getProductsByCategory(category2.getId(), pageRequest);
    // then
    assertThat(productPageResponse.getProducts())
        .extracting("name")
        .containsExactlyInAnyOrder("바나나");
    assertThat(productPageResponse.getTotalPage()).isEqualTo(1);
  }

  @DisplayName("판매된 상품 순을 기준으로 상위 100개의 상품을 조회한다.")
  @Test
  void getBestProducts() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));

    Product product1 = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail1", 1000, "P001");
    Product product2 = createProduct(category2, "블루베리", "thumbnail.jpeg", "detail2", 2000, "P002");
    Product product3 = createProduct(category2, "곶감", "thumbnail.jpeg", "detail3", 3000, "P003");
    Product product4 = createProduct(category2, "단감", "thumbnail.jpeg", "detail4", 4000, "P004");
    Product product5 = createProduct(category2, "딸기", "thumbnail.jpeg", "detail5", 5000, "P005");
    Product product6 = createProduct(category2, "바나나", "thumbnail.jpeg", "detail6", 6000, "P006");
    productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));

    BestProductVO bestProductVO1 = new BestProductVO(product1.getId(), 10L);
    BestProductVO bestProductVO2 = new BestProductVO(product2.getId(), 20L);
    BestProductVO bestProductVO3 = new BestProductVO(product3.getId(), 30L);
    BestProductVO bestProductVO4 = new BestProductVO(product4.getId(), 5L);
    BestProductVO bestProductVO5 = new BestProductVO(product5.getId(), 40L);
    BestProductVO bestProductVO6 = new BestProductVO(product6.getId(), 50L);

    // 상품 순서  -> 바나나, 딸기, 곶감, 블루베리, 충주사과, 단감 순서
    String stringFormat = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue();
    String stringList =
        objectMapper.writeValueAsString(
            List.of(
                bestProductVO1,
                bestProductVO2,
                bestProductVO3,
                bestProductVO4,
                bestProductVO5,
                bestProductVO6));
    redisTemplate.opsForValue().set(stringFormat, stringList);
    // when
    List<ProductResponse> bestProducts = productService.getBestProducts();
    // then
    assertThat(bestProducts)
        .extracting("name")
        .containsExactlyInAnyOrder("바나나", "딸기", "곶감", "블루베리", "충주사과", "단감");
  }

  @DisplayName("마감임박 할인 중인 상품 리스트를 조회한다. 정렬은 재고 오름차순 정렬이다.")
  @Test
  void getSalesProducts() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));

    Product product1 = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail1", 1000, "P001");
    Product product2 = createProduct(category2, "블루베리", "thumbnail.jpeg", "detail2", 2000, "P002");
    Product product3 = createProduct(category2, "곶감", "thumbnail.jpeg", "detail3", 3000, "P003");
    Product product4 = createProduct(category2, "단감", "thumbnail.jpeg", "detail4", 4000, "P004");
    Product product5 = createProduct(category2, "딸기", "thumbnail.jpeg", "detail5", 5000, "P005");
    Product product6 = createProduct(category2, "바나나", "thumbnail.jpeg", "detail6", 6000, "P006");
    productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));

    SalesProductVO salesProductVO1 = new SalesProductVO(product1.getId(), 10);
    SalesProductVO salesProductVO2 = new SalesProductVO(product2.getId(), 20);
    SalesProductVO salesProductVO3 = new SalesProductVO(product3.getId(), 30);
    SalesProductVO salesProductVO4 = new SalesProductVO(product4.getId(), 5);
    SalesProductVO salesProductVO5 = new SalesProductVO(product5.getId(), 40);
    SalesProductVO salesProductVO6 = new SalesProductVO(product6.getId(), 50);

    String memberAddressKey = "Seoul";
    String stringList =
        objectMapper.writeValueAsString(
            List.of(
                salesProductVO1,
                salesProductVO2,
                salesProductVO3,
                salesProductVO4,
                salesProductVO5,
                salesProductVO6));
    redisTemplate.opsForValue().set(memberAddressKey, stringList);

    // 상품 순서  -> 단감, 충주사과, 블루베리, 곶감, 딸기, 바나나
    // when
    List<ProductResponse> salesProducts = productService.getSalesProducts(memberAddressKey);

    // then
    assertThat(salesProducts)
        .extracting("name")
        .containsExactlyInAnyOrder("단감", "충주사과", "블루베리", "곶감", "딸기", "바나나");
  }

  @DisplayName("배달이 불가능한 지역의 경우 마감할인 상품리스트는 빈 리스트로 반환한다.")
  @Test
  void getSalesProductsByInvalidKey() throws Exception {
    // given
    String invalidMemberAddressKey = "제주";
    // when
    List<ProductResponse> salesProducts = productService.getSalesProducts(invalidMemberAddressKey);
    // then
    assertThat(salesProducts).isEmpty();
  }

  @DisplayName("신상품 최대 100개 까지의 상품 정보 리스트를 조회 한다.")
  @Test
  void getNewProductsLimit100() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));

    Product product1 = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail1", 1000, "P001");
    Product product2 = createProduct(category2, "블루베리", "thumbnail.jpeg", "detail2", 2000, "P002");
    Product product3 = createProduct(category2, "곶감", "thumbnail.jpeg", "detail3", 3000, "P003");
    Product product4 = createProduct(category2, "단감", "thumbnail.jpeg", "detail4", 4000, "P004");
    Product product5 = createProduct(category2, "딸기", "thumbnail.jpeg", "detail5", 5000, "P005");
    Product product6 = createProduct(category2, "바나나", "thumbnail.jpeg", "detail6", 6000, "P006");
    productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));
    // when
    List<ProductResponse> newProducts = productService.getNewProducts();

    // then
    assertThat(newProducts)
            .extracting("name")
            .containsExactlyInAnyOrder("바나나", "딸기", "단감", "곶감", "블루베리", "충주사과");
  }

  private Product createProduct(
      Category category,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      String productCode) {
    return Product.builder()
        .category(category)
        .name(name)
        .thumbnail(thumbnail)
        .detail(detail)
        .price(price)
        .productCode(productCode)
        .build();
  }

  private Category createCategory(Category parent, String name) {
    return Category.builder().parent(parent).name(name).build();
  }

  private Discount createDiscount(
      Category category, Double rate, LocalDate startDate, LocalDate endDate, String imgurl) {
    return Discount.builder()
        .category(category)
        .rate(rate)
        .startDate(startDate)
        .endDate(endDate)
        .imgurl(imgurl)
        .build();
  }
}
