package com.alkemy.disney.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "disney_character")
@SQLDelete(sql = "UPDATE disney_character SET deleted = true WHERE id=?") //This query provide the soft delete, as an update over icon
@Where(clause = "deleted=false")
@Getter
@Setter
@Entity
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String image;
    private Integer age;
    private Double weight;
    private String biography;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinColumn(name = "movie_id", updatable = false)
    private List<MovieEntity> movies = new ArrayList<>();

    //Soft delete
    private Boolean deleted = Boolean.FALSE;

    //Add and remove movies
    public void addMovie(MovieEntity movie) {
        this.movies.add(movie);
    }

    public void removeMovie(MovieEntity movie) {
        this.movies.add(movie);
    }
}
