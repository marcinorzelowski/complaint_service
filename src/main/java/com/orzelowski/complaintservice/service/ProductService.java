package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.model.Product;
import com.orzelowski.complaintservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) throws ComplaintDataException {
        return productRepository.findById(productId).orElseThrow(() -> new ComplaintDataException("Product with given ID does not exist."));
    }
}
