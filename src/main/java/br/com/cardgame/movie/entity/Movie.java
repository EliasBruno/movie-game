package br.com.cardgame.movie.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer launching;
    @OneToMany
    private List<MoviePair> moviePair;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLaunching() {
        return launching;
    }

    public void setLaunching(Integer launching) {
        this.launching = launching;
    }

    public List<MoviePair> getMoviePair() {
        return moviePair;
    }

    public void setMoviePair(List<MoviePair> moviePair) {
        this.moviePair = moviePair;
    }
}
