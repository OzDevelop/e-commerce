package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.port.in.product.ProductCommandUseCase;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import backend.e_commerce.representaion.request.product.CreateProductRequest;
import backend.e_commerce.representaion.request.product.UpdateProductRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductCommandUseCase productCommandUseCase;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        Product createdProduct = productCommandUseCase.createProduct(request.toCommand());

        return ResponseEntity.ok(createdProduct);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Product>> batchCreateProduct(@RequestBody List<CreateProductRequest> requests) {
        List<Product> createdProducts = productCommandUseCase.createProducts(requests.stream().map(
                CreateProductRequest::toCommand).collect(
                Collectors.toList()));

        return ResponseEntity.ok(createdProducts);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                 @RequestBody UpdateProductRequest request) {
        Product updatedProduct = productCommandUseCase.updateProduct(productId, request.toCommand());

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productCommandUseCase.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long productId,
            @RequestParam ProductStatus status) {
        productCommandUseCase.changeStatus(productId, status);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/stock/increase")
    public ResponseEntity<Void> increaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        productCommandUseCase.increaseStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/stock/decrease")
    public ResponseEntity<Void> decreaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        productCommandUseCase.decreaseStock(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
