package com.e_commerce.demo.service.product;

import com.e_commerce.demo.dto.ProductDto;
import com.e_commerce.demo.exceptions.AlreadyExistsException;
import com.e_commerce.demo.exceptions.ProductNotFoundException;
import com.e_commerce.demo.exceptions.ResourceNotFoundException;
import com.e_commerce.demo.models.Category;
import com.e_commerce.demo.models.Product;
import com.e_commerce.demo.repository.ICategoryRepository;
import com.e_commerce.demo.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService{

    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Product addProduct(ProductDto request) {
        //check if category exist or not

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category with id: " + request.getCategoryId() + " Not found !"));

      //  check if the product already exist or not
        boolean productExist = productRepository.existsByProductName(request.getProductName());

        if(productExist){
            throw new AlreadyExistsException("Product Already exist !");
        }
        else{
           Product product1 =  createProduct(request,category);
            return productRepository.save(product1);
        }
    }

    private Product createProduct(ProductDto request, Category category) {

                Product product = new Product();
                product.setProductId(UUID.randomUUID().toString());
                product.setProductName(request.getProductName());
                product.setDescription(request.getDescription());
                product.setBrand(request.getBrand());
                product.setPrice(request.getPrice());
                product.setInventory(request.getInventory());
                product.setCategoryId(category.getCategoryId());
                return product;
    }


    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found !"));
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,()->{new ResourceNotFoundException("Product not Found");
                });
    }

    @Override
    public Product updateProduct(ProductDto request, String productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
    }

    private Product updateExistingProduct(Product existingProduct, ProductDto request) {
        existingProduct.setProductName(request.getProductName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        // Assuming you pass categoryId in ProductDto
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + request.getCategoryId()));

        existingProduct.setCategoryId(category.getCategoryId());
        return existingProduct;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        Category category1 = categoryRepository.findByCategoryName(categoryName);

        System.out.println(category1);
        return productRepository.findByCategoryId(category1.getCategoryId());
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
