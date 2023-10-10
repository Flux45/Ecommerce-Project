package dev.naman.productservice.services;

import dev.naman.productservice.exceptions.NotFoundException;
import dev.naman.productservice.dtos.GenericProductDto;

import java.util.List;

public interface ProductService {


    GenericProductDto createProduct(GenericProductDto product);

    GenericProductDto updateProductById(Long id, GenericProductDto product);

    GenericProductDto getProductById(Long id) throws NotFoundException;

    List<GenericProductDto> getAllProducts();

    GenericProductDto deleteProductById(Long id);

}
