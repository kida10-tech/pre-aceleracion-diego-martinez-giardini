package com.alkemy.disney.disney.service.implement;

import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.MovieDTOBasic;
import com.alkemy.disney.disney.dto.MovieDTOFilter;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.CharMapper;
import com.alkemy.disney.disney.mapper.MovieMapper;
import com.alkemy.disney.disney.repository.MovieRepository;
import com.alkemy.disney.disney.repository.specification.MovieSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImplement implements MovieService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CharMapper charMapper;
    @Autowired
    private CharacterService charService;
    @Autowired
    private MovieSpecification movieSpecification;

    @Override
    public MovieDTO save(MovieDTO movie) {
        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movie, true);
        MovieEntity movieSaved = movieRepository.save(movieEntity);
        MovieDTO result = movieMapper.movieEntity2DTO(movieSaved, false);

        return result;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> entities = movieRepository.findAll();
        List<MovieDTO> result = movieMapper.movieEntityList2DTOList(entities, true);

        return result;
    }

    @Override
    public List<MovieDTOBasic> getBasicList() {
        List<MovieEntity> movieList = movieRepository.findAll();
        List<MovieDTOBasic> resultDTO = movieMapper.entityList2BasicDTO(movieList);
        return resultDTO;
    }


    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO modify(Long id, MovieDTO movieDTO) {
        MovieEntity savedMovie = this.getById(id);

        savedMovie.setImage(movieDTO.getImage());
        savedMovie.setTitle(movieDTO.getTitle());

        String date = movieDTO.getCreationDate().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate transformedDate = LocalDate.parse(date, formatter);
        savedMovie.setCreationDate(transformedDate);

        savedMovie.setStar(movieDTO.getStar());
        //savedMovie.setGenreId(movieDTO.getGenreId());
        //savedMovie.setCharacters(charMapper.charDTOList2EntityList(movieDTO.getCharacters()));

        MovieEntity movieEntity = movieRepository.save(savedMovie);
        MovieDTO result = movieMapper.movieEntity2DTO(movieEntity, false);

        return result;
    }

    @Override
    public void addCharacter(Long movieId, Long characterId) {
        MovieEntity movieEntity = this.getById(movieId);
        movieEntity.getCharacters().size();

        CharacterEntity character = charService.getById(characterId);
        movieEntity.addCharacter(character);
        movieRepository.save(movieEntity);
    }

    @Override
    public List<MovieDTO> getByFilters(String title, Set<Long> genre, String order) {
        MovieDTOFilter movieFilters = new MovieDTOFilter(title, genre, order);
        List<MovieEntity> entityList = movieRepository.findAll(movieSpecification.getByFilters(movieFilters));
        List<MovieDTO> result = movieMapper.movieEntityList2DTOList(entityList, true);
        return result;
    }

    @Override
    public MovieEntity getById(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);
        if(!movieEntity.isPresent()) {
            throw new ParamNotFound("Movie does not exist.");
        }
        return movieEntity.get();
    }

    @Override
    public MovieDTO getByDetails(Long id) {
        MovieEntity movieEntity = this.getById(id);
        MovieDTO result = movieMapper.movieEntity2DTO(movieEntity, true);
        return result;
    }

    @Override
    public void addGenre(Long movieId, Long genreId) {

    }

}
