package br.com.cardgame.movie.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user = null;

    @OneToOne
    private MoviePair moviePair;

    private Integer success;

    private Date start;

    private Date end;

    private String selected;

    private Integer errors = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MoviePair getMoviePair() {
        return moviePair;
    }

    public void setMoviePair(MoviePair moviePair) {
        this.moviePair = moviePair;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success += success;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors += errors;
    }
}
