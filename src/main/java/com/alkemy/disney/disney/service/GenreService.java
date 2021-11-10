package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO save(GenreDTO dto);

    List<GenreDTO> getAllGenres();

    GenreDTO modify(Long id, GenreDTO genreDTO);

    void deleteGenreById(Long id);
}
