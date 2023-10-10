package dev.naman.productservice.thirdPartyClients.productService.fakestore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}

// Break till 10:35