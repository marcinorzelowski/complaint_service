package com.orzelowski.complaintservice.service;


import com.orzelowski.complaintservice.exception.ComplaintDataException;
import com.orzelowski.complaintservice.model.Product;
import com.orzelowski.complaintservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProductById_ProductExists_ReturnsProduct() {
        //Arrange
        Product product = new Product();
        product.setId(1L);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //Act
        Product foundProduct = productService.getProductById(1L);

        //Assert
        Assertions.assertAll(() -> {
            Assertions.assertEquals(product, foundProduct);
        });
    }

    @Test
    public void getProductById_ProductNotExists_ThrowsException() {
        //Arrange

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        Exception exception = Assertions.assertThrows(ComplaintDataException.class, () -> productService.getProductById(1L));
        Assertions.assertEquals("Product with given ID does not exist.", exception.getMessage());
    }


}