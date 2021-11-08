package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    @Autowired
    private CharMapper charMapper;

    public MovieEntity movieDTO2Entity(MovieDTO dto, boolean loadChar) {
        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setImage(dto.getImage());
        movieEntity.setTitle(dto.getTitle());

        //cast string to date
        String date = dto.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate transformedDate = LocalDate.parse(date, formatter);
        movieEntity.setCreationDate(transformedDate);

        movieEntity.setStar(dto.getStar());
        movieEntity.setGenreId(dto.getGenreId());

        if(loadChar) {
            Set<CharacterEntity> entitySet = new HashSet<>();
            for (CharacterDTO characterDTO: dto.getCharacters()) {
                entitySet.add(charMapper.charDTO2Entity(characterDTO));
            }
            movieEntity.setCharacters(entitySet);
        }
        return movieEntity;
    }


    //Aqui coloco un boolean para poder mostrar
    public MovieDTO movieEntity2DTO(MovieEntity movieEntity, boolean loadChar) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movieEntity.getId());
        dto.setImage(movieEntity.getImage());
        dto.setTitle(movieEntity.getTitle());

        LocalDate date = movieEntity.getCreationDate();//1. Get the original format date
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); //2. Convert it to string
        dto.setCreationDate(formatDate);

        dto.setStar(movieEntity.getStar());
        dto.setGenreId(movieEntity.getGenreId());

        if(loadChar) {
            Set<CharacterDTO> dtoSet = new HashSet<>();
            for (CharacterEntity character: movieEntity.getCharacters()) {
                dtoSet.add(charMapper.charEntity2DTO(character, false));
            }
            dto.setCharacters(dtoSet);
        }

        return dto;
    }

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean load) {
        List<MovieDTO> dtos = new ArrayList<>();

        for (MovieEntity entity: entities) {
            dtos.add(this.movieEntity2DTO(entity, load));
        }
        return dtos;
    }

    public List<MovieEntity> movieDTOList2EntityList(List<MovieDTO> dtoList, boolean load) {
        List<MovieEntity> entities = new ArrayList<>();

        for (MovieDTO dto: dtoList) {
            entities.add(this.movieDTO2Entity(dto, load));
        }
        return entities;
    }

}
