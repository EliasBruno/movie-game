package br.com.cardgame.movie.entity;

import javax.persistence.*;

@Entity
public class MoviePair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieOneId")
    private Movie movieOne;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieTwoId")
    private Movie movieTwo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovieOne() {
        return movieOne;
    }

    public void setMovieOne(Movie movieOne) {
        this.movieOne = movieOne;
    }

    public Movie getMovieTwo() {
        return movieTwo;
    }

    public void setMovieTwo(Movie movieTwo) {
        this.movieTwo = movieTwo;
    }
}
