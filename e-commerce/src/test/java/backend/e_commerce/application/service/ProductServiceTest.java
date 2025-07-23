package backend.e_commerce.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import backend.e_commerce.application.command.product.CreateProductCommand;
import backend.e_commerce.application.command.product.UpdateProductCommand;
import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Long productId = 1L;
    private Product product;
    private CreateProductCommand createProductCommand;
    private UpdateProductCommand updateProductCommand;

    @BeforeEach
    void setUp() {
        createProductCommand = createProductCommand.builder()
                .sellerId(1L)
                .category("가전제품")
                .name("김치냉장고")
                .description("아삭아삭 맛있는 김치")
                .price(2000000)
                .stock(30)
                .brand("IG")
                .manufacturer("멋진 김치냉장고 회사")
                .build();

        updateProductCommand = updateProductCommand.builder()
                .category(Optional.of("가전제품"))
                .name(Optional.of("짱쎈에어컨과선풍기"))
                .price(Optional.of(3000000))
                .stock(Optional.of(10))
                .build();

        product = Product.builder()
                .id(productId)
                .sellerId(createProductCommand.getSellerId())
                .category(createProductCommand.getCategory())
                .name(createProductCommand.getName())
                .description(createProductCommand.getDescription())
                .price(createProductCommand.getPrice())
                .stock(createProductCommand.getStock())
                .brand(createProductCommand.getBrand())
                .manufacturer(createProductCommand.getManufacturer())
                .build();
    }

    @Test
    void 제품_단건등록_테스트() {
        given(productRepository.save(any(Product.class))).willReturn(product);

        Product createdProduct = productService.createProduct(createProductCommand);

        assertNotNull(createdProduct);
        assertEquals(createProductCommand.getSellerId(), createdProduct.getSellerId());
        assertEquals(createProductCommand.getName(), createdProduct.getName());
        assertEquals(createProductCommand.getPrice(), createdProduct.getPrice());
        assertEquals(createProductCommand.getStock(), createdProduct.getStock());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void 제품_다중등록_테스트() {
        List<Product> mockProducts = List.of(product);

        given(productRepository.saveAll(anyList())).willReturn(mockProducts);
        List<Product> result = productService.createProducts(List.of(createProductCommand));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("김치냉장고", result.get(0).getName());

        verify(productRepository).saveAll(anyList());
    }

    @Test
    void 제품_수정_테스트() {
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        productService.updateProduct(productId, updateProductCommand);

        assertEquals("짱쎈에어컨과선풍기", product.getName());      // 변경된 name
        assertEquals(3000000, product.getPrice());             // 변경된 가격
        assertEquals(10, product.getStock());                  // 변경된 재고

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));    // 더티체킹으로 업데이트 함
    }

    @Test
    void 제품_삭제_테스트() {
        given(productRepository.save(any())).willReturn(product);
        given(productRepository.existsById(productId)).willReturn(true);

        Product createdProduct = productService.createProduct(createProductCommand);

        productService.deleteProduct(createdProduct.getId());

        assertNotNull(createdProduct);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void 제품_상태수정_테스트() {
        Product existing = mock(Product.class);
        given(productRepository.findById(productId)).willReturn(Optional.of(existing));

        productService.changeStatus(productId, ProductStatus.OUT_OF_STOCK);

        verify(productRepository).findById(productId);
    }

    @Test
    void 제품_재고증가_테스트() {
        Product existing = mock(Product.class);
        given(productRepository.findById(productId)).willReturn(Optional.of(existing));

        productService.increaseStock(productId, 5);

        verify(productRepository).findById(productId);
        verify(existing).increaseStock(5);
    }

    @Test
    void 제품_재고감소_테스트() {
        Product existing = mock(Product.class);
        given(productRepository.findById(productId)).willReturn(Optional.of(existing));

        productService.decreaseStock(productId, 3);

        verify(productRepository).findById(productId);
        verify(existing).decreaseStock(3);
    }

    @Test
    void 제품_수정실패_테스트() {
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, updateProductCommand);
        });

        verify(productRepository).findById(productId);
    }

    @Test
    void 제품_삭제실패_테스트() {
        given(productRepository.existsById(productId)).willReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository).existsById(productId);
    }

    //TODO - 조회 관련 테스트 케이스
}