package strategy;

import java.util.ArrayList;

public final class Teen implements CalculateNiceScoreStrategy {
    private ArrayList<Double> niceScores;
    public Teen(final ArrayList<Double> niceScores) {
        this.niceScores = niceScores;
    }

    @Override
    public double getNiceScore() {
        int k = 1;
        Double sum = 0.0;
        for (Double score : niceScores) {
            sum += score * k;
            k++;
        }
        return sum / (niceScores.size() * (niceScores.size() + 1) / 2);
    }
}
