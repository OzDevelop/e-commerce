package backend.e_commerce.application.port.in.product;

import backend.e_commerce.application.command.product.CreateProductCommand;
import backend.e_commerce.application.command.product.UpdateProductCommand;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import java.util.List;

public interface ProductCommandUseCase {
    Product createProduct(CreateProductCommand productCommand);
    List<Product> createProducts(List<CreateProductCommand> productsCommand);
    Product updateProduct(Long productId, UpdateProductCommand command);

    void deleteProduct(Long productId);

    void changeStatus(Long productId, ProductStatus productStatus);

    void increaseStock(Long productId, Integer quantity);
    void decreaseStock(Long productId, Integer quantity);
}
