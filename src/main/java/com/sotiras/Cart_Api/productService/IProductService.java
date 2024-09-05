package com.sotiras.Cart_Api.productService;

import com.sotiras.Cart_Api.dto.ProductDto;
import com.sotiras.Cart_Api.model.Product;
import com.sotiras.Cart_Api.request.AddProductRequest;
import com.sotiras.Cart_Api.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String productName);
    List<Product> getProductByBrandAndName(String brand, String productName);
    Long countProductsByBrandAndName(String brand, String productName);
    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
