package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CharacterDTO {
    private Long id;
    private String name;
    private String image;
    private Integer age;
    private Double weight;
    private String biography;

    private List<MovieDTO> movies;

}
