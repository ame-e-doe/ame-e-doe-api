package com.api.loveanddonateapi.dto;

import com.api.loveanddonateapi.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DigitalProductDto {

    private Long id;

    private byte url;

    private String title;

    private String description;

    private Double value;

    private Category category;

}
