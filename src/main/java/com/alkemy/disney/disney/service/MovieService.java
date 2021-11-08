package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.dto.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO movie); //Save the country

    List<MovieDTO> getAllMovies(); //List all the countries

    public void delete(Long id);

    public MovieDTO modify(Long id, MovieDTO movieDTO);

    public void addCharacter(Long movieId, Long characterId);

    public List<MovieDTO> getByFilters(String name, String genre, String order);

    public MovieEntity getById(Long id);
}
