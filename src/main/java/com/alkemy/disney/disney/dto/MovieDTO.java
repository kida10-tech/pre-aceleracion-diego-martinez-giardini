package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MovieDTO {
    private Long id;
    private String image;
    private String title;
    private String creationDate;
    private Integer star;
    private Long genreId;

    private Set<CharacterDTO> characters;
}
