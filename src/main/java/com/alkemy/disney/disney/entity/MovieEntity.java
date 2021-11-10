package com.alkemy.disney.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "movie")
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Getter
@Setter
@Entity
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String image;

    private String title;

    @Column(name = "date_of_creation")
    private LocalDate creationDate;
    private Integer star;

    // Soft Delete:
    private boolean deleted = Boolean.FALSE;

//    @Column(name = "genre_id", nullable = false)
//    private Long genreId;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}) //genera tabla intermedia para relacionar tablas
    @JoinTable(name = "movie_chars",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "char_id"))
    private List<CharacterEntity> characters = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns= @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> movieGenres = new ArrayList<>();

    //Add character to movie
    public void addCharacter(CharacterEntity character) {
        this.characters.add(character);
    }

    //Delete character from movie
    public void removeCharacter(CharacterEntity character) {
        this.characters.remove(character);
    }

    // Genres:
    public void addGenre(GenreEntity genre) {
        this.movieGenres.add(genre);
    }

    public void removeGenre(GenreEntity genre) {
        this.movieGenres.remove(genre);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final MovieEntity other = (MovieEntity) obj;
        return other.id == this.id;
    }
}
