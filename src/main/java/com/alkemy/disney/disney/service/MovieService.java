package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.MovieDTOBasic;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.dto.MovieDTO;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO movie); //Save movie

    List<MovieDTO> getAllMovies(); //List all movies

    List<MovieDTOBasic> getBasicList(); //Basic list movies

    void delete(Long id);

    MovieDTO modify(Long id, MovieDTO movieDTO);

    void addCharacter(Long movieId, Long characterId);

    MovieEntity getById(Long id);

    MovieDTO getByDetails(Long id);

    void addGenre(Long movieId, Long genreId);

    List<MovieDTO> getByFilters(String name, Set<Long> genre, String order);
}
