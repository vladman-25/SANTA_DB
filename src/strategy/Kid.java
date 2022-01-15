package strategy;

import java.util.ArrayList;

public final class Kid implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    public Kid(final ArrayList<Double> niceScores) {
        this.niceScores = niceScores;
    }

    @Override
    public double getNiceScore() {
        Double sum = 0.0;
        for (Double score : niceScores) {
            sum += score;
        }
        return sum / niceScores.size();
    }
}
