package com.alkemy.disney.disney.service.implement;

import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.CharacterDTOFilter;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.CharMapper;
import com.alkemy.disney.disney.mapper.MovieMapper;
import com.alkemy.disney.disney.repository.CharacterRepository;
import com.alkemy.disney.disney.repository.specification.CharacterSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterServiceImplement implements CharacterService {

    @Autowired
    private CharMapper charMapper;
    @Autowired
    private CharacterRepository charRepository;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CharacterSpecification charSpecification;

    @Override
    public List<CharacterDTO> getAll() {
        List<CharacterEntity> charEntities = charRepository.findAll();
        List<CharacterDTO> dtoList = charMapper.charEntityList2DTOList(charEntities, true);
        return dtoList;
    }

    @Override
    public CharacterDTO modify(Long id, CharacterDTO charDTO) {
        CharacterEntity savedChar = this.getById(id);

        savedChar.setName(charDTO.getName());
        savedChar.setImage(charDTO.getName());
        savedChar.setAge(charDTO.getAge());
        savedChar.setWeight(charDTO.getWeight());
        savedChar.setBiography(charDTO.getBiography());
        
        savedChar.setMovies(movieMapper.movieDTOList2EntityList(charDTO.getMovies(), false));

        CharacterEntity editedChar = charRepository.save(savedChar);

        CharacterDTO savedDTO = charMapper.charEntity2DTO(editedChar, false);

        return savedDTO;
    }

    @Override
    public CharacterDTO save(CharacterDTO charDTO) {
        CharacterEntity charEntity = new CharacterEntity();

        charEntity.setName(charDTO.getName());
        charEntity.setImage(charDTO.getImage());
        charEntity.setAge(charDTO.getAge());
        charEntity.setWeight(charDTO.getWeight());
        charEntity.setBiography(charDTO.getBiography());

        CharacterEntity savedChar = charRepository.save(charEntity);
        CharacterDTO savedDTO = charMapper.charEntity2DTO(savedChar, false);

        return savedDTO;
    }

    @Override
    public CharacterEntity getById(Long id) {
        Optional<CharacterEntity> character = charRepository.findById(id);
        if(!character.isPresent()) {
            throw new ParamNotFound("This Disney character does not exist.");
        }
        return character.get();
    }

    @Override
    public void delete(Long id) {
        charRepository.deleteById(id);
    }

    @Override
    public List<CharacterDTO> getByFilters(String name, Integer age, Set<Long> movies, String order) {
        CharacterDTOFilter charFilter = new CharacterDTOFilter(name, age, movies, order);
        List<CharacterEntity> entities = charRepository.findAll(charSpecification.getByFilters(charFilter));
        List<CharacterDTO> result = charMapper.charEntityList2DTOList(entities,true);

        return result;
    }

    @Override
    public CharacterDTO getDetailById(Long id) {
        CharacterEntity charEntity = this.getById(id);
        CharacterDTO result = charMapper.charEntity2DTO(charEntity, true);
        return result;
    }

    @Override
    public void addMovie(Long id, Long idMovie) {
        CharacterEntity charEntity = charRepository.getById(id);
        charEntity.getMovies().size();
        MovieEntity movie = this.movieService.getById(idMovie);
        charEntity.addMovie(movie);
        this.charRepository.save(charEntity);
    }

    @Override
    public void removeMovie(Long id, Long idMovie) {
        CharacterEntity charEntity = charRepository.getById(id);
        charEntity.getMovies().size();
        MovieEntity movie = this.movieService.getById(idMovie);
        charEntity.removeMovie(movie);
        this.charRepository.save(charEntity);
    }
}
