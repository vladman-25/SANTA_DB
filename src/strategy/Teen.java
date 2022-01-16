package strategy;

import java.util.ArrayList;

public final class Teen implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    private int niceBonus;
    public Teen(final ArrayList<Double> niceScores,
                final int niceBonus) {
        this.niceScores = niceScores;
        this.niceBonus = niceBonus;
    }

    @Override
    public double getNiceScore() {
        int k = 1;
        Double sum = 0.0;
        for (Double score : niceScores) {
            sum += score * k;
            k++;
        }
        Double avgScore = sum / (niceScores.size() * (niceScores.size() + 1) / 2);
        avgScore += (avgScore * niceBonus / 100);
        if (avgScore > 10.0) {
            avgScore = 10.0;
        }
        return avgScore;
    }
}
