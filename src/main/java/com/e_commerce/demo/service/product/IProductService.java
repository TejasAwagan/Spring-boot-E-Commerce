package com.e_commerce.demo.service.product;

import com.e_commerce.demo.dto.ProductDto;
import com.e_commerce.demo.models.Category;
import com.e_commerce.demo.models.Product;
import org.hibernate.Cache;

import java.util.List;

public interface IProductService {
    Product addProduct(ProductDto product);

    Product getProductById(String id);

    void deleteProductById(String id);

    Product updateProduct(ProductDto product, String productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategoryName(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductByName(String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
