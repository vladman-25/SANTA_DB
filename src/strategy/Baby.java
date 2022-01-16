package strategy;

import common.Constants;

import java.util.ArrayList;

public final class Baby implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    private int niceBonus;
    public Baby(final ArrayList<Double> niceScores,
               final int niceBonus) {
        this.niceScores = niceScores;
        this.niceBonus = niceBonus;
    }

    @Override
    public double getNiceScore() {
        return Constants.BABY_SCORE;
    }
}
