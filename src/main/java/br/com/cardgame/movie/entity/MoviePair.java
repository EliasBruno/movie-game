package br.com.cardgame.movie.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MoviePair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameA;
    private Integer launchingA;
    private String nameB;
    private Integer launchingB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public Integer getLaunchingA() {
        return launchingA;
    }

    public void setLaunchingA(Integer launchingA) {
        this.launchingA = launchingA;
    }

    public String getNameB() {
        return nameB;
    }

    public void setNameB(String nameB) {
        this.nameB = nameB;
    }

    public Integer getLaunchingB() {
        return launchingB;
    }

    public void setLaunchingB(Integer launchingB) {
        this.launchingB = launchingB;
    }
}
