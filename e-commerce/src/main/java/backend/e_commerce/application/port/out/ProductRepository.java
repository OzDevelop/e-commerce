package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // 등록
    Product save(Product product);

    // 다건 등록
    List<Product> saveAll(List<Product> products);

    // 단건 조회
    Optional<Product> findById(Long productId);

    // 삭제
    void deleteById(Long productId);

    // 존재 여부 확인
    boolean existsById(Long productId);
}
