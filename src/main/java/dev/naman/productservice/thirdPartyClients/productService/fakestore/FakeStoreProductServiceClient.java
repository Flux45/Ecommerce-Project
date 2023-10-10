package dev.naman.productservice.thirdPartyClients.productService.fakestore;
import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper over FakeStore API.
 */
@Service
public class FakeStoreProductServiceClient {
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${fakestore.api.url}")
    private String fakeStoreApiUrl;

    @Value("${fakestore.api.paths.product}")
    private String fakeStoreProductsApiPath;
    private String getProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String createProductRequestUrl = "https://fakestoreapi.com/products";
    private String specificProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String productRequestsBaseUrl = "https://fakestoreapi.com/products";

    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

//    private FakeStoreProductDto convertFakeStoreProductIntoGenericProduct(FakeStoreProductDto fakeStoreProductDto) {
//        FakeStoreProductDto product = new FakeStoreProductDto();
//        product.setId(fakeStoreProductDto.getId());
//        product.setImage(fakeStoreProductDto.getImage());
//        product.setDescription(fakeStoreProductDto.getDescription());
//        product.setTitle(fakeStoreProductDto.getTitle());
//        product.setPrice(fakeStoreProductDto.getPrice());
//        product.setCategory(fakeStoreProductDto.getCategory());
//
//        return product;
//    }

    public FakeStoreProductDto createProduct(GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(
                createProductRequestUrl, product, FakeStoreProductDto.class
        );

        return response.getBody();
    }




    public FakeStoreProductDto updateProductById(Long id, GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.execute(specificProductRequestUrl, HttpMethod.PUT,requestCallback,responseExtractor, id, product);
//        GenericProductDto result = new GenericProductDto();
//
//        result.setTitle(product.getTitle());
//        result.setDescription(product.getDescription());
//        result.setCategory(product.getCategory());
//        result.setPrice(product.getPrice());
//        result.setImage(product.getImage());
//        result.setId(id);
        return response.getBody();
    }


    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(getProductRequestUrl, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        if(fakeStoreProductDto == null) {
            throw new NotFoundException("Product with id: "+id+" doesn't exist.");
        }

        return fakeStoreProductDto;
    }


    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response =
                restTemplate.getForEntity(productRequestsBaseUrl,FakeStoreProductDto[].class);
        List<FakeStoreProductDto> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: response.getBody()) {
            products.add(fakeStoreProductDto);
        }
        return products;
    }


    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Set HTTP headers in the request callback
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.execute(specificProductRequestUrl, HttpMethod.DELETE,
                        requestCallback, responseExtractor, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return (fakeStoreProductDto);
    }
}
