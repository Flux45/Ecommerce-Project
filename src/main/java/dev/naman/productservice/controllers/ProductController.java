package dev.naman.productservice.controllers;

import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.exceptions.NotFoundException;
import dev.naman.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
//    @Autowired
    // field injection
    private ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);

    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product) {
//        System.out.println(product.name);
        return productService.createProduct(product);
    }

    @PutMapping ("{id}")
    public ResponseEntity<GenericProductDto> updateProductById(@PathVariable("id") Long id, @RequestBody GenericProductDto product) {
        return new ResponseEntity<>(productService.updateProductById(id, product), HttpStatus.OK );
    }


}
