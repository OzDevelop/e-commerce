package backend.e_commerce.application.service;

import backend.e_commerce.application.command.product.CreateProductCommand;
import backend.e_commerce.application.command.product.ProductCommandMapper;
import backend.e_commerce.application.command.product.UpdateProductCommand;
import backend.e_commerce.application.port.in.product.ProductCommandUseCase;
import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.application.port.out.UserRepository;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.domain.user.UserRole;
import backend.e_commerce.infrastructure.out.persistence.product.ProductEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.product.entity.ProductEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements ProductCommandUseCase {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Product createProduct(CreateProductCommand command) {
        User seller = userRepository.findById(command.getSellerId());

        if (!(seller.getRole() == UserRole.SELLER)) {
            throw new IllegalStateException("User is not seller");
        }

        Product product = ProductCommandMapper.toDomain(command);
        return productRepository.save(product);
    }

    @Override
    public List<Product> createProducts(List<CreateProductCommand> productsCommand) {
        List<Product> products = productsCommand.stream()
                .map(this::createProduct)
                .toList();


        return productRepository.saveAll(products);
    }

    @Override
    public Product updateProduct(Long productId, UpdateProductCommand command) {
        Product product = findProductByIdOrThrow(productId);

        ProductCommandMapper.applyUpdate(product, command);
        ProductEntityMapper.fromDomain(product);

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        productRepository.deleteById(productId);
    }

    @Override
    public void changeStatus(Long productId, ProductStatus productStatus) {
        Product product = findProductByIdOrThrow(productId);

        product.changeStatus(productStatus);
        productRepository.save(product);
    }

    @Override
    public void increaseStock(Long productId, Integer quantity) {
        Product product = findProductByIdOrThrow(productId);

        product.increaseStock(quantity);
        productRepository.save(product);
    }

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        Product product = findProductByIdOrThrow(productId);

        product.decreaseStock(quantity);
        productRepository.save(product);
    }

    private Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }
}