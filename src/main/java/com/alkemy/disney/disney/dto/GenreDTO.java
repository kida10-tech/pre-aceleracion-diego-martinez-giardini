package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GenreDTO {
    private Long id;
    private String name;
    private List<MovieDTO> movies;
}
