package strategy;

import common.Constants;

import java.util.ArrayList;

public final class Baby implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    public Baby(final ArrayList<Double> niceScores) {
        this.niceScores = niceScores;
    }

    @Override
    public double getNiceScore() {
        return Constants.BABY_SCORE;
    }
}
