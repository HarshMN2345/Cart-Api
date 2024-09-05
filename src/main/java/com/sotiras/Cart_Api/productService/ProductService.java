package com.sotiras.Cart_Api.productService;

import com.sotiras.Cart_Api.ProductNotFoundException;
import com.sotiras.Cart_Api.dto.ImageDto;
import com.sotiras.Cart_Api.dto.ProductDto;
import com.sotiras.Cart_Api.model.Category;
import com.sotiras.Cart_Api.model.Image;
import com.sotiras.Cart_Api.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import com.sotiras.Cart_Api.model.Product;
import com.sotiras.Cart_Api.repository.CategoryRepository;
import com.sotiras.Cart_Api.repository.ProductRepository;
import com.sotiras.Cart_Api.request.AddProductRequest;
import com.sotiras.Cart_Api.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request) {
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(
                ()->{
                    Category newCategory=new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);

                }
        );
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,()->{
            throw new ProductNotFoundException("Product not found");
        });

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
       return productRepository.findById(productId).map(existingProduct->updateExistingProduct(existingProduct,request)).map(productRepository::save).orElseThrow(()->new ProductNotFoundException("Product not found"));

    }

    private Product updateExistingProduct(Product product, ProductUpdateRequest request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);
        return product;

    }
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String productName) {
        return productRepository.findByBrandAndName(brand,productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return productRepository.countByBrandAndName(brand,productName);
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
