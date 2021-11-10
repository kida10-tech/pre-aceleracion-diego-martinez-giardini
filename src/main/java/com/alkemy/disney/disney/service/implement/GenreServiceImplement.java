package com.alkemy.disney.disney.service.implement;

import com.alkemy.disney.disney.dto.GenreDTO;
import com.alkemy.disney.disney.entity.GenreEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.GenreMapper;
import com.alkemy.disney.disney.repository.GenreRepository;
import com.alkemy.disney.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImplement implements GenreService {

    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreDTO save(GenreDTO dto) {
        GenreEntity genreEntity = genreMapper.genreDTO2Entity(dto); //Aqui convierto a ContinentDTO a entity.
        GenreEntity genreSaved = genreRepository.save(genreEntity);
        //Aqui tenemos la entidad guardada con Id, image, y nombre, en forma de entidad.
        // Como el controller maneja DTO debemos convertirla en un DTO.

        GenreDTO result = genreMapper.genreEntity2DTO(genreSaved); //Finalmente vuelvo a convertir la entidad guardada en DTO.
        return result;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<GenreEntity> entities = genreRepository.findAll();
        List<GenreDTO> result = genreMapper.genreEntityList2DTOList(entities);
        return result;
    }


    @Override
    public GenreDTO modify(Long id, GenreDTO genreDTO) {
        GenreEntity savedGenre = this.getById(id);
        savedGenre.setName(genreDTO.getName());
        GenreEntity editedGenre = genreRepository.save(savedGenre);
        GenreDTO result = genreMapper.genreEntity2DTO(editedGenre);
        return result;
    }

    @Override
    public void deleteGenreById(Long id) {

    }

    public GenreEntity getById(Long id) {
        Optional<GenreEntity> toBeFound = genreRepository.findById(id);
        if(!toBeFound.isPresent()) {
            throw new ParamNotFound("Genre does not exist: " + id);
        }
        return toBeFound.get();
    }
}
