package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.dto.CharacterDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CharacterService {

    public List<CharacterDTO> getAll();

    public CharacterDTO modify(Long id, CharacterDTO charDTO);

    public CharacterDTO save(CharacterDTO charDTO);

    public CharacterEntity getById(Long id);

    public void delete(Long id);

    public List<CharacterDTO> getByFilters(String name, Integer age, Set<Long> movies, String order);

    public CharacterDTO getDetailById(Long id);

    public void addMovie(Long id, Long idMovie);

    public void removeMovie(Long id, Long idMovie);

}
