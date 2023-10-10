package dev.naman.productservice.services;

import dev.naman.productservice.exceptions.NotFoundException;
import dev.naman.productservice.thirdPartyClients.productService.fakestore.FakeStoreProductDto;
import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.thirdPartyClients.productService.fakestore.FakeStoreProductServiceClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final FakeStoreProductServiceClient fakeStoreProductServiceClient;
    private RestTemplateBuilder restTemplateBuilder;
    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }



    private GenericProductDto convertFakeStoreProductIntoGenericProduct(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto product = new GenericProductDto();
        product.setId(fakeStoreProductDto.getId());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());

        return product;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        return convertFakeStoreProductIntoGenericProduct(fakeStoreProductServiceClient.createProduct(product));
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto product) {
        return convertFakeStoreProductIntoGenericProduct(fakeStoreProductServiceClient.updateProductById(id,product));
    }

    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException {
        return convertFakeStoreProductIntoGenericProduct(fakeStoreProductServiceClient.getProductById(id));
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<GenericProductDto> genericProductDtos = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductServiceClient.getAllProducts()) {
            genericProductDtos.add(convertFakeStoreProductIntoGenericProduct(fakeStoreProductDto));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto deleteProductById(Long id) {
        return convertFakeStoreProductIntoGenericProduct(fakeStoreProductServiceClient.deleteProductById(id));
    }


}
