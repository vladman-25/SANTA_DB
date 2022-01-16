package strategy;

import java.util.ArrayList;

public final class Kid implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    private int niceBonus;
    public Kid(final ArrayList<Double> niceScores,
               final int niceBonus) {
        this.niceScores = niceScores;
        this.niceBonus = niceBonus;
    }

    @Override
    public double getNiceScore() {
        Double sum = 0.0;
        for (Double score : niceScores) {
            sum += score;
        }
        Double avgScore = sum / niceScores.size();
        avgScore += (avgScore * niceBonus / 100) % 10;
        return avgScore;
    }
}
