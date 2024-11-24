package com.PharmAide.domain.service;


import com.PharmAide.domain.dao.Product;
import com.PharmAide.domain.repository.interfaces.ICategoryRepository;
import com.PharmAide.domain.repository.interfaces.IProductRepository;
import com.PharmAide.web.dto.ProductDTO;
import jakarta.inject.Inject;

import java.util.List;

public class ProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    @Inject
    public ProductService(IProductRepository productRepository,
                          ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product save(ProductDTO product) {
        var cat = categoryRepository.getbyId(Integer.parseInt(product.getCategory()));
        Product newproduct = Product.builder()
                .brandname(product.getBrandname())
                .genericname(product.getGenericname())
                .price(product.getPrice())
                .categoryid(cat.get())
                .build()
                ;
        var res = productRepository.addProduct(newproduct);
        return res.orElse(null);
    }

    public List<Product> getAllProds() {
        var res = productRepository.getallProducts();



        return res;
    }


    public Product getbyname(String s) {
        var res = productRepository.getbyName(s);
        return res.orElse(null);
    }

    public Product getUserbyID(int id) {
        var res = productRepository.getbyId(id);
        return res.orElse(null);
    }

    public Product update(ProductDTO product, String name) {
        Product newproduct = Product.builder()
                .brandname(product.getBrandname())
                .genericname(product.getGenericname())
                .price(product.getPrice())
                .build();
        var res = productRepository.updateProduct(newproduct, name);
        return res.orElse(null);
    }

    public void delete(String name) {
        productRepository.delete(name);
    }

}
