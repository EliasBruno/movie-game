package br.com.cardgame.movie.repository.custom;

import br.com.cardgame.movie.entity.User;

public class ScoreRanking {
    private User user;
    private Long scoreTotal;

    public ScoreRanking(User user, Long scoreTotal) {
        this.user = user;
        this.scoreTotal = scoreTotal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Long scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

}
