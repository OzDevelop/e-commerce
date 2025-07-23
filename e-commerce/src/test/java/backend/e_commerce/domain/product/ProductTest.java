package backend.e_commerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .sellerId(1L)
                .category("가전제품")
                .name("김치냉장고")
                .description("아삭아삭 맛있는 김치")
                .price(2000000)
                .stock(30)
                .status(ProductStatus.AVAILABLE)
                .brand("IG")
                .manufacturer("멋진 김치냉장고 회사")
                .build();
    }

    @Test
    void 재고_추가_테스트() {
        // when
        product.increaseStock(5);

        // then
        assertEquals(product.getStock(), 35);
    }

    @Test
    void 재고_감소_테스트() {
        // when
        product.decreaseStock(5);

        // then
        assertEquals(product.getStock(), 25);
    }

    @Test
    void 재고_0미만일경우_예외_테스트() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> product.increaseStock(-40));
    }

    @Test
    void 재고_감소_재고보다많은요청_예외_테스트() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> product.decreaseStock(40));
    }

    @Test
    void 재고_0일경우_상태변경_테스트() {
        // when
        product.decreaseStock(30);
        assertEquals(product.getStatus().toString(), "OUT_OF_STOCK");
    }
}