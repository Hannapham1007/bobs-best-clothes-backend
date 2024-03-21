package com.booleanuk.requests.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private float price;

    @NotBlank
    private String imageURL;

    @NotBlank
    private int categoryId;

}
