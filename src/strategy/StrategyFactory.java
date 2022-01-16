package strategy;

import data.Child;
import data.Database;
import enums.Ages;
import tasks.Utils;

import java.util.ArrayList;

public final class StrategyFactory {
    /**
     *
     * @param indexOfChild
     * @return
     */
    public Double createStrategy(final int indexOfChild) {

        Child tempChild = Database.getDatabase().getInitialChildren().get(indexOfChild);
        ArrayList<Double> tempScoresList = Database.getDatabase()
                .getScores().get(tempChild.getId());
        Integer tempBonus = tempChild.getNiceScoreBonus();

        if (Utils.getChildAgeEnum(tempChild) == Ages.BABY) {
            Double score = new Baby(tempScoresList, tempBonus).getNiceScore();
            return score;
        }
        if (Utils.getChildAgeEnum(tempChild) == Ages.KID) {
            Double score = new Kid(tempScoresList, tempBonus).getNiceScore();
            return score;
        }
        if (Utils.getChildAgeEnum(tempChild) == Ages.TEEN) {
            Double score = new Teen(tempScoresList, tempBonus).getNiceScore();
            return score;
        }
        return null;
    }
}
