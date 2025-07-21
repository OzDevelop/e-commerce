package backend.e_commerce.application.port.in.product;

import backend.e_commerce.domain.product.Product;
import java.util.List;

public interface ProductQueryUseCase {
    Product getProductById(Long productId);
    List<Product> getAllProducts(); //TODO - 추후 페이징 처리
    List<Product> findByCategory(String category);
    List<Product> findProductsByKeyword(String keyword);
}
